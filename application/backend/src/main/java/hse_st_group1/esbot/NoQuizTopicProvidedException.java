package hse_st_group1.esbot;

public class NoQuizTopicProvidedException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public NoQuizTopicProvidedException(final String errorMessage){
        super(errorMessage);
    }
}
