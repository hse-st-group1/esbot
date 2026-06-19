import { Injectable, signal, computed, inject } from '@angular/core';
import { Api } from './api';
import { Session } from '../models/session.model';
import { SessionMetadata } from '../models/sessionMetadata.model';
import { Message } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private userId = "11111111-1111-1111-1111-111111111111";

  private readonly apiService = inject(Api);
  // Alle Sessions
  sessions = signal<SessionMetadata[]>([]);
  
  // Die ID der aktuell ausgewählten Session
  activeSessionId = signal<string | null>(null);

  activeSessionData = signal<Session | null>(null);

  // Ein "Computed Signal", das automatisch die aktive Session zurückgibt
  activeSession = computed(() => {
    return this.sessions().find(s => s.sessionID === this.activeSessionId()) || null;
  });

  async getSessions() {

    const sessionIds = await this.apiService.getSessions(this.userId);

    const mappedSessions: SessionMetadata[] = await Promise.all(
      sessionIds.map(async (sessionId) => {
        
        // 1. Hole die Metadaten asynchron vom Server
        const session = await this.apiService.getSessionMetadata(sessionId, this.userId);
        
        // 2. Gib das fertige Objekt an das neue Array zurück
        return session;
      })
    );

    // Jetzt ist "mappedSessions" ein sauberes SessionMetadata[] und du kannst es ins Signal schreiben!
    this.sessions.set(mappedSessions);
  }

  async changeUID(){
    this.userId = "11111111-1111-1111-1111-111111111122";
  }

  async initUID(){
    this.userId = "11111111-1111-1111-1111-111111111111"
  }

  async createNewSession() {
    try{
      const timestamp = new Date().toISOString();
      const newSession: SessionMetadata = {
        sessionID: await this.apiService.createSession(this.userId),
        userID: this.userId,
        startedAt: timestamp,
        lastAccessed: timestamp,
      };
      
      // Füge die neue Session zur Liste hinzu
      this.sessions.update(list => [...list, newSession]);
      // Wähle sie direkt aus
      this.activeSessionId.set(newSession.sessionID);
    }
    catch (error) {
      alert('You are not logged in')
      throw error;
    }
  }

  async selectSession(sessionId: string) {

    this.activeSessionId.set(sessionId);
    this.activeSessionData.set(null);

    try {

      const fullSession = await this.apiService.getCompleteSessionInformation(sessionId, this.userId);

      this.activeSessionData.set(fullSession);
      
    } catch (error) {
      console.error('Fehler beim Laden der Session-Details:', error);
    }
  }

  async addMessage(text: string, sender: boolean) {
    let currentSession = this.activeSessionData();

    if (!currentSession) {
      return;
    }

    // 1. Deine Nachricht erstellen
    const userMessage: Message = {
      messageID: 'temp-' + crypto.randomUUID(), 
      sessionID: currentSession.sessionID,
      messageContent: text,
      sender: sender, 
      timestamp: new Date().toISOString(),
      messageType: 'TEXT' 
    };

    // 2. Deine Nachricht ins Signal schreiben
    this.activeSessionData.set({
      ...currentSession,
      messages: [...currentSession.messages, userMessage]
    });

    // Auf LLM warten
    const messageFromLLM = await this.apiService.sendAndReceiveMessage(currentSession.sessionID, text);

    // Aktuellsten Zustand holen (Stale-Closure Schutz)
    currentSession = this.activeSessionData();
    if (!currentSession) return;

    const botMessage: Message = {
      messageID: 'temp-' + crypto.randomUUID(),
      sessionID: currentSession.sessionID,
      messageContent: messageFromLLM,
      sender: true,
      timestamp: new Date().toISOString(),
      messageType: 'TEXT'
    };

    // 3. Die neue Bot-Nachricht an das aktuelle Array anhängen
    this.activeSessionData.set({
      ...currentSession,
      messages: [...currentSession.messages, botMessage]
    });
  }
}