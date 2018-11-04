package com.roomalloc.dynamicalloc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBUtility {
	
	static ConnectionUtil cu = new ConnectionUtil();
	public static boolean createTables(){
		
		ConnectionUtil cu=new ConnectionUtil();
		
		try {
			cu.stmt.executeUpdate("create table room_allocation(id int,bed varchar(50) primary key)");
			cu.stmt.executeUpdate("create table campus_data(MID int,gender varchar(1),region varchar(10),college varchar(100),branch varchar(100),state varchar(20),percentage float)");
			cu.stmt.executeUpdate("create table space_config(number int)");
			cu.stmt.executeUpdate("create table block_constraints(blockNo int primary key,gender_constraint varchar(4))");
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		
		
	}
	
	public static boolean excelToCampusDataTable(String file){
		
		String s,query;
		String[] record;
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			cu.stmt.executeUpdate("truncate table campus_data");
			
			while((s=br.readLine())!=null){
				record=s.split(",");
				
				query="insert into campus_data values(\""+Integer.parseInt(record[0])+"\",\""+record[1]+"\",\""+record[2]+"\",\""+record[3]+"\",\""+record[4]+"\",\""+record[5]+"\",\""+Float.parseFloat(record[6])+"\")";
				//System.out.println(query+"\n");
				cu.stmt.executeUpdate(query);
				
				
			}
			return true;
	
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
		
		

	}
	
	public static ArrayList<Integer> getMIDList(){
		
		ArrayList<Integer> midList=new ArrayList<Integer>();
		try {
			cu.rst=cu.stmt.executeQuery("select mid from campus_data");
			while(cu.rst.next()){
				midList.add(cu.rst.getInt(1));
			}
			return midList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	/*public static String convertToKalinga(String input){
		
		String output="";
		switch(input.charAt(1)){
			case '1': output="B";
					break;
			case '2': output="E";
					break;
		}
		
		switch(input.charAt(3)){
			case '1': output+="UF";
					break;
			case '2': output+="1F";
					break;
		}
		output+="U"+input.charAt(5);
		switch(input.charAt(7)){
			case '1': output+="A";
			break;
			case '2': output+="B";
			break;
			case '3': output+="C";
			break;
			case '4': output+="D";
			break;
		
		}
		output+=input.charAt(9);
				
		
		return output;
		
	}
	
	public static void main(String args[]){
		
		System.out.println(convertToKalinga("B1F1U1R1B1"));
		
	
	}*/
	

}
