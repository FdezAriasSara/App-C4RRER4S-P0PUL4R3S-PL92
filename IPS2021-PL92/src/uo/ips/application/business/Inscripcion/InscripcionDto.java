package uo.ips.application.business.Inscripcion;

import java.sql.Date;
import java.sql.Time;

public class InscripcionDto {

	public static final int VACIO = -1;
	public int idCompeticion;
	public int idAtleta;	
	public double cuota;
	public Date fechaInscripcion;
	public Date fechaUltimoCambio;
	public int idCategoria;
	public int posicionFinal;
	public Time tiempoQueTarda;
	public int tiempoQueTardaEnSegundos;
	public Estado estado;

	public enum Estado {
		PRE_INSCRITO, INSCRITO, TERMINADA

	};

	


	@SuppressWarnings("deprecation")
	public InscripcionDto(int idCompeticion, int idAtleta,double cuota, String estado, Date fechaInscripcion,
			Date fechaUltimoCambio, int idCategoria, int posicionFinal, Time tiempoQueTarda) {
		
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
		this.cuota = cuota;
		this.fechaInscripcion = fechaInscripcion;
		this.fechaUltimoCambio = fechaUltimoCambio;
		this.idCategoria = idCategoria;
		this.posicionFinal = posicionFinal;
		this.tiempoQueTarda = tiempoQueTarda;
		this.tiempoQueTardaEnSegundos = tiempoQueTarda.getSeconds() +  tiempoQueTarda.getMinutes()*60 + tiempoQueTarda.getHours()*60*60;
		
		
		
		switch(estado) {
		case("PRE_INSCRITO"): this.estado = Estado.PRE_INSCRITO; break;
		case("INSCRITO"):  this.estado = Estado.INSCRITO; break;
		case("TERMINADA"):  this.estado = Estado.TERMINADA; break;
		}
		
	}
	
	
	public String toStringParaClasificacion() {
		return "ID Atleta: " + idAtleta + 
				" - ID Competicion: " + idCompeticion + 
				" - Posicion: " + posicionFinal + 
				" - Tiempo de carrera: " + tiempoQueTarda;
	}





	public InscripcionDto() {
		this.fechaInscripcion = null;
		this.fechaUltimoCambio = null;
		this.posicionFinal = VACIO;
		this.tiempoQueTardaEnSegundos = VACIO;
	}

}
