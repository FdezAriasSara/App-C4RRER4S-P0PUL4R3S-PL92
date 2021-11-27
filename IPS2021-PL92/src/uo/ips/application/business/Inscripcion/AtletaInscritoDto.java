package uo.ips.application.business.Inscripcion;

import java.sql.Date;
import java.sql.Time;

public class AtletaInscritoDto {

	public int idAtleta;
	public String dni;
	public String email;
	public String nombre;
	public String apellido;
	public Date fechaNacimiento;
	public String sexo;

	public String categoria;

	public int idCompeticion;
	public String nombreCompeticion;
	public Date fechaInscripcion;
	public Date fechaUltimoCambio;
	public int idCategoria;
	public Estado estado;

	// para comparación de atletas
	public int posicionFinal;
	public Time tiempoQueTarda;
	public String club;
	public double ritmoPorKm;
	public int dorsal;

}
