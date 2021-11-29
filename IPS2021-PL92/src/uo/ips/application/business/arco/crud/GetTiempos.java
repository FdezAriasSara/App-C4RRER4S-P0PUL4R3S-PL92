package uo.ips.application.business.arco.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.arco.ArcoDto;

public class GetTiempos {
	
	String SQL = "SELECT tiempo from ArcoTiempo where dorsal = ? And idCompeticion = ? order by idArco";
	
	int dorsal,idCompeticion;

	public GetTiempos(int dorsal, int idCompeticion) {
		this.dorsal = dorsal;
		this.idCompeticion =idCompeticion;
	}

	public List<Time> execute() {
		List<Time> tiempos = new ArrayList<Time>();
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL);
			pst.setInt(1, dorsal);
			pst.setInt(2, idCompeticion);
		
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				tiempos.add(rs.getTime(1));
			}

			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
		
		return tiempos;
	}

}
