package uo.ips.application.business.Inscripcion.crud;

import java.util.List;

import alb.util.assertion.Argument;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class RegistrarTiempos {
	
	private String SQL_RegsitrarTiempos = "UPDATE Inscripcion set estado = 'TERMINADA' , tiempoQueTard = ? where dorsal = ? and idCompeticion ?";
	
	private List<InscripcionDto> inscripciones;
	
	public RegistrarTiempos(List<InscripcionDto> inscripciones){
		Argument.isNotNull(inscripciones);
		this.inscripciones = inscripciones;
	}

}
