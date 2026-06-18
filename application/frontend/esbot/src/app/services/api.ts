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

  constructor(private readonly http: HttpClient) {}

  // ----- POST /sessions/ -----
  async createSession(userId: string) : Promise<string> {

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const newSessionId = this.http.post<string> (
      `${this.baseURL}`, 
      JSON.stringify(userId), // String should be same Format as UUID
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
  getCompleteSessionInformation(
    sessionId: string, 
    userId: string
  ) : Observable<Session> {
    const params = new HttpParams().set('userId', userId); // AI USAGE

    const session = this.http.get<Session> (
      `${this.baseURL}/${sessionId}/complete`, 
      {params}
    );
    return session;
  }

  // ----- DELETE /sessions/{sessionId}?userId={userId} -----
  deleteSession(userId: string) : Observable<string> {

    const params = new HttpParams().set('userId', userId); // AI USAGE
    
    const statusOfDeletion = this.http.delete<string> (
      `${this.baseURL}`, 
      {params}
    );
    return statusOfDeletion;
  }

  // ----- POST /sessions/{sessionId}/messages -----
  // IMPORTANT DIFFERENCE TO FRONTEND:
  // returns only the LLM response as a string, not as a Message
  sendAndReceiveMessage(
    sessionId: string, 
    messageContent: string
  ) : Observable<string> {
    const messageFromLLM = this.http.post<Message> (
      `${this.baseURL}/${sessionId}/messages`,
      messageContent
    ).pipe(
      map(message => message.messageContent) // AI Usage: How to access Object Attributes
    );
    return messageFromLLM;
  }

  // ----- GET /sessions/{sessionId}/messages?userId={userId}  -----
  getAllMessagesForASession(
    sessionId: string, 
    userId: string
  ) : Observable<Message[]> {
    const params = new HttpParams().set('userId', userId); // AI USAGE

    const messages = this.http.get<Message[]> (
      `${this.baseURL}/${sessionId}/messages`, 
      {params}
    );
    return messages;    
  }

  // ----- POST /sessions/{sessionId}/quiz  -----
  createQuizRequest(
    sessionId: string, 
    quizRequestContent: string, 
    count: number, 
    difficulty: string) 
    : Observable<QuizRequest> {
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
    return quizRequestReceivedWithQuizItems; 
  }

  // ----- POST /sessions/{sessionId}/quiz/{quizItemId}/answer -----
  createQuizEvaluation(
    sessionId: string, 
    quizItemId: string, 
    answer: string
  ) : Observable <string> {
    const quizAnswerEvaluation = this.http.post<string> (
      `${this.baseURL}/${sessionId}/quiz/${quizItemId}/answer`, 
      answer
    );
    return quizAnswerEvaluation;
  }

}
