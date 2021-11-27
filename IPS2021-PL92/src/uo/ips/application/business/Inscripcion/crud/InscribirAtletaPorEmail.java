package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;

public class InscribirAtletaPorEmail {

	
	private String SQLGetId = "SELECT idAtleta FROM Atleta WHERE email = ?";
	private String SQLGetCompeticion = "SELECT idCompeticion FROM Competicion WHERE idCompeticion=?";
	private int  idCompeticion;
	private String email;
	private int idCategoria;
	private int idAtleta;
	private String club = "";
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public InscribirAtletaPorEmail(String email, int idCompeticion) {
		this.idCompeticion = idCompeticion;
		this.email = email;
		try {
			c = Jdbc.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public InscribirAtletaPorEmail(String email, int idCompeticion, Connection c) {
		this.idCompeticion = idCompeticion;
		this.email = email;
		this.c = c;
	}
	
	public InscribirAtletaPorEmail(String email, int idCompeticion, Connection c, String club) {
		this(email,idCompeticion,c);
		this.club = club;
	}

	public void execute() throws BusinessException {
		// Process
		
		try {
			

			pst = c.prepareStatement(SQLGetCompeticion);
			pst.setInt(1, idCompeticion);
			ResultSet competicionRs = pst.executeQuery();
			
			if(!competicionRs.next())
				throw new BusinessException("La competiciÑn no existe");
			
			pst = c.prepareStatement(SQLGetId);
			pst.setString(1, email);
			

			ResultSet emailRs = pst.executeQuery();
			
			if(emailRs.next())
				idAtleta= emailRs.getInt(1);
		
			

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
		
		this.idCategoria = BusinessFactory.forAtletaCrudService().CalcularCategoria(idAtleta, idCompeticion);
		
		if(this.club.isBlank()) {
			new InscribirAtleta(idCompeticion,idAtleta,idCategoria,  c).execute();
		}else {
			
		
			new InscribirAtleta(idCompeticion,idAtleta,idCategoria,  c,this.club).execute();
			
			
		}
		
		
		
		
		
	}
	
}

