/*
The software is licensed under a Creative Commons Attribution 3.0 Unported License.
Copyright (C) 2012-2014 Timo Rantalainen
*/

/*
A class to convert a CSV-file to .mat file
Mat file format obtained from  http://www.mathworks.com/help/pdf_doc/matlab/matfile_format.pdf on 12th of march 2012
javac WriteMat.java CSVReader.java
java WriteMat
*/
package timo.deakin;
import java.io.*;
import java.util.*;
public class CSVtoBIN{
	
	public FileOutputStream writer = null;
	
	public static void main(String[] args){
		if (args.length < 2){
			System.out.println("Please give full .csv-file and .mat file path, e.g.:");
			System.out.println("java WriteMat H:/UserData/winMigrationBU/Deakin/David/sample.csv H:/UserData/winMigrationBU/Deakin/David/test.mat");
			System.out.println("Optionally a third agrument can be given for csv-file spearator, e.g. for a tab separated file \\t");
		}
		String separator = ",";
		if (args.length > 2){
			separator = args[2];
			System.out.println("Separator changed");
		}				
		CSVReader reader = new CSVReader(args[0],separator);
		CSVtoBIN mainProgram = new CSVtoBIN(args[1]);
		Vector<Double> dataLine;
		while ((dataLine = reader.readLine())!= null){
			/*Write the data to binary file*/
			for (int i = 0; i< dataLine.size();++i){
				try{
					mainProgram.writer.write(mainProgram.putDouble(dataLine.get(i)));
				}catch (Exception err){System.out.println("Couldn't write double"+err.toString());}
			}
		}
		mainProgram.closeFile();
		reader.close();
	}

	public CSVtoBIN(String fileName){
		try{
			writer = new FileOutputStream(fileName);
		}catch (Exception err){System.out.println("Couldn't open "+err.toString());}
	}
	
	public void closeFile(){
		try{
			writer.close();
		}catch (Exception err){System.out.println("Couldn't close "+err.toString());}
	}
	
	private byte[] putDouble(double input){
		byte[] array = new byte[8];
		for (int i = 0; i < 8;++i){
			short temp =(short) ((Double.doubleToRawLongBits(input) & (255L <<(8*i)))>>8*i);
			//System.out.println(temp);
			array[i]  = (byte) temp;
		}
		return array;
	}
}