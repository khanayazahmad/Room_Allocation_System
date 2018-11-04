package com.roomalloc.dynamicalloc;
import java.sql.SQLException;


public class CheckDB {
	public static void main(String args[]) throws SQLException{
		ConnectionUtil cu=new ConnectionUtil();
		cu.exec("select mid from campus_data");
		int i=1;
		while(cu.rst.next()){
			if(i!=cu.rst.getInt(1))
				break;
			i++;
		}
		System.out.println("i="+i);
	}
	
}
