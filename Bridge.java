// Bridge.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

// Manage semaphore and NEON

import java.util.concurrent.Semaphore;

import javax.print.DocFlavor.SERVICE_FORMATTED;

public class Bridge {
    Semaphore semaphore;
    private int NEON;
    private int steps;
    boolean crossed = false;
    // Return NEON
    public int getNEON()
    {
        return NEON;
    }
    // Set NEON value
    public void setNEON(int NEON)
    {
        this.NEON = NEON;
    }
    // Bridge constructor
    public Bridge()
    {
        semaphore = new Semaphore(1, true);
        NEON = 0;
        steps = 0;
    }
    // Function for crossing the bridge
    public void crossBridge(Farmer farmer)
    {
        // Loop while NEON is less than 100
        while(NEON < 100)
        {
            // Acquire semaphore
            System.out.println(farmer.getID() + ": Waiting for bridge. Going towards " + farmer.getIsland());
            try {
                semaphore.acquire();
            } catch (InterruptedException e) { }
            // Loop until farmer has crossed the bridge
            while(crossed != true)
            {  
                // If NEON is greater/equal to 100, release (program finished)
                if(NEON >= 100)
                {
                    semaphore.release();
                    return;
                }
                // 20ms delay between every 5 steps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) { }
                // Check for each steps
                if(steps == 0)
                {
                    steps = 5;
                    System.out.println(farmer.getID() + ": Crossing bridge Step 5.");
                }
                else if(steps == 5)
                {
                    steps = 10;
                    System.out.println(farmer.getID() + ": Crossing bridge Step 10.");
                }
                else if(steps == 10)
                {
                    steps = 15;
                    System.out.println(farmer.getID() + ": Crossing bridge Step 15.");
                }
                else if(steps == 15)
                {
                    System.out.println(farmer.getID() + ": Across the bridge");
                    NEON++;
                    crossed = true;
                    // Set direction of farmers once they have crossed (new island will be opposite to island they are now on)
                    if(farmer.getID().startsWith("N") && farmer.getIsland() == "South")
                    {
                        farmer.setIsland("North");
                    }
                    else if(farmer.getID().startsWith("N") && farmer.getIsland() == "North")
                    {
                        farmer.setIsland("South");
                    }
                    else if(farmer.getID().startsWith("S") && farmer.getIsland() == "South")
                    {
                        farmer.setIsland("North");
                    }
                    else if(farmer.getID().startsWith("S") && farmer.getIsland() == "North")
                    {
                        farmer.setIsland("South");
                    }
                }
                // If farmer has crossed, release semaphore
                if(crossed == true)
                {
                    steps = 0;
                    semaphore.release();
                }
            }
            crossed = false;
            System.out.println("NEON: " + NEON);
        }
    }
}