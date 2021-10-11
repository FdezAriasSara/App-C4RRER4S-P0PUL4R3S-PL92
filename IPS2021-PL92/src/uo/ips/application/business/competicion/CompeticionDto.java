package uo.ips.application.business.competicion;

import java.sql.Date;

public class CompeticionDto {
	public String idCompeticion;
	public String nombre;
	public Date fechaCompeticion;
	public String organizador;
	
	
	public CompeticionDto(String idCompeticion, String nombre, Date fechaCompeticion, String organizador) {
		
		this.idCompeticion = idCompeticion;
		this.nombre = nombre;
		this.fechaCompeticion = fechaCompeticion;
		this.organizador = organizador;
		
	}
	
	public CompeticionDto() {
		idCompeticion=null;
		nombre=null;
		fechaCompeticion=null;
		organizador = null;
	}
}
