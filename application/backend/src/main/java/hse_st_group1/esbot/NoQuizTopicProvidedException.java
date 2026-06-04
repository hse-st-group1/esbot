package hse_st_group1.esbot;

public class NoQuizTopicProvidedException extends RuntimeException{
    public NoQuizTopicProvidedException(String errorMessage){
        super(errorMessage);
    }
}
