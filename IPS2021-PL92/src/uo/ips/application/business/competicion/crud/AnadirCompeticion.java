package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;


public class AnadirCompeticion {

	private String SQL = "INSERT INTO Competicion (idCompeticion,nombre,fechaCompeticion,organizador,plazoInscripcion) VALUES ,(?,?,?,?,?)";
	private String SQLGetCompeticion = "SELECT * FROM Competicion WHERE idCompeticion = ?";
	private CompeticionDto competicion;
	
	public AnadirCompeticion(CompeticionDto competicion) {
		this.competicion = competicion;
	}

	public CompeticionDto execute() throws BusinessException {
	// Process
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	

	try {
		c = Jdbc.getConnection();
		
		pst = c.prepareStatement(SQLGetCompeticion);
		pst.setString(1, competicion.idCompeticion);
		
		rs = pst.executeQuery();
		
		while(rs.next()) {
			throw new BusinessException("Ya existe la competición");
		}
		pst.close();
		pst = c.prepareStatement(SQL);
		pst.setString(1, competicion.idCompeticion);
		pst.setString(2, competicion.nombre);
		pst.setDate(3, competicion.fechaCompeticion);
		pst.setString(4, competicion.organizador);
		pst.setDate(5, competicion.plazoInscripcion);
		
		pst.executeUpdate();			

		
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	finally {
		Jdbc.close(rs, pst, c);
	}
	return competicion;
}

}
