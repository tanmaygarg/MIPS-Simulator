package edu.ufl.cda5155;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MIPSsim {

	public static String FILE_NAME = "testcase1.txt";
	public static String OUTPUT2 = "simulation.txt";
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Disassembly input = new Disassembly();
		input.fileInput(FILE_NAME);		
		Simulator simulator = new Simulator();
		simulator.fileInput("disassembly.txt");
		simulator.simulate();

	}
}
