package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaDto;

public class BuscarAtletaPorEmail {

	private String email;





	public BuscarAtletaPorEmail(String email) {
		Argument.isNotEmpty(email, "El email no puede estar vacio");
		this.email=email;
	}
	private String ENCONTRAR_EMAIL = "select * from Atleta where email= ?";

	
	
	
	

public AtletaDto execute() throws BusinessException {

	// Process
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	AtletaDto atleta = new AtletaDto();
	try {
		c = Jdbc.getConnection();
		
		pst = c.prepareStatement(ENCONTRAR_EMAIL);
		pst.setString(1, email);
		
		rs = pst.executeQuery();
		
		if(rs.next()) {
			atleta.apellido = rs.getString("Surname");
			atleta.dni = rs.getString("dni");
			atleta.idAtleta = rs.getInt("idAtleta");
			atleta.email = rs.getString("email");
			atleta.nombre = rs.getString("Name");
			atleta.fechaNacimiento = rs.getDate("fechaNacimiento");
			atleta.sexo = rs.getString("sexo");
			
			
		}else {
			return null;
		}

		
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	finally {
		Jdbc.close(rs, pst, c);
	}
	
	return atleta;
}

}


