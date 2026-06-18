import { QuizItem } from "./quizItem.model";

export interface QuizRequest {
  quizRequestContent: string,
  count: number,
  difficulty: string,
  quizItems: QuizItem[]
}