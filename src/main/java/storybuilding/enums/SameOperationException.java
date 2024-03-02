package storybuilding.enums;

import storybuilding.ElevatorException;

public class SameOperationException extends ElevatorException {
    
    public SameOperationException() {
    }
    
    public SameOperationException(String message) {
        super(message);
    }
    
    public SameOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SameOperationException(Throwable cause) {
        super(cause);
    }
    
    public SameOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}