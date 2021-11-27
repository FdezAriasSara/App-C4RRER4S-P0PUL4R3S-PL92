package uo.ips.application.business.arco.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.arco.ArcoDto;
import uo.ips.application.business.atleta.AtletaDto;

public class GetArcos {
	private static final String SQL ="SELECT * FROM Arco WHERE idCompeticion = ?";
	int idCompeticion;
	
	public GetArcos(int idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public List<ArcoDto> execute() {

		// Process
		List<ArcoDto> list = new ArrayList<ArcoDto>();
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArcoDto arco;
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL);
			pst.setInt(1, idCompeticion);
		
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				arco = new ArcoDto();
				arco.idArco = rs.getInt(1);
				arco.km = rs.getDouble(2);
				arco.idCompeticion = rs.getInt(3);
				arco.name = rs.getString(4);
				list.add(arco);
			}

			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
		
		return list;
	}

}
