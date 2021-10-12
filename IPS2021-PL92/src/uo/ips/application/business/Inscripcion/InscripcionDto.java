package uo.ips.application.business.Inscripcion;

import java.sql.Date;

public class InscripcionDto {

	public static final int VACIO = -1;
	public int idCompeticion;
	public int idAtleta;	
	public double cuota;
	public Date fechaInscripcion;
	public Date fechaUltimoCambio;
	public int idCategoria;
	public int posicionFinal;
	public float tiempoQueTarda;
	public Estado estado;

	public enum Estado {
		PRE_INSCRITO, INSCRITO, PENDIENTE

	};

	

	

	public InscripcionDto(int idCompeticion, int idAtleta, double cuota, Date fechaInscripcion,
			Date fechaUltimoCambio, int idCategoria, int posicionFinal, float tiempoQueTarda, Estado estado) {
		
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
		this.cuota = cuota;
		this.fechaInscripcion = fechaInscripcion;
		this.fechaUltimoCambio = fechaUltimoCambio;
		this.idCategoria = idCategoria;
		this.posicionFinal = posicionFinal;
		this.tiempoQueTarda = tiempoQueTarda;
		this.estado = estado;
	}





	public InscripcionDto() {
		this.fechaInscripcion = null;
		this.fechaUltimoCambio = null;
		this.posicionFinal = VACIO;
		this.tiempoQueTarda = VACIO;
	}

}
