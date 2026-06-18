import { Injectable, signal, computed } from '@angular/core';

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
  // Alle Sessions
  sessions = signal<ChatSession[]>([]);
  
  // Die ID der aktuell ausgewählten Session
  activeSessionId = signal<string | null>(null);

  // Ein "Computed Signal", das automatisch die aktive Session zurückgibt
  activeSession = computed(() => {
    return this.sessions().find(s => s.id === this.activeSessionId()) || null;
  });

  // 1. Neue Session anlegen
  createNewSession() {
    const newSession: ChatSession = {
      id: Date.now().toString(), // Simpler unique ID Generator
      messages: []
    };
    
    // Füge die neue Session zur Liste hinzu
    this.sessions.update(list => [...list, newSession]);
    // Wähle sie direkt aus
    this.activeSessionId.set(newSession.id);
  }

  // 2. Session wechseln
  selectSession(id: string) {
    this.activeSessionId.set(id);
  }

  // 3. Nachricht zur aktiven Session hinzufügen
  addMessage(text: string, isMe: boolean) {
    const currentId = this.activeSessionId();
    console.log('2. Service addMessage aufgerufen für Session:', currentId);
  
    if (!currentId) {
      console.error('❌ Fehler: Keine aktive Session ausgewählt!');
      return;
    }

    this.sessions.update(list => list.map(session => {
      if (session.id == currentId) {
        return {
          ...session,
          messages: [...session.messages, { text, isMe }]
        };
      }
      console.log('3. Aktuelle Nachrichten im Signal:', this.activeSession()?.messages);
      return session;
    }));
  }
}