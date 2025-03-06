package bidgood.global.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BidGoodException extends RuntimeException{
    public final Map<String, String> validation = new HashMap<>();

    public BidGoodException(String message){
        super(message);
    }

    public BidGoodException(String message, Throwable cause){
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
