package hse_st_group1.esbot;

public class AIServiceUnavailableException extends RuntimeException{
    public AIServiceUnavailableException(String errorMessage){
        super(errorMessage);
    }
}
