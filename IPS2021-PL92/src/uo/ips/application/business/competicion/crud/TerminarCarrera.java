package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class TerminarCarrera {
	
	private String SQL_TerminarCarrera = "UPDATE Competicion set estado = 'TERMINADA' where idCompeticion = ?";
	
	
	private int idCompeticion;
	
	public TerminarCarrera(int idCompeticion) {
		Argument.isTrue(idCompeticion >0);
		this.idCompeticion = idCompeticion;
	}
	
	public void execute() throws BusinessException {
		
		// Process
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_TerminarCarrera);
			pst.setInt(1, idCompeticion);
			
			rs = pst.executeQuery();
			
			
			pst.executeUpdate();			

			
		} catch (SQLException e) {
			throw new BusinessException("Error al registrar carera como terminada");
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
	
		
	}

}
