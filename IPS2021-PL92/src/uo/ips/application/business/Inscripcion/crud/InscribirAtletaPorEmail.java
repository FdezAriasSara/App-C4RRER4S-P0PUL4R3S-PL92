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
	private int  idCompeticion;
	private String email;
	private int idCategoria;
	private int idAtleta;
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public InscribirAtletaPorEmail(String email, int idCompeticion) {
		this.idCompeticion = idCompeticion;
		this.email = email;
	}

	public void execute() throws BusinessException {
		// Process

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQLGetId);
			pst.setString(1, email);
			

			ResultSet emailRs = pst.executeQuery();
			
			if(emailRs.next())
				idAtleta= emailRs.getInt(1);
			
			

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		
		
		this.idCategoria = BusinessFactory.forAtletaCrudService().CalcularCategoria(idAtleta, idCompeticion);
		
		
		new InscribirAtleta(idCompeticion,idAtleta,idCategoria).execute();
		
		
		
		
	}

}

