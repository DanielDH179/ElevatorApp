package storybuilding.calls;

import storybuilding.ElevatorException;

public class NoCallsException extends ElevatorException {
    
    public NoCallsException() {
    }
    
    public NoCallsException(String message) {
        super(message);
    }
    
    public NoCallsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoCallsException(Throwable cause) {
        super(cause);
    }
    
    public NoCallsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}