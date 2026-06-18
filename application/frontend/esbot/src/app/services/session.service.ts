import { Injectable, signal, computed, inject } from '@angular/core';
import { Api } from './api';
import { Observable } from 'rxjs';
import { Session } from '../models/session.model';
import { SessionMetadata } from '../models/sessionMetadata.model';
import { Message } from '../models/message.model';

export interface ChatMessage {
  text: string;
  isMe: boolean;
}

export interface ChatSession {
  id: string;
  messages: ChatMessage[];
}

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private readonly userId = "11111111-1111-1111-1111-111111111111";

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

  async createNewSession() {
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

  selectSession(sessionId: string) {
    this.activeSessionId.set(sessionId);

  }

  addMessage(text: string, isMe: boolean) {
    const currentSession = this.activeSessionData();
  
    if (!currentSession) {
      return;
    }

    const newMessage: Message = {
    // Temporäre ID generieren, damit Angular einen eindeutigen Track-Key hat
    messageID: 'temp-' + crypto.randomUUID(), 
    sessionID: currentSession.sessionID,
    
    // Hier mappen wir deine Parameter auf die echten Interface-Namen
    messageContent: text,
    sender: isMe, // true = Du, false = KI/Bot
    
    timestamp: new Date().toISOString(),
    messageType: 'TEXT' // Oder was auch immer dein Standard-Typ im Backend ist
  };

  // Jetzt passt es perfekt in das Array rein!
  this.activeSessionData.set({
    ...currentSession,
    messages: [...currentSession.messages, newMessage]
  });
  }
}