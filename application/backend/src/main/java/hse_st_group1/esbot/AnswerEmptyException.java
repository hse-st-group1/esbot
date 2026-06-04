package hse_st_group1.esbot;

public class AnswerEmptyException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public AnswerEmptyException(final String errorMessage){
        super(errorMessage);
    }
}
