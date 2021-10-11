package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public class ListarCompeticionesInscripcionesAbiertas {
	

	private String SQL = "SELECT idCompeticion,nombre,fechaCompeticion,organizador FROM COMPETICION WHERE plazoinscripcion > ?";
	
	private List<CompeticionDto> competiciones;
	
	Date sqlDate = new Date(new java.util.Date().getTime());
	
	

	public ListarCompeticionesInscripcionesAbiertas() {
		this.competiciones = new ArrayList<CompeticionDto>();
	}

	public List<CompeticionDto> execute() throws BusinessException {
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	

	try {
		c = Jdbc.getConnection();
		
		pst = c.prepareStatement(SQL);
		
		pst.setDate(1,sqlDate);
		
		rs = pst.executeQuery();
		
		while(rs.next()) {
			
			competiciones.add(new CompeticionDto(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getString(4)));
			
		}
		
		
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	finally {
		Jdbc.close(rs, pst, c);
	}
	return competiciones;
}
	

}
