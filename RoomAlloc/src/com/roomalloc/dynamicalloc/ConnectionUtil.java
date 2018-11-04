package com.roomalloc.dynamicalloc;
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

}
