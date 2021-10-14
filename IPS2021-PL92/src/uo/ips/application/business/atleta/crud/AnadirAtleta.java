package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaDto;

public class AnadirAtleta {
	
private String SQL = "INSERT INTO Atleta (dni,email,nombre,apellido,fechaNacimiento) VALUES (?,?,?,?,?)";
private String SQLGetAtleta = "SELECT * FROM Atleta WHERE dni = ?";
private AtletaDto atleta;

public AnadirAtleta(AtletaDto atleta) {
	this.atleta = atleta;
}

public AtletaDto execute() throws BusinessException {

	// Process
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
		c = Jdbc.getConnection();
		
		pst = c.prepareStatement(SQLGetAtleta);
		pst.setString(1, atleta.dni);
		
		rs = pst.executeQuery();
		
		while(rs.next()) {
			throw new BusinessException("Ya existe el atleta");
		}
		pst.close();
		pst = c.prepareStatement(SQL);
		pst.setString(1, atleta.dni);
		pst.setString(2, atleta.email);
		pst.setString(3, atleta.nombre);
		pst.setString(4, atleta.apellido);
		pst.setDate(5, atleta.fechaNacimiento);
		
		pst.executeUpdate();			

		
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	finally {
		Jdbc.close(rs, pst, c);
	}
	return atleta;
}

}
