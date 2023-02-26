// P1.java
/*******************************/
// Name: Connor Farrenden
// Course: COMP2240 - Assignment 2
// Student Number: c3374676

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class P1  {
    public static void main(String[] args) throws FileNotFoundException {
        int N = 0;
        int S = 0;
        Scanner sc = new Scanner(new File(args[0]));
        // Loop through text file and set number of north and south farmers
        while(sc.hasNextLine())
        {
            String textID = sc.nextLine();
            String lines[] = textID.split("[=,]");
            if(textID.contains("N"))
            {
                N = Integer.parseInt(lines[1]);
            }
            if(textID.contains("S"))
            {
                S = Integer.parseInt(lines[3]);
            }
        }
        // Create bridge and farmers list
        Bridge bridge = new Bridge();
        ArrayList<Farmer> farmers = new ArrayList<Farmer>();
        // Loop through array of farmers and add North farmers
        for(int i = 0; i < N; i++)
        {
            farmers.add(new Farmer("N_Farmer" + (i + 1), "South", bridge));
        }
        // Loop through array of farmers and add South farmers
        for(int i = N; i < N + S; i++)
        {
            farmers.add(new Farmer("S_Farmer" + (i - N + 1), "North", bridge));
        }
        // Execute farmer threads
        for(int i = 0; i < farmers.size(); i++)
        {
            farmers.get(i).start();
        }
    }
}