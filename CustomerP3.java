// CustomerP3.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.io.*;
import java.util.*;

public class CustomerP3 extends Thread implements Comparable<CustomerP3> {
    // Info - Arrival-time, Customer-ID, Ice-cream-eating-time
    int arrivalTime;
    String id;
    int eatingTime;
    // When the customer seats
    int seated;
    // Time customer leaves
    int leaveTime;
    // Parlour
    ParlourP3 parlour;
    // If customer is seated
    boolean isSeated;
    // Customer constructor
    public CustomerP3(int arrivalTime, String id, int eatingTime, ParlourP3 parlour, boolean isSeated)
    {
        this.arrivalTime = arrivalTime;
        this.id = id;
        this.eatingTime = eatingTime;
        this.parlour = parlour;
        this.isSeated = isSeated;
    }
    // Used for sorting array list by order of arrival time
    @Override
    public int compareTo(CustomerP3 c) {
        return Integer.valueOf(this.getArrivalTime()).compareTo(Integer.valueOf(c.getArrivalTime()));
    }
    // Get arrival time
    public int getArrivalTime()
    {
        return arrivalTime;
    }
    // Boolean to check if customer is seated
    public void setIsSeated(boolean isSeated)
    {
        this.isSeated = isSeated;
    }
    // Get customer id
    public String getID()
    {
        return id;
    }
    // Get eating time
    public int getEatingTime()
    {
        return eatingTime;
    }
    // Get time seated
    public int getSeated()
    {
        return seated;
    }
    // Return leave time
    public int getLeaveTime()
    {
        return leaveTime;
    }
    // Set time customer sits in parlour
    public void setSeated(int seated)
    {
        this.seated = seated;
    }
    // Set time customer leaves
    public void setLeaveTime(int leaveTime)
    {
        this.leaveTime = leaveTime;
    } 
    // Run threads
    public void run()
    {
        // Check if the parlour is full
        if(parlour.isFull() == true)
        {
            // Block incoming threads/customers until notified when 0 customers sitting down
            parlour.trySeat();
            this.setIsSeated(true);
            parlour.setSeated();
            // Set time customer sits down
            this.setSeated(parlour.getTime());
            // Set the time the customer will leave
            this.setLeaveTime(this.getSeated() + this.getEatingTime());
            // Loop until parlour is empty
            while(parlour.getSeated() != 0)
            {
                try { Thread.sleep(50); } catch (final InterruptedException e) { }
                // Check if the current time is equal to the time the customer leaves
                if(parlour.getTime() == this.getLeaveTime())
                {
                    try { Thread.sleep(50); } catch (final InterruptedException e) { }
                    // Reduce seated count
                    parlour.setLeftSeat();
                    this.setIsSeated(false);
                }
            }
        }
        // If parlour isnt full, set times as normal
        else
        {
            this.setIsSeated(true);
            parlour.setSeated();
            // Set time customer sits down
            this.setSeated(parlour.getTime());
            // Set the time the customer will leave
            this.setLeaveTime(this.getSeated() + this.getEatingTime());
        }
        // Check if 5 customers are seated
        if(parlour.getSeated() == 5)
        {
            parlour.setIsFull(true);
            // Loop until all customers have left
            while(parlour.seated() != 0)
            {
                try { Thread.sleep(50); } catch (final InterruptedException e) { }
                // Check if the current time is equal to the time the customer leaves
                if(parlour.getTime() == this.getLeaveTime())
                {
                    try { Thread.sleep(50); } catch (final InterruptedException e) { }
                    // Reduce seated count
                    parlour.setLeftSeat();
                    this.setIsSeated(false);
                }
            }
            // Once all customers have left, wake monitor for next threads
            parlour.customerLeave();
            parlour.setIsFull(false);
        }
    }
}