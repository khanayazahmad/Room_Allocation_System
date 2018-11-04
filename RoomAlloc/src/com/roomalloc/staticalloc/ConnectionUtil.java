package com.roomalloc.staticalloc;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class ConnectionUtil {
	Statement stmt;
	Connection conn;
	ResultSet rst;
	ConnectionUtil(){
		try{
			System.out.println("Connection created...");
			Class.forName("com.mysql.jdbc.Driver");
			conn=(Connection) DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/campus_mind_info","root","Welcome123");
			stmt=(Statement) conn.createStatement();
			}catch(Exception e){
				System.out.println("Database Connection Error");
			}
	}
	void exec(String s) throws SQLException{
		rst=stmt.executeQuery(s);
	}
	ResultSet getResult(){
		return rst;
	}
	
	public void createTable() throws SQLException{
		
		  String createString =
			        "create table " + "campus_mind_info" +
			        ".SUPPLIERS " +
			        "(SUP_ID integer NOT NULL, " +
			        "SUP_NAME varchar(40) NOT NULL, " +
			        "STREET varchar(40) NOT NULL, " +
			        "CITY varchar(20) NOT NULL, " +
			        "STATE char(2) NOT NULL, " +
			        "ZIP char(5), " +
			        "PRIMARY KEY (SUP_ID))";

			    Statement stmt = null;
			    try {
			        stmt = (Statement) conn.createStatement();
			        stmt.executeUpdate(createString);
			    } catch (SQLException e) {
			        e.printStackTrace();;
			    } finally {
			        if (stmt != null) { stmt.close(); }
			    }
	}
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		ConnectionUtil dc=new ConnectionUtil();
		dc.createTable();
	}

}
