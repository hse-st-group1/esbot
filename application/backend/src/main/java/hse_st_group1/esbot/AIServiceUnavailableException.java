package hse_st_group1.esbot;

public class AIServiceUnavailableException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public AIServiceUnavailableException(final String errorMessage){
        super(errorMessage);
    }
}
