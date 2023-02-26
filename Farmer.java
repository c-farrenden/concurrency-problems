// Farmer.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

// Threads

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

// Subclass Thread implements Runnable
public class Farmer extends Thread {
    private String id; 
    private Bridge bridge;
    private String island;
    // Farmer constructor
    public Farmer(String id, String island, Bridge bridge)
    {
        this.id = id;
        this.bridge = bridge;
        this.island = island;
    }
    // Get farmer id
    public String getID() 
    {
        return id;
    }
    // Set island
    public void setIsland(String island)
    {
        this.island = island;
    }
    // Return island
    public String getIsland()
    {
        return island;
    }
    // Run threads when start() is called
    public void run() 
    {
        bridge.crossBridge(this);
    }
}