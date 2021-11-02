package uo.ips.application.business.Inscripcion;

import java.sql.Date;

import uo.ips.application.business.Inscripcion.InscripcionDto.Estado;

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
	public Date fechaInscripcion;
	public Date fechaUltimoCambio;
	public int idCategoria;
	public Estado estado;

}
