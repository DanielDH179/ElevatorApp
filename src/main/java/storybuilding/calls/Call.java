package storybuilding.calls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Call {
    
    private final int originFloor, destinationFloor;
    private final int GROUND_FLOOR = 0, ROOF = 10;
    
    private static final Logger logger = LoggerFactory.getLogger(Call.class);
    
    public Call(int originFloor, int destinationFloor) throws IllegalFloorException {
        checkFloors(originFloor, destinationFloor);
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
    }
    
    public int getOriginFloor() {
        return originFloor;
    }
    
    public int getDestinationFloor() {
        return destinationFloor;
    }
    
    private void checkFloors(int ... floors) throws IllegalFloorException {
        if (floors[0] == floors[1]) {
            logger.error("Origin and destination floors cannot be the same");
            throw new IllegalFloorException();
        }
        for (int floor : floors)
            if (floor < GROUND_FLOOR || floor > ROOF) {
                logger.error("Illegal floor value: {}", floor);
                throw new IllegalFloorException();
            }
    }
    
    @Override
    public String toString() {
        return "(%d, %d)".formatted(originFloor, destinationFloor);
    }
    
}