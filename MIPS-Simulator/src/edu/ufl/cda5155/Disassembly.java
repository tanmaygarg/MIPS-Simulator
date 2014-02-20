package edu.ufl.cda5155;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Disassembly {

	public static int breakFlag = 0;
	public static int memoryAddress = 124;
	BufferedWriter writer;
	// Reading the input file
	public void fileInput(String file) throws FileNotFoundException, IOException{

		String line;
		BufferedReader br = new BufferedReader(new FileReader(file));
		writer = new BufferedWriter
			    (new OutputStreamWriter(new FileOutputStream("disassembly.txt"),"UTF-8"));
		while ((line = br.readLine()) != null) {
			String opcode = null;
			String lmb = line.substring(0, 3);
			switch(category(lmb))
			{
			case 1: opcode = line.substring(3,6);			
			break;
			case 2: 
			case 3: opcode = line.substring(13,16);
			break;
			}
			instruction(opcode,category(lmb),line);
		}

		br.close();
		writer.close();
	}

	// Returns the category
	public Integer category(String lmb){
		int category=0;

		if(lmb.equals("000"))
		{
			category = 1;
		}
		else if(lmb.equals("110"))
		{			
			category = 2;
		}
		else if(lmb.equals("111"))
		{			
			category = 3;
		}

		return category;

	}
	// Returns the instruction based on opcode and category
	public String instruction(String opcode, int category, String line) throws IOException{
		String instruction= null;
		String offset;
		int offset1;
		int rs;
		int rt;
		int rd;
		int immediate_value;
		 
		if(breakFlag==1){
			memoryAddress+=4;
			String temp= ""+signedBinary(line.toString());
			writer.write(line+"\t"+memoryAddress+"\t"+temp);
			writer.newLine();
		}
		else if(category==1)
		{
			switch(opcode)
			{
			case "000": instruction = "J";memoryAddress+=4;
			offset = line.substring(6);		
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" "+"#"+leftShiftBy2(offset));writer.newLine();
			break;
			case "010": instruction = "BEQ";memoryAddress+=4;rs=Integer.parseInt(line.substring(6, 11),2);rt= Integer.parseInt(line.substring(11, 16),2);offset = line.substring(16);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rs+", R"+rt+", #"+signedBinary(leftShiftBy2ReturnBin(offset)));writer.newLine();
			break;
			case "100": instruction = "BGTZ";memoryAddress+=4;rs=Integer.parseInt(line.substring(6, 11),2);offset = line.substring(16);			
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rs+", #"+signedBinary(leftShiftBy2ReturnBin(offset)));writer.newLine();
			break;
			case "101": instruction = "BREAK";breakFlag=1;memoryAddress+=4;writer.write(line+"\t"+memoryAddress+"\t"+instruction);writer.newLine();break;
			case "110": instruction = "SW";memoryAddress+=4;rs=Integer.parseInt(line.substring(6, 11),2);rt= Integer.parseInt(line.substring(11, 16),2);offset1 = signedBinary(line.substring(16));			
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", "+offset1+"(R"+rs+")");writer.newLine();
			break;
			case "111": instruction = "LW";memoryAddress+=4;rs=Integer.parseInt(line.substring(6, 11),2);rt= Integer.parseInt(line.substring(11, 16),2);offset1 = signedBinary(line.substring(16));
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", "+offset1+"(R"+rs+")");writer.newLine();
			break;
			}
		}
		else if(category==2)
		{
			switch(opcode)
			{
			case "000": instruction = "ADD";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "001": instruction = "SUB";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "010": instruction = "MUL";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "011": instruction = "AND";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "100": instruction = "OR";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "101": instruction = "XOR";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			case "110": instruction = "NOR";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);rd= Integer.parseInt(line.substring(16, 21),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rd+", R"+rs+", R"+rt);writer.newLine();
			break;
			}
		}
		else if(category==3)
		{
			switch(opcode)
			{
			case "000": instruction = "ADDI";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);immediate_value = signedBinary(line.substring(16));
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", R"+rs+", #"+immediate_value);writer.newLine();
			break;
			case "001": instruction = "ANDI";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);		immediate_value = Integer.parseInt(line.substring(16),2);	
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", R"+rs+", #"+immediate_value);writer.newLine();
			break;
			case "010": instruction = "ORI";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);immediate_value = Integer.parseInt(line.substring(16),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", R"+rs+", #"+immediate_value);writer.newLine();
			break;
			case "011": instruction = "XORI";memoryAddress+=4;rs=Integer.parseInt(line.substring(3, 8),2);rt= Integer.parseInt(line.substring(8, 13),2);immediate_value = Integer.parseInt(line.substring(16),2);
			writer.write(line+"\t"+memoryAddress+"\t"+instruction+" R"+rt+", R"+rs+", #"+immediate_value);writer.newLine();
			break;

			}
		}
		return instruction;

	}

	public int leftShiftBy2(String offset){
		int res;
		if(offset==null) return 0;
		StringBuffer s = new StringBuffer(offset);
		s.append("00");
		res = Integer.parseInt(s.toString(),2);
		return res;
	}
	public String leftShiftBy2ReturnBin(String offset){
		String res=null;
		if(offset==null) return null;
		StringBuffer s = new StringBuffer(offset);
		s.append("00");
		res = s.toString();
		return res;
	}
	
	public int signedBinary(String offset){
		int offsetInt;
		if(offset==null) return 0;
		if(offset.startsWith("1")){
			StringBuffer buff = new StringBuffer(offset);
			for(int i=0;i<buff.length();i++)
			{
				switch(buff.charAt(i)) {
				case '0': buff.setCharAt(i, '1');
				; break;
				case '1': buff.setCharAt(i, '0');break;
				}
			}
			offsetInt=Integer.parseInt(buff.toString(), 2);
			offsetInt+=1;
			offsetInt=-offsetInt;
		}
		else 
			offsetInt = Integer.parseInt(offset,2);
		return offsetInt;
	}

}