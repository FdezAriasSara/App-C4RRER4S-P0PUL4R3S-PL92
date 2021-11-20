package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ips.application.business.BusinessException;

public class IncribirClusterEmails {
	
	private int idCompeticion;
	private Connection c;
	private String[] emails;
	private int alreadyReg = 0;
	private String club;
	
	private String SQLGetId = "SELECT idAtleta FROM Atleta WHERE email = ?";
	
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public IncribirClusterEmails(int idCompeticion , Connection c, String club, String... emails) {
		this.idCompeticion = idCompeticion;
		this.c = c;
		this.emails = emails;
		this.club = club;
	}
	
	
	public int execute() {
		
		for(String email : emails) {
			
			try {
				new InscribirAtletaPorEmail(email, idCompeticion, c,this.club).execute();
				
			} catch (BusinessException | RuntimeException e) {
				alreadyReg++;
			}
			
		}
		
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.alreadyReg;
		
	}
	
}
