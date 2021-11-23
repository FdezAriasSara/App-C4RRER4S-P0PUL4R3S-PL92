package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.atleta.AtletaDto;

public class FindAll {
	
	private static String SQL_FindAll = "Select * From Atleta";
	
	public List<AtletaDto> execute(){
		
		// Process
		List<AtletaDto> list = new ArrayList<AtletaDto>();
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		AtletaDto atleta;
		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_FindAll);
		
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				atleta = new AtletaDto();
				atleta.apellido = rs.getString("Surname");
				atleta.dni = rs.getString("dni");
				atleta.idAtleta = rs.getInt("idAtleta");
				atleta.email = rs.getString("email");
				atleta.nombre = rs.getString("Name");
				atleta.fechaNacimiento = rs.getDate("fechaNacimiento");
				atleta.sexo = rs.getString("sexo");
				
				list.add(atleta);
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
