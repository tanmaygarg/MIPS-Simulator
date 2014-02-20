package edu.ufl.cda5155;

//On my honor, I have neither given nor received unauthorized aid on this assignment
//Tanmay Garg
import java.io.FileNotFoundException;
import java.io.IOException;

public class MIPSsim {

	//public static String FILE_NAME = "testcase1.txt";
	public static String OUTPUT2 = "simulation.txt";
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Disassembly input = new Disassembly();
		input.fileInput(args[0]);		
		Simulator simulator = new Simulator();
		simulator.fileInput("disassembly.txt");
		simulator.simulate();

	}
}