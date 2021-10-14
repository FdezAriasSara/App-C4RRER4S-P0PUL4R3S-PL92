package uo.ips.application.business;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ips.application.business.atleta.AtletaDto;

public class DtoAssembler {
	public static AtletaDto toAtletaDto(ResultSet m) throws SQLException, BusinessException {
		AtletaDto dto = new AtletaDto();
		if(m.next()) {
			dto.idAtleta = m.getInt("idAtleta");
			dto.dni = m.getString("DNI");
			dto.nombre = m.getString("Name");
			dto.apellido = m.getString("Surname");
			dto.email = m.getString("Email");
			dto.fechaNacimiento = m.getDate("fechaNacimiento");
			dto.sexo = m.getString("sexo");			
		}else {
			throw new BusinessException("El atleta no está registrado");				

		}
		
		return dto;
	    }
}