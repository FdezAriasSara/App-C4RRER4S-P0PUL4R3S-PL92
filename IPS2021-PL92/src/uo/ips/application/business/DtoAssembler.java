package uo.ips.application.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.competicion.CompeticionDto;

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
	
	public static List<AtletaDto> toAtletaDtoList(ResultSet rs) throws SQLException{
		List<AtletaDto> atletas = new ArrayList<AtletaDto>();
		
		while(rs.next()) {
			AtletaDto dto = new AtletaDto();
			dto.idAtleta = rs.getInt("idAtleta");
			dto.dni = rs.getString("DNI");
			dto.nombre = rs.getString("Name");
			dto.apellido = rs.getString("Surname");
			dto.email = rs.getString("Email");
			dto.fechaNacimiento = rs.getDate("fechaNacimiento");
			dto.sexo = rs.getString("sexo");
			atletas.add(dto);
		}
		return atletas;
		
	}
	
	public static CompeticionDto toCompeticionDto(ResultSet m) throws SQLException, BusinessException {
		
		CompeticionDto dto = new CompeticionDto();
		if(m.next()) {		
			dto.idCompeticion = m.getString("idCompeticion");
			dto.nombre =  m.getString("nombre");
			dto.fechaCompeticion = m.getDate("fechaCompeticion");
			dto.organizador = m.getString("organizador");
			dto.tipoCompeticion = m.getString("tipoCompeticion");
			dto.distanciaKm = m.getInt("distanciaKm");
			dto.plazasDisponibles = m.getInt("plazasDisponibles");
			dto.plazoInicioInscripcion = m.getDate("plazoInicioInscripcion");
			dto.plazoFinInscripcion = m.getDate("plazoFinInscripcion");
			dto.cuota = m.getDouble("cuota");
			dto.cuentaBancaria = m.getString("cuentaBancaria");
			
		}else {
			throw new BusinessException("La competición no está registrada");				
		}
		
		return dto;
	}
}