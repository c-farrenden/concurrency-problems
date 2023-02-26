// P3.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.io.*;
import java.util.*;

public class P3 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args[0]));
        int arrivalTime;
        String id;
        int eatingTime;
        ParlourP3 parlour = new ParlourP3();
        boolean isSeated = false;
        ArrayList<CustomerP3> customers = new ArrayList<CustomerP3>();
        // Loop through text file
        while(sc.hasNextLine())
        {
            String textID = sc.nextLine();
            String lines[] = textID.split(" ");
            // Set arrival time, id and eating time
            if(!textID.contains("END"))
            {
                arrivalTime = Integer.parseInt(lines[0]);
                id = lines[1];
                eatingTime = Integer.parseInt(lines[2]);
                // Add new customer to array list
                CustomerP3 customer = new CustomerP3(arrivalTime, id, eatingTime, parlour, isSeated);
                customers.add(customer);
            }
        }
        // Sort in order by arrival time
        Collections.sort(customers);
        // Queue for customer
        Queue<CustomerP3> queue = new LinkedList<CustomerP3>();
        // Add customers to the queue
        for(int i = 0; i < customers.size(); i++)
        {
            queue.add(customers.get(i));
        }
        // Run function to start threads
        parlour.enterParlour(queue);
    }
}