package com.ef;

import java.util.Scanner;

public class Parser {
	public static void main(String [] args)
	{
		System.out.println("Hello from Parser!!!");
		
		promptEnterKey();
		System.exit(0);
	}
	
	private static void promptEnterKey(){
	   System.out.println("Press \"ENTER\" to exit...");
	   Scanner scanner = new Scanner(System.in);
	   scanner.nextLine();
	   scanner.close();
	}
}
