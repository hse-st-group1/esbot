package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;


import hse_st_group1.esbot.util.UnitTestHelper;
import hse_st_group1.esbot.model.QuizItem;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;

import java.util.Set;
import java.util.UUID;
import java.sql.Timestamp;
import hse_st_group1.esbot.model.QuizAnswer;

import static org.junit.jupiter.api.Assertions.*;


class EsbotQuizAnswerEntityTest{

    QuizAnswer answer = new QuizAnswer();
    
    QuizItem quizItem = UnitTestHelper.quizItemCreator();
    UUID id = UUID.randomUUID();
    Timestamp time = new Timestamp(System.currentTimeMillis());

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    void SetAndGetAnswerTest(){

        answer.setQuizAnswerID(id);
        answer.setQuizItem(quizItem);
        answer.setAnswer("Rabbit is OP");
        answer.setTimeStamp(time);

        assertEquals(id, answer.getQuizAnswerID());
        assertEquals(quizItem, answer.getQuizItem());
        assertEquals("Rabbit is OP", answer.getAnswer());
        assertEquals(time, answer.getTimeStamp());
    }

    @Test
    void constrainStuffAnswer(){
        QuizAnswer newAnswer = new QuizAnswer();
        newAnswer.setQuizItem(UnitTestHelper.quizItemCreator());
        newAnswer.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        newAnswer.setQuizAnswerID(id);

        newAnswer.setAnswer(null);
        Set<ConstraintViolation<QuizAnswer>> violations1 = validator.validate(newAnswer);
        assertFalse(violations1.isEmpty());

        newAnswer.setAnswer("");
        Set<ConstraintViolation<QuizAnswer>> violations2 = validator.validate(newAnswer);
        assertFalse(violations2.isEmpty());

        newAnswer.setAnswer("Rabbit");
        Set<ConstraintViolation<QuizAnswer>> violations3 = validator.validate(newAnswer);
        assertTrue(violations3.isEmpty());
    }
    
    @Test
    void constrainStuffTime(){
        QuizAnswer newAnswer = new QuizAnswer();
        newAnswer.setQuizItem(UnitTestHelper.quizItemCreator());
        newAnswer.setAnswer("Rabbit stuff");
        newAnswer.setQuizAnswerID(id);

        newAnswer.setTimeStamp(null);
        Set<ConstraintViolation<QuizAnswer>> violations1 = validator.validate(newAnswer);
        assertFalse(violations1.isEmpty());   

        newAnswer.setTimeStamp(new Timestamp(System.currentTimeMillis()));   
        Set<ConstraintViolation<QuizAnswer>> violations2 = validator.validate(newAnswer);
        assertTrue(violations2.isEmpty());
        }
    
    @Test
    void constrainStuffId(){
        QuizAnswer newAnswer = new QuizAnswer();
        newAnswer.setQuizItem(UnitTestHelper.quizItemCreator());
        newAnswer.setAnswer("Rabbit stuff");
        newAnswer.setTimeStamp(new Timestamp(System.currentTimeMillis()));   

        newAnswer.setQuizAnswerID(null);
        Set<ConstraintViolation<QuizAnswer>> violations1 = validator.validate(newAnswer);
        assertFalse(violations1.isEmpty());   

        newAnswer.setQuizAnswerID(id);
        Set<ConstraintViolation<QuizAnswer>> violations2 = validator.validate(newAnswer);
        assertTrue(violations2.isEmpty());
        }        

    @Test
    void constrainStuffQuizItem(){
        QuizAnswer newAnswer = new QuizAnswer();
        newAnswer.setAnswer("Rabbit stuff");
        newAnswer.setTimeStamp(new Timestamp(System.currentTimeMillis()));   
        newAnswer.setQuizAnswerID(id);        

        newAnswer.setQuizItem(null);
        Set<ConstraintViolation<QuizAnswer>> violations1 = validator.validate(newAnswer);
        assertFalse(violations1.isEmpty());   

        newAnswer.setQuizItem(UnitTestHelper.quizItemCreator());
        Set<ConstraintViolation<QuizAnswer>> violations2 = validator.validate(newAnswer);
        assertTrue(violations2.isEmpty());
        }  
    
     @Test
    void relatinshipStuffToQuizItem() {
        QuizItem uwuQuizItem = UnitTestHelper.quizItemCreator();
        QuizAnswer newAnswer = new QuizAnswer();   

        newAnswer.setQuizItem(uwuQuizItem);
        uwuQuizItem.setQuizAnswers(Set.of(newAnswer));

        assertTrue(uwuQuizItem.getQuizAnswers().contains(newAnswer));
        assertEquals(uwuQuizItem, newAnswer.getQuizItem());
    }
        
}