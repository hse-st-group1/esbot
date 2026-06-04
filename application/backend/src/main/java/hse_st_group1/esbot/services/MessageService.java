package hse_st_group1.esbot.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.repository.MessageRepository;
import jakarta.transaction.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AIService aiService;

    public MessageService(final MessageRepository messageRepository, final AIService aiService){
        this.messageRepository = messageRepository;
        this.aiService = aiService;
    }
    
    @Transactional
    public Message sendMessage(final Message message){
        //Send to be implemented
        messageRepository.save(message);

        final Message response = new Message();
        response.setMessageType("Message");
        response.setSender(true);
        response.setTimestamp(Instant.now());
        response.setSession(message.getSession());

        if (aiService.isAvailable()) {
            final String aiResponse = aiService.responseString(message.getMessageContent());
            response.setMessageContent(aiResponse);
        } else {
            throw new AIServiceUnavailableException("Error: Message service is currently unavailable");
        }
        messageRepository.save(response);
        return response;
    }
}
