// P2.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.io.*;
import java.util.*;

public class P2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args[0]));
        int arrivalTime;
        String id;
        int eatingTime;
        Parlour parlour = new Parlour();
        boolean isSeated = false;
        ArrayList<Customer> customers = new ArrayList<Customer>();
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
                Customer customer = new Customer(arrivalTime, id, eatingTime, parlour, isSeated);
                customers.add(customer);
            }
        }
        // Sort in order by arrival time
        Collections.sort(customers);
        // Queue for customer
        Queue<Customer> queue = new LinkedList<Customer>();
        // Add customers to the queue
        for(int i = 0; i < customers.size(); i++)
        {
            queue.add(customers.get(i));
        }
        // Run function to start threads
        parlour.enterParlour(queue);
    }
}