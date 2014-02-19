package edu.ufl.cda5155;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;

public class Simulator {

	static LinkedHashMap<Integer, String> addressOpcodeMap=new LinkedHashMap<Integer, String>();
	LinkedHashMap<String, Integer> registerMap=new LinkedHashMap<String, Integer>();
	LinkedHashMap<Integer, Integer> addressDataMap=new LinkedHashMap<Integer, Integer>();
	int address=128;
	static int dataAddress=0;
	BufferedWriter writer;

	public void fileInput(String file) throws FileNotFoundException, IOException{
		int breakF = 0;
		String line;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
			String[] values = line.split("\t");

			if(breakF==0){
				addressOpcodeMap.put(Integer.parseInt(values[1]), values[2]);
				if(values[2].equalsIgnoreCase("BREAK")){breakF=1;dataAddress=Integer.parseInt(values[1])+4;}
			}
			else if(breakF==1)
			{
				addressDataMap.put(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			}
			//System.out.println(addressOpcodeMap.get(values[1]));
		}
		for(int i=0;i<32;i++)
		{String tmp="R"+i;
		registerMap.put(tmp, 0);
		}
		br.close();
	}
	public void simulate() throws IOException{
		String[] opcode;
		int cycle=0;
		writer = new BufferedWriter
			    (new OutputStreamWriter(new FileOutputStream("simulation.txt"),"UTF-8"));;
		while(addressOpcodeMap.containsKey(address)){	
			writer.write("--------------------");
			writer.newLine();
			writer.write("Cycle:"+(++cycle)+"\t"+address+"\t"+addressOpcodeMap.get(address));
			writer.newLine();
			writer.newLine();
			writer.write("Registers");
			writer.newLine();
			writer.write("R00:"+"\t"+registerMap.get("R0")+"\t"+registerMap.get("R1")+"\t"+registerMap.get("R2")
					+"\t"+registerMap.get("R3")+"\t"+registerMap.get("R4")+"\t"+registerMap.get("R5")
					+"\t"+registerMap.get("R6")+"\t"+registerMap.get("R7"));
			writer.newLine();
			writer.write("R08:"+"\t"+registerMap.get("R8")+"\t"+registerMap.get("R9")+"\t"+registerMap.get("R10")
					+"\t"+registerMap.get("R11")+"\t"+registerMap.get("R12")+"\t"+registerMap.get("R13")
					+"\t"+registerMap.get("R14")+"\t"+registerMap.get("R15"));
			writer.newLine();
			writer.write("R16:"+"\t"+registerMap.get("R16")+"\t"+registerMap.get("R17")+"\t"+registerMap.get("R18")
					+"\t"+registerMap.get("R19")+"\t"+registerMap.get("R20")+"\t"+registerMap.get("R21")
					+"\t"+registerMap.get("R22")+"\t"+registerMap.get("R23"));
			writer.newLine();
			writer.write("R24:"+"\t"+registerMap.get("R24")+"\t"+registerMap.get("R25")+"\t"+registerMap.get("R26")
					+"\t"+registerMap.get("R27")+"\t"+registerMap.get("R28")+"\t"+registerMap.get("R29")
					+"\t"+registerMap.get("R30")+"\t"+registerMap.get("R31"));
			writer.newLine();
			writer.newLine();
			writer.write("Data");
			writer.newLine();
			int test=dataAddress;
			while(addressDataMap.containsKey(test))
			{
				writer.write(test+":");
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				test+=4;
				if(addressDataMap.containsKey(test))
				writer.write("\t"+addressDataMap.get(test));
				writer.newLine();
				test+=4;
			}
			//System.out.println(entry.getKey()+","+entry.getValue());
			opcode=addressOpcodeMap.get(address).split("\\s+");
						int value;
			switch(opcode[0])
			{
			case "ADD": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])+registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "SUB": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])-registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "MUL": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])*registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "AND": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])&registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "OR":  opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])|registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "XOR": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=registerMap.get(opcode[2])^registerMap.get(opcode[3]);
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "NOR": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			value=~(registerMap.get(opcode[2])|registerMap.get(opcode[3]));
			registerMap.put(opcode[1], value);address+=4;
			break;
			case "J":	opcode[1] = opcode[1].substring(1);
			address=Integer.parseInt(opcode[1]);
			break;
			case "BEQ": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			opcode[3] = opcode[3].substring(1);
			if(registerMap.get(opcode[1])==registerMap.get(opcode[2])){
				address=address+4+Integer.parseInt(opcode[3]);
			}
			else address+=4;
			break;
			case "BGTZ": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(1);
			if(registerMap.get(opcode[1])>0){
				address=address+4+Integer.parseInt(opcode[2]);
			}
			else address+=4;
			break;
			case "BREAK": address+=4;
			break;
			case "SW": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			int offset= Integer.parseInt(opcode[2].substring(0,3));
			String base= (opcode[2].substring(4,6));
			addressDataMap.put(registerMap.get(base)+offset, registerMap.get(opcode[1]));
			address+=4;
			break;
			case "LW": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			int offset1= Integer.parseInt(opcode[2].substring(0,3));
			String base1= (opcode[2].substring(4,6));
			registerMap.put(opcode[1], addressDataMap.get(registerMap.get(base1)+offset1));
			address+=4;
			break;
			case "ADDI": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			opcode[3] = opcode[3].substring(1);
			registerMap.put(opcode[1], (registerMap.get(opcode[2])+Integer.parseInt(opcode[3])));
			address+=4;
			break;
			case "ANDI": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			opcode[3] = opcode[3].substring(1);
			registerMap.put(opcode[1], (registerMap.get(opcode[2])&Integer.parseInt(opcode[3])));
			address+=4;
			break;
			case "ORI": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			opcode[3] = opcode[3].substring(1);
			registerMap.put(opcode[1], (registerMap.get(opcode[2])|Integer.parseInt(opcode[3])));
			address+=4;
			break;
			case "XORI": opcode[1] = opcode[1].substring(0, opcode[1].length() - 1);
			opcode[2] = opcode[2].substring(0, opcode[2].length() - 1);
			opcode[3] = opcode[3].substring(1);
			registerMap.put(opcode[1], (registerMap.get(opcode[2])^Integer.parseInt(opcode[3])));
			address+=4;
			break;
			}

		}
		writer.close();
	}
}
