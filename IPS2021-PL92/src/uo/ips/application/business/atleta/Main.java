package uo.ips.application.business.atleta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import alb.util.jdbc.Jdbc;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		Connection con = Jdbc.getConnection();
		Statement st = con.createStatement();
		
		 boolean valid = con.isValid(50000);
         System.out.println(valid ? "TEST OK" : "TEST FAIL");
	}

}
