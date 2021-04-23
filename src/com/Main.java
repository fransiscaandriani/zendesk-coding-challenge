package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            final File myObj = new File("input/testCase1.txt");
            final Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                final String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.printf("File specified does not exist.");
        }
    }
}
