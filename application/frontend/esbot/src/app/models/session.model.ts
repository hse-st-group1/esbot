import { Message } from "./message.model";
import { QuizRequest } from "./quizRequest.model";

export interface Session {
  sessionID: string,
  userID: string,
  sessionTitle: string;
  startedAt: string,
  lastAccessed: string,
  messages: Message[],
  quizRequests: QuizRequest[]
}