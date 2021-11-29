package uo.ips.application.business.arco.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.arco.ArcoDto;

public class GetAllTiempos {
	
	String SQL = "SELECT dorsal,tiempo from ArcoTiempo where idCompeticion = ? order by idArco";
	
	int idCompeticion;

	public GetAllTiempos(int idCompeticion) {
		this.idCompeticion =idCompeticion;
	}

	public HashMap<Integer, List<Time>> execute() {
		HashMap<Integer,List<Time>> tiempos = new HashMap<Integer, List<Time>>();
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL);
			pst.setInt(1, idCompeticion);
		
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int dorsal = rs.getInt(1);
				Time tiempo= rs.getTime(2);
				if(!tiempos.containsKey(dorsal))
					tiempos.put(dorsal,new ArrayList<Time>());
				
				tiempos.get(dorsal).add(tiempo);
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
