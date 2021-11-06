package uo.ips.application.business.Inscripcion;

import java.sql.Date;
import java.sql.Time;

public class InscripcionDto {

	public static final int VACIO = -1;
	public int idCompeticion;
	public int idAtleta;	
	public Date fechaInscripcion;
	public Date fechaUltimoCambio;
	public int idCategoria;
	public int posicionFinal;
	public Time tiempoQueTarda;
	public int tiempoQueTardaEnSegundos;
	public Estado estado;
	public int dorsal;
	
	//Esto no está en la bd pero no los borreis porfas que los uso
	public Time tiempoInicio;
	public Time tiempoFinal;
	
	public String nombreCategoria; //No me lo borreis que lo uso (Martin)

	


	@SuppressWarnings("deprecation")
	public InscripcionDto(int idCompeticion, int idAtleta, String estado, Date fechaInscripcion,
			Date fechaUltimoCambio, int idCategoria, int posicionFinal, Time tiempoQueTarda) {
		
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
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
		

		public InscripcionDto(int idCompeticion, int idAtleta, String estado, Date fechaInscripcion,
				Date fechaUltimoCambio, int idCategoria, int posicionFinal, Time tiempoQueTarda, String nombreCategoria) {
			this(idCompeticion, idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria,posicionFinal,tiempoQueTarda);
			this.nombreCategoria = nombreCategoria;
			
		
	}
	
	
	public String toStringParaClasificacion() {
		String res =  "ID Atleta: " + idAtleta + 
				" - ID Competicion: " + idCompeticion;
		
		String pos = (posicionFinal < 0) ? " - Posicion: No terminada " : (" - Posicion: " + posicionFinal);
		String time = (tiempoQueTardaEnSegundos > 0) ? 	(" - Tiempo de carrera: " + tiempoQueTarda) : " - Tiempo de carrera: --"	;
		
		
		return res + pos + time;
		
				
	}


	public InscripcionDto() {
		this.fechaInscripcion = null;
		this.fechaUltimoCambio = null;
		this.posicionFinal = VACIO;
		this.tiempoQueTardaEnSegundos = VACIO;
	}

}
