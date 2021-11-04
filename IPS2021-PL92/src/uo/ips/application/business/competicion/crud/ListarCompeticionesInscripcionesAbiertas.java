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
	


	private String SQL = "SELECT idCompeticion,nombre,fechaCompeticion,organizador,plazoInicioInscripcion, plazoFinInscripcion, tipoCompeticion, distanciaKm, plazasDisponibles,cuota"+
	" FROM Competicion WHERE plazoInicioInscripcion < curdate() and plazoFinInscripcion > curdate()";
	
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
			
			competiciones.add(new CompeticionDto(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getString(4),
					rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getInt(8), rs.getInt(9),rs.getDouble(10)));
			
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
