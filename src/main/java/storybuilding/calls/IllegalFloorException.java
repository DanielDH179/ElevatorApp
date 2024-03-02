package storybuilding.calls;

import storybuilding.ElevatorException;

public class IllegalFloorException extends ElevatorException {
    
    public IllegalFloorException() {
    }
    
    public IllegalFloorException(String message) {
        super(message);
    }
    
    public IllegalFloorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IllegalFloorException(Throwable cause) {
        super(cause);
    }
    
    public IllegalFloorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}