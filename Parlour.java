// Parlour.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.sql.Time;
import java.util.concurrent.Semaphore;
import java.io.*;
import java.util.*;

public class Parlour {
    Semaphore semaphore;
    int numberSeated;
    int seatedTime;
    int time;
    Queue<Customer> tempQueue = new LinkedList<Customer>();
    // Parlour constructor
    public Parlour()
    {
        this.semaphore = new Semaphore(5, true);
        this.time = 0;
        this.numberSeated = 0;
    }
    // Return number of permits available
    public int seated()
    {
        return this.semaphore.availablePermits();
    }
    // Acquire semaphore - customer sits down
    public void trySeat()
    {
        try {
            semaphore.acquire();
        } catch (final InterruptedException e) { }
    }
    // Release semaphore - customer leaves
    public void customerLeave()
    {
        semaphore.release();
    }
    // Get current time
    public int getTime()
    {
        return time;
    }
    // Return seated
    public int getSeated()
    {
        return numberSeated;
    }
    // Increment total seated
    public void setSeated()
    {
        numberSeated++;
    }
    // Decrement total seated
    public void setLeftSeat()
    {
        numberSeated--;
    }  
    // Loop to track current time and start threads
    public void enterParlour(Queue<Customer> queue)
    {   
        // Loop until queue is empty
        while(queue.size() != 0)
        {
            //System.out.println("queue size" + queue.size());
            while(queue.peek().getArrivalTime() == time)
            {
                // Start thread and add to temp queue for later printing, then remove from queue
                queue.peek().start();
                tempQueue.add(queue.peek());
                queue.remove();
                // Break out of loop if queue is empty (otherwise error because queue is empty)
                if(queue.size() == 0)
                {
                    break;
                }
            }
            try { Thread.sleep(1000); } catch (final InterruptedException e) { }
            time++;
        }
        // If customers still remain, loop and increment time until permits are reset to 5
        while(this.seated() != 5)
        {
            try { Thread.sleep(1000); } catch (final InterruptedException e) { }
            time++;
        }
        // Print out customers and times
        if(this.seated() == 5)
        {
            System.out.println("Customer" + String.format("%3c", ' ') + "Arrives" + String.format("%5c", ' ') + "Seats" + String.format("%4c", ' ') + "Leaves");
            while(tempQueue.size() != 0)
            {
                System.out.println(String.format("%-11s %-10d %-8d %-6d", tempQueue.peek().getID(), tempQueue.peek().getArrivalTime(), tempQueue.peek().getSeated(), tempQueue.peek().getLeaveTime()));
                tempQueue.remove();
            }
        }
    }
}
