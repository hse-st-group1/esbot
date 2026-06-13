package hse_st_group1.esbot.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import hse_st_group1.esbot.dto.MessageDTO;
import hse_st_group1.esbot.dto.QuizAnswerDTO;
import hse_st_group1.esbot.dto.QuizEvaluationDTO;
import hse_st_group1.esbot.dto.QuizItemDTO;
import hse_st_group1.esbot.dto.QuizRequestDTO;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;

@Component
public class EntityToDTO {
    public List<MessageDTO> messagesToMessageDTOs (List<Message> messages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO( 
                message.getMessageID(), 
                message.getSession().getSessionID(), 
                message.getMessageContent(), 
                message.getTimestamp(), 
                message.getSender(), 
                message.getMessageType()
            );
            messageDTOs.add(messageDTO);
        }
        return messageDTOs;
    }

    public List<QuizRequestDTO> quizRequestsToQuizRequestDTOs (Set<QuizRequest> quizRequests) {
        List<QuizRequestDTO> quizRequestDTOs = new ArrayList<>();
        for (QuizRequest quizRequest : quizRequests) {
            QuizRequestDTO quizRequestDTO = new QuizRequestDTO(
                quizRequest.getQuizRequestContent(), 
                quizRequest.getQuizItemCount(), 
                quizRequest.getQuizItemDifficulty(),
                quizRequest.getQuizItems() != null
                    ? quizItemsToQuizItemsDTO(quizRequest.getQuizItems())
                    : null
            );
            quizRequestDTOs.add(quizRequestDTO);
        }
        return quizRequestDTOs;
    }

    public QuizRequestDTO quizRequestToQuizRequestDTO (QuizRequest quizRequest) {
        return new QuizRequestDTO(
            quizRequest.getQuizRequestContent(), 
            quizRequest.getQuizItemCount(), 
            quizRequest.getQuizItemDifficulty(),
            quizRequest.getQuizItems() != null
                    ? quizItemsToQuizItemsDTO(quizRequest.getQuizItems())
                    : null
        );
    }

    public List<QuizAnswerDTO> quizAnswersToQuizAnswerDTOs (Set<QuizAnswer> quizAnswers){
        List<QuizAnswerDTO> quizAnswerDTOs = new ArrayList<>();
        for (QuizAnswer quizAnswer : quizAnswers) {
            QuizAnswerDTO quizAnswerDTO = new QuizAnswerDTO(
                quizAnswer.getQuizAnswerID(),
                quizAnswer.getAnswer(),
                quizAnswer.getTimeStamp()
            );
            quizAnswerDTOs.add(quizAnswerDTO);
        }
        return quizAnswerDTOs;
    }

    public List<QuizEvaluationDTO> quizEvaluationsToQuizEvaluationDTOs (Set<QuizEvaluation> quizEvaluations){
        List<QuizEvaluationDTO> quizEvaluationDTOs = new ArrayList<>();
        for (QuizEvaluation quizEvaluation : quizEvaluations) {
            QuizEvaluationDTO quizEvaluationDTO = new QuizEvaluationDTO(
                quizEvaluation.getEvaluationID(), 
                quizEvaluation.getQuizAnswer().getQuizAnswerID(), 
                quizEvaluation.getEvaluation()
            );
            quizEvaluationDTOs.add(quizEvaluationDTO);
        }
        return quizEvaluationDTOs;
    }

    public List<QuizItemDTO> quizItemsToQuizItemsDTO (List<QuizItem> quizItems){
        List<QuizItemDTO> quizItemDTOs = new ArrayList<>();
        for (QuizItem quizItem : quizItems) {
            QuizItemDTO quizItemDTO = new QuizItemDTO(
                quizItem.getQuizItemID(), 
                quizItem.getQuizRequest().getQuizID(), 
                quizItem.getQuestion(), 
                quizItem.getQuizAnswers() != null
                    ? quizAnswersToQuizAnswerDTOs(quizItem.getQuizAnswers())
                    : null, 
                quizItem.getQuizEvaluations() != null
                    ? quizEvaluationsToQuizEvaluationDTOs(quizItem.getQuizEvaluations())
                    : null
            );
            quizItemDTOs.add(quizItemDTO);
        }
        return quizItemDTOs;
    }
    
}
