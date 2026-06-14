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
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor()
@SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.LawOfDemeter"})
public class EntityToDTO {
    public final List<MessageDTO> messagesToMessageDTOs (final List<Message> messages) {
        final List<MessageDTO> messageDTOs = new ArrayList<>();
        for (final Message message : messages) {
            final MessageDTO messageDTO = new MessageDTO( 
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

    public List<QuizRequestDTO> quizRequestsToQuizRequestDTOs (final Set<QuizRequest> quizRequests) {
        final List<QuizRequestDTO> quizRequestDTOs = new ArrayList<>();
        for (final QuizRequest quizRequest : quizRequests) {
           final QuizRequestDTO quizRequestDTO = new QuizRequestDTO(
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

    public QuizRequestDTO quizRequestToQuizRequestDTO (final QuizRequest quizRequest) {
        return new QuizRequestDTO(
            quizRequest.getQuizRequestContent(), 
            quizRequest.getQuizItemCount(), 
            quizRequest.getQuizItemDifficulty(),
            quizRequest.getQuizItems() != null
                    ? quizItemsToQuizItemsDTO(quizRequest.getQuizItems())
                    : null
        );
    }

    public List<QuizAnswerDTO> quizAnswersToQuizAnswerDTOs (final Set<QuizAnswer> quizAnswers){
        final List<QuizAnswerDTO> quizAnswerDTOs = new ArrayList<>();
        for (final QuizAnswer quizAnswer : quizAnswers) {
            final QuizAnswerDTO quizAnswerDTO = new QuizAnswerDTO(
                quizAnswer.getQuizAnswerID(),
                quizAnswer.getAnswer(),
                quizAnswer.getTimeStamp()
            );
            quizAnswerDTOs.add(quizAnswerDTO);
        }
        return quizAnswerDTOs;
    }

    public List<QuizEvaluationDTO> quizEvaluationsToQuizEvaluationDTOs (final Set<QuizEvaluation> quizEvaluations){
        final List<QuizEvaluationDTO> quizEvaluationDTOs = new ArrayList<>();
        for (final QuizEvaluation quizEvaluation : quizEvaluations) {
            final QuizEvaluationDTO quizEvaluationDTO = new QuizEvaluationDTO(
                quizEvaluation.getEvaluationID(), 
                quizEvaluation.getQuizAnswer().getQuizAnswerID(), 
                quizEvaluation.getEvaluation()
            );
            quizEvaluationDTOs.add(quizEvaluationDTO);
        }
        return quizEvaluationDTOs;
    }

    public List<QuizItemDTO> quizItemsToQuizItemsDTO (final List<QuizItem> quizItems){
        final List<QuizItemDTO> quizItemDTOs = new ArrayList<>();
        for (final QuizItem quizItem : quizItems) {
            final QuizItemDTO quizItemDTO = new QuizItemDTO(
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
