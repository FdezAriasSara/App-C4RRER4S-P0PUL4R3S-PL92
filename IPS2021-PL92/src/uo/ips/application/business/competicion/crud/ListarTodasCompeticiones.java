package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public class ListarTodasCompeticiones {
	

	private String SQL = "SELECT c.*"+
	" FROM Competicion as c";
	

	
	private List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();
	
	
	

	public List<CompeticionDto> execute() throws BusinessException {
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	

	try {
		c = Jdbc.getConnection();
		
		pst = c.prepareStatement(SQL);
		
		rs = pst.executeQuery();
		
		while(rs.next()) {
			
			competiciones.add(new CompeticionDto(rs.getInt("idCompeticion"), rs.getString("nombre"),
					rs.getDate("fechaCompeticion"), rs.getString("organizador"),null,null,
					rs.getString("tipoCompeticion"), rs.getInt("distanciaKm"), rs.getInt("plazasDisponibles"), 0.0));
			
		}
		
		
	} catch (SQLException e) {
		throw new BusinessException(e);
	}
	finally {
		Jdbc.close(rs, pst, c);
	}
	return competiciones;
	}
}
