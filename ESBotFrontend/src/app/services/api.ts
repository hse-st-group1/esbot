import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Api {
  private readonly baseURL = 'http://localhost:8080/sessions';

  constructor(private readonly http: HttpClient) {}

  // POST /sessions/
  createSession(userId: string) : Observable<string> {
    const newSessionId = this.http.post<string> (
      `${this.baseURL}`, 
      userId // String should be same Format as UUID
    );
    return newSessionId;
  }

  // GET /sessions/?userId={userId}
  getSessions(userId: string) : Observable<string[]> {

    const params = new HttpParams().set('userId', userId); // AI USAGE: 
    // Asked for explaination how request paramters are passed/used 
    // for api calls based on backend api endpoint header
    
    const allSessionsForUser = this.http.get<string[]> (
      `${this.baseURL}`, 
      {params}
    );
    return allSessionsForUser;
  }



}
