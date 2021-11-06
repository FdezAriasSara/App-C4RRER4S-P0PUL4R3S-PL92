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

public class ListarCompeticionesInscripcionesAbiertas {
	


	private String SQL = "SELECT c.*, p.fechaInicio, p.fechaFin, p.cuota"+
	" FROM Competicion as c, Plazos as p WHERE p.fechaInicio <= curdate() and p.fechaFin >= curdate() and p.idCompeticion = c.idCompeticion";
	

	
	private List<CompeticionDto> competiciones;
	
	
	

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
		
		rs = pst.executeQuery();
		
		while(rs.next()) {
			
			competiciones.add(new CompeticionDto(rs.getInt("idCompeticion"), rs.getString("nombre"),
					rs.getDate("fechaCompeticion"), rs.getString("organizador"),
					rs.getDate("fechaInicio"), rs.getDate("fechaFin"), 
					rs.getString("tipoCompeticion"), rs.getInt("distanciaKm"), rs.getInt("plazasDisponibles"),rs.getDouble("cuota")));
			
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
