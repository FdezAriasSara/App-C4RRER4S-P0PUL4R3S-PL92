package uo.ips.application.business.competicion;

import java.sql.Date;

public class CompeticionDto {
	public int idCompeticion;
	public String nombre;
	public Date fechaCompeticion;
	public String organizador;
	public String tipoCompeticion;
	public int distanciaKm;
	public int plazasDisponibles;
	public Date plazoInicioInscripcion;
	public Date plazoFinInscripcion;
	public double cuota;
	public String cuentaBancaria;
	public int dorsalesReservados;
	
	
	
	public CompeticionDto(int idCompeticion, String nombre, Date fechaCompeticion, 
			String organizador,Date plazoInicioInscripcion, Date plazoFinInscripcion ,
			String tipoCompeticion,int distanciaKm,int plazasDisponibles,double cuota
			 ) {
		
		this.idCompeticion = idCompeticion;
		this.nombre = nombre;
		this.fechaCompeticion = fechaCompeticion;
		this.organizador = organizador;
		this.tipoCompeticion = tipoCompeticion;
		this.distanciaKm = distanciaKm;
		this.plazasDisponibles = plazasDisponibles;
		this.plazoFinInscripcion = plazoFinInscripcion;
		this.plazoInicioInscripcion = plazoInicioInscripcion; 
		this.cuota = cuota;
	}


	public String toString() {
		
		return "ID: "+ idCompeticion + 
				" - Nombre: " + nombre + 
				" - Fecha competicion: " + fechaCompeticion + 
				" - Organizador: " + organizador + 
				" - Tipo de competicion: " + tipoCompeticion + 
				" - Distancia en KM: " + distanciaKm +
				" - Plazas disponibles: " + plazasDisponibles + 
				" - Inicio periodo de inscripcion: " + plazoInicioInscripcion + 
				" - Fin periodo de inscripcion: " + plazoFinInscripcion +
				" - Cuota: "+ cuota
				;
		
	}


	
	public CompeticionDto() {
		idCompeticion=0
				;
		nombre=null;
		fechaCompeticion=null;
		organizador = null;
	}
	
	

}
