// ParlourP3.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.sql.Time;
import java.io.*;
import java.util.*;

public class ParlourP3 {
    // Main monitor variable
    int monitor;
    int numberSeated;
    int seatedTime;
    int time;
    boolean isFull = false;
    Queue<CustomerP3> tempQueue = new LinkedList<CustomerP3>();
    // Parlour constructor
    public ParlourP3()
    {
        this.monitor = 0;
        this.time = 0;
        this.numberSeated = 0;
    }
    // Return number of permits available
    public int seated()
    {
        return monitor;
    }
    // Block threads
    public synchronized void trySeat()
    {
        try {
            wait();
        } catch (final InterruptedException e) { }
    }
    // Awaken all threads
    public synchronized void customerLeave()
    {
        notifyAll();
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
        monitor++;
    }
    // Decrement total seated
    public void setLeftSeat()
    {
        numberSeated--;
        monitor--;
    } 
    // Check if parlour is full
    public boolean isFull()
    {
        return isFull;
    }
    // Set whether parlour is full
    public void setIsFull(boolean isFull)
    {
        this.isFull = isFull;
    }
    // Loop to track current time and start threads
    public void enterParlour(Queue<CustomerP3> queue)
    {   
        // Loop until queue is empty
        while(queue.size() != 0)
        {
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
        // If customers still remain, loop and increment time until monitor equals 0
        while(this.seated() != 0)
        {
            try { Thread.sleep(1000); } catch (final InterruptedException e) { }
            time++;
        }
        // Print out customers and times
        if(this.seated() == 0)
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
