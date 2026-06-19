/* AI USAGE:
- asked how to create Angular Project
- used AI to explain how to send HTTP requests from Angular Frontend to Backend and how to receive data from it
- bug fixing, error debugging and code corrections
 */

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { firstValueFrom, map, Observable } from 'rxjs';

import { SessionMetadata } from '../models/sessionMetadata.model';
import { Session } from '../models/session.model';
import { Message } from '../models/message.model';
import { QuizRequest } from '../models/quizRequest.model';

@Injectable({
  providedIn: 'root',
})
export class Api {
  private readonly baseURL = 'http://localhost:8080/sessions';

  constructor(private readonly http: HttpClient) {} //AI USAGE

  // ----- POST /sessions/ -----
  async createSession(userId: string, sessionTitle: string) : Promise<string> {

    if (!sessionTitle || sessionTitle.trim() === "")
    {
      const newSessionId = this.createSessionWithoutTitle(userId);
      return newSessionId;
    }
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const newSessionId = this.http.post<string> (
      `${this.baseURL}`, 
      {
        userID: userId,
        sessionTitle: sessionTitle
      },
      { headers }
    );
    return firstValueFrom(newSessionId);
  }

  async createSessionWithoutTitle(userId: string) : Promise<string> {

    const date = Date.now().toString();
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const newSessionId = this.http.post<string> (
      `${this.baseURL}`, 
      {
        userID: userId,
        sessionTitle: "Session " + date
      },
      { headers }
    );
    return firstValueFrom(newSessionId);
  }

  // ----- GET /sessions/?userId={userId} -----
  async getSessions(userId: string) : Promise<string[]> {

    const params = new HttpParams().set('userId', userId); // AI USAGE: 
    // Asked for explaination how request paramters are passed/used 
    // for api calls based on backend api endpoint header
    
    const allSessionsForUser = this.http.get<string[]> (
      `${this.baseURL}`, 
      {params}
    );
    return firstValueFrom(allSessionsForUser);
  }

  // ----- GET /sessions/{sessionId}?userId={userId} -----
  async getSessionMetadata(
    sessionId: string, 
    userId: string
  ) : Promise<SessionMetadata> {
    const params = new HttpParams().set('userId', userId); // AI USAGE

    const sessionMetadata = this.http.get<SessionMetadata> (
      `${this.baseURL}/${sessionId}`, 
      {params}
    );
    return firstValueFrom(sessionMetadata);
  }

  // ----- GET /sessions/{sessionId}/complete?userId={userId} -----
  async getCompleteSessionInformation(
    sessionId: string, 
    userId: string
  ) : Promise<Session> {
    const params = new HttpParams().set('userId', userId); // AI USAGE

    const session = this.http.get<Session> (
      `${this.baseURL}/${sessionId}/complete`, 
      {params}
    );
    return firstValueFrom(session);
  }

  // ----- DELETE /sessions/{sessionId}?userId={userId} -----
  async deleteSession(userId: string) : Promise<string> {

    const params = new HttpParams().set('userId', userId); // AI USAGE
    
    const statusOfDeletion = this.http.delete<string> (
      `${this.baseURL}`, 
      {params}
    );
    return firstValueFrom(statusOfDeletion);
  }

  // ----- POST /sessions/{sessionId}/messages -----
  // IMPORTANT DIFFERENCE TO FRONTEND:
  // returns only the LLM response as a string, not as a Message
  async sendAndReceiveMessage(
    sessionId: string, 
    messageContent: string
  ) : Promise<string> {
    const messageFromLLM = this.http.post<Message> (
      `${this.baseURL}/${sessionId}/messages`,
      messageContent
    ).pipe(
      map(message => message.messageContent) // AI Usage: How to access Object Attributes
    );
    return firstValueFrom(messageFromLLM);
  }

  // ----- GET /sessions/{sessionId}/messages?userId={userId}  -----
  async getAllMessagesForASession(
    sessionId: string, 
    userId: string
  ) : Promise<Message[]> {
    const params = new HttpParams().set('userId', userId); // AI USAGE

    const messages = this.http.get<Message[]> (
      `${this.baseURL}/${sessionId}/messages`, 
      {params}
    );
    return firstValueFrom(messages);    
  }

  // ----- POST /sessions/{sessionId}/quiz  -----
  async createQuizRequest(
    sessionId: string, 
    quizRequestContent: string, 
    count: number, 
    difficulty: string) 
    : Promise<QuizRequest> {
      const quizRequestToSendWithoutQuizItems : QuizRequest = {
        quizRequestContent, 
        count, 
        difficulty, 
        quizItems:[] // AI Usage: how to instantiate with empty array
      };

      const quizRequestReceivedWithQuizItems = this.http.post<QuizRequest> (
      `${this.baseURL}/${sessionId}/quiz`, 
      quizRequestToSendWithoutQuizItems
    );
    return firstValueFrom(quizRequestReceivedWithQuizItems); 
  }

  // ----- POST /sessions/{sessionId}/quiz/{quizItemId}/answer -----
  async createQuizEvaluation(
    sessionId: string, 
    quizItemId: string, 
    answer: string
  ) : Promise <string> {
    const quizAnswerEvaluation = this.http.post<string> (
      `${this.baseURL}/${sessionId}/quiz/${quizItemId}/answer`, 
      answer
    );
    return firstValueFrom(quizAnswerEvaluation);
  }

}
