package storybuilding.enums;

import storybuilding.ElevatorException;

public class SamePriorityException extends ElevatorException {
    
    public SamePriorityException() {
    }
    
    public SamePriorityException(String message) {
        super(message);
    }
    
    public SamePriorityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SamePriorityException(Throwable cause) {
        super(cause);
    }
    
    public SamePriorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}