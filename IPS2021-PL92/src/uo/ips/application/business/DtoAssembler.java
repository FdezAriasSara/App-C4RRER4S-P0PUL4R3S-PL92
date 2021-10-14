package uo.ips.application.business;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ips.application.business.atleta.AtletaDto;

public class DtoAssembler {
	public static AtletaDto toAtletaDto(ResultSet m) throws SQLException {
		AtletaDto dto = new AtletaDto();
		dto.dni = m.getString("dni");
		dto.nombre = m.getString("Name");
		dto.apellido = m.getString("Surname");
		dto.email = m.getString("Email");
		dto.fechaNacimiento = m.getDate("fechaNacimiento");
		dto.sexo = m.getString("sexo");
		
		return dto;
	    }
}