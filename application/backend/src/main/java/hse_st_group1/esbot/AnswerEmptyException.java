package hse_st_group1.esbot;

public class AnswerEmptyException extends RuntimeException{
    public AnswerEmptyException(String errorMessage){
        super(errorMessage);
    }
}
