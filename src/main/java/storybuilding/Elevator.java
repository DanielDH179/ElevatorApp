package storybuilding;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storybuilding.calls.*;
import storybuilding.enums.*;

public class Elevator {
    
    private float motionCounter;
    private OperationType operation;
    private PriorityType priority;
    private final List<Call> original;
    private List<Call> calls;
    private boolean alreadySorted;
    private int currentFloor, index;
    
    private final float INTERVAL = 3F;
    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);
    
    public Elevator(List<Call> calls) throws NoCallsException {
        if (calls.isEmpty()) {
            logger.warn("No calls to attend");
            throw new NoCallsException();
        }
        this.calls = this.original = calls;
        logger.debug("List: {}", calls);
    }
    
    public void resetAll() {
        calls = original;
        resetCounter();
        resetFloor();
    }
    
    public void resetCounter() {
        motionCounter = 0;
    }
    
    public void resetFloor() {
        currentFloor = 0;
    }
    
    public float getMotionCounter() {
        return motionCounter;
    }
    
    public void setOperation(OperationType operation) throws SameOperationException {
        if (this.operation == operation)
            throw new SameOperationException();
        logger.info("Set operation to {}", operation);
        this.operation = operation;
    }
    
    public void setPriority(PriorityType priority) throws SamePriorityException {
        if (this.priority == priority)
            throw new SamePriorityException();
        logger.info("Set priority to {}", priority);
        this.priority = priority;
        alreadySorted = false;
    }
    
    public void attendCalls() {
        index = -1;
        while (++index < calls.size()) {
            processNextCall();
        }
    }
    
    private void processNextCall() {
        switch (priority) {
            case CLOSEST_FLOOR -> checkClosestFloor();
            case HIGHEST_FLOOR -> checkHighestFloor();
            default -> logger.error("Invalid priority type");
        }
        int origin = calls.get(index).getOriginFloor();
        int destination = calls.get(index).getDestinationFloor();
        System.out.printf("- Current: %2d, Call: %2d -> %d\n",
                          currentFloor, origin, destination);
        float timeElapsed = Math.abs(origin - destination) * INTERVAL;
        logger.info("Time elapsed: {}", timeElapsed);
        motionCounter += timeElapsed;
        currentFloor = destination;
    }
    
    private void checkClosestFloor() {
        switch (operation) {
            case SORTING -> {
                Collections.sort(calls, (c1, c2) ->
                    Math.abs(c1.getOriginFloor() - currentFloor) -
                    Math.abs(c2.getOriginFloor() - currentFloor)
                );
                logger.debug("Sorted list: {}", calls);
            }
            case SWAPPING -> {
                try {
                    Call c1 = calls.get(index), c2 = calls.get(index + 1);
                        if (Math.abs(c1.getOriginFloor() - currentFloor) >
                            Math.abs(c2.getOriginFloor() - currentFloor))
                            Collections.swap(calls, index, index + 1);
                    logger.debug("Swapped list: {}", calls);
                } catch (IndexOutOfBoundsException ex) {
                    logger.error("Reached end of list");
                }
            }
            default -> logger.error("Invalid operation type");
        }
    }
    
    private void checkHighestFloor() {
        switch (operation) {
            case SORTING -> {
                if (alreadySorted) {
                    logger.warn("List already sorted, skipping...");
                    return;
                }
                Collections.sort(calls, (c1, c2) ->
                    c2.getOriginFloor() - c1.getOriginFloor()
                );
                logger.debug("Sorted list: {}", calls);
                alreadySorted = true;
            }
            case SWAPPING -> {
                try {
                    Call c1 = calls.get(index), c2 = calls.get(index + 1);
                    if (c1.getOriginFloor() < c2.getOriginFloor())
                        Collections.swap(calls, index, index + 1);
                    logger.debug("Swapped list: {}", calls);
                } catch (IndexOutOfBoundsException ex) {
                    logger.error("Reached end of list");
                }
            }
            default -> logger.error("Invalid operation type");
        }
    }
    
}