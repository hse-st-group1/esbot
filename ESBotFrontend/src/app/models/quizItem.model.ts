import { QuizItemAnswer } from "./quizItemAnswer.model";
import { QuizItemAnswerEvaulation } from "./quizItemAnswerEvaluation.model";

export interface QuizItem {
  quizItemID: string,
  quizRequestID: string,
  question: string,
  quizAnswers: QuizItemAnswer[],
  quizEvaluations: QuizItemAnswerEvaulation[]
}