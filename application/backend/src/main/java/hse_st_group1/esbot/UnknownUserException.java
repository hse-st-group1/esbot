package hse_st_group1.esbot;

public class UnknownUserException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UnknownUserException(final String errorMessage){
        super(errorMessage);
    }
}
