package storybuilding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import storybuilding.calls.*;
import storybuilding.enums.*;

public class Application {
    
    private static Elevator elevator;

    private static final boolean USE_DEFAULT = false, USER_INPUT = true, SHOW_DEBUG = true;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            elevator = new Elevator(
                USE_DEFAULT ? defaultList() : (
                    USER_INPUT ? userList() : randomList(10)
                )
            );
            nextScenario(OperationType.SORTING, PriorityType.HIGHEST_FLOOR);
            nextScenario(OperationType.SORTING, PriorityType.CLOSEST_FLOOR);
            nextScenario(OperationType.SWAPPING, PriorityType.HIGHEST_FLOOR);
            nextScenario(OperationType.SWAPPING, PriorityType.CLOSEST_FLOOR);
        } catch (NoCallsException ex) {
            System.out.println(ex);
        } finally {
            logger.info("Closing application...\n");
        }
    }
    
    private static List<Call> defaultList() {
        List<Call> calls = new ArrayList<>();
        try {
            // Valid elevator calls
            calls.add(new Call(3, 1));
            calls.add(new Call(7, 2));
            calls.add(new Call(2, 6));
            calls.add(new Call(10, 5));
            calls.add(new Call(4, 10));
            // Illegal elevator calls
            calls.add(new Call(8, 8));
            calls.add(new Call(-1, 4));
            calls.add(new Call(13, -2));
        } catch (IllegalFloorException ex) {
            logger.error("Tried to append an illegal call");
        }
        if (SHOW_DEBUG)
            System.out.println(calls);
        return calls;
    }
    
    private static List<Call> userList() {
        List<Call> calls = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int option, origin, destination;
        do {
            System.out.println("-".repeat(72));
            System.out.println("0 - EXIT");
            System.out.println("1 - Call elevator");
            System.out.println("2 - List of calls");
            System.out.println("-".repeat(72));
            System.out.print("Select an option: ");
            option = scan.nextInt();
            logger.info("User input: {}", option);
            switch (option) {
                case 0 -> {}
                case 1 -> {
                    System.out.print("- Floor origin: ");
                    origin = scan.nextInt();
                    System.out.print("- Floor destination: ");
                    destination = scan.nextInt();
                    try {
                        calls.add(new Call(origin, destination));
                    } catch (IllegalFloorException ex) {
                        logger.error("Tried to append an illegal call");
                    }
                }
                case 2 -> System.out.println(calls);
                default -> System.out.println("Try again");
            }
        } while (option != 0);
        return calls;
    }

    private static List<Call> randomList(int total) {
        if (total <= 0)
            logger.warn("Number of calls was negative");
        List<Call> calls = new ArrayList<>();
        Random r = new Random();
        int counter = 0;
        while (counter < total) {
            int origin = r.nextInt(0, 11), destination = r.nextInt(0, 11);
            try {
                calls.add(new Call(origin, destination));
                counter++;
            } catch (IllegalFloorException ex) {
                logger.error("{}: ({}, {})", counter, origin, destination);
            }
        }
        if (SHOW_DEBUG)
            System.out.println(calls);
        return calls;
    }
    
    private static void nextScenario(OperationType operation, PriorityType priority) {
        System.out.println("-".repeat(72));
        if (SHOW_DEBUG)
            System.out.printf("Operation: %s, Priority: %s\n", operation, priority);
        try {
            elevator.setOperation(operation);
        } catch (SameOperationException ex) {
            logger.warn("Operation already set to {}", operation);
        }
        try {
            elevator.setPriority(priority);
        } catch (SamePriorityException ex) {
            logger.warn("Priority already set to {}", priority);
        }
        elevator.attendCalls();
        System.out.printf("Total time elapsed: %.3f seconds\n", elevator.getMotionCounter());
        elevator.resetAll();
    }
    
}
