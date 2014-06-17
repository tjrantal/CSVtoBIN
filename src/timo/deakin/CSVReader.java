/*
The software is licensed under a Creative Commons Attribution 3.0 Unported License.
Copyright (C) 2012 - 2014 Timo Rantalainen
*/
/*CSV file reader.
Reads first row as column headings into Vector columnHeadings
Reads remaining data as columns into columns Vector Vector where the outermost
vector is column and innermost is rows from the respective column
*/

package timo.deakin;
import java.io.*;
import java.util.*;
public class CSVReader{
	BufferedReader br;
	String strLine;
	StringTokenizer st;
	int tokenNumber;
	Vector<Double> columns;
	String separator;
	public CSVReader(String fileName,String separator){
		this.separator = separator;
		columns = new Vector<Double>();
		try {
			br = new BufferedReader( new FileReader(fileName));
			strLine = "";
			st = null;
			/*Discard column headings and other stuff, the first 7 rows*/
			for (int i = 0; i< 7;++i){
				strLine = br.readLine(); /*Read the headings row*/
			}

			
		} catch (Exception err){System.err.println("Error: "+err.getMessage());}
	}
	public Vector<Double> readLine(){
		try {
			columns.clear();
			/*Read data row*/
			if ((strLine = br.readLine()) == null){
				return null;
			}
			st = new StringTokenizer(strLine, separator);
			while(st.hasMoreTokens()){
				columns.add(Double.valueOf(st.nextToken()));
			}
			return columns;
		} catch (Exception err){
			System.err.println("Couldn't read from the file: "+err.getMessage());
			return null;
		}
	}
	
	public void close(){
		try {
			br.close();
		} catch (Exception err){
			System.err.println("Couldn't close buffered reader: "+err.getMessage());
		}
	}
}