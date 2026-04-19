package hse_st_group1.esbot.services;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.repository.MessageRepository;
import jakarta.transaction.Transactional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AIService aiService;

    public MessageService(MessageRepository messageRepository, AIService aiService){
        this.messageRepository = messageRepository;
        this.aiService = aiService;
    }
    
    @Transactional
    public Message sendMessage(Message message){
        //Send to be implemented
        messageRepository.save(message);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Message response = new Message();
        response.setMessageType("Message");
        response.setSender(true);
        response.setTimestamp(timestamp);
        response.setSession(message.getSession());

        if (aiService.isAvailable()) {
            String aiResponse = aiService.responseString(message.getMessageContent());
            response.setMessageContent(aiResponse);
        } else {
            throw new AIServiceUnavailableException("Error: Message service is currently unavailable");
        }

        return messageRepository.save(response);
    }
}
