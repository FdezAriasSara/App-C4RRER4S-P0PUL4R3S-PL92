package uo.ips.application.business;

import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.crud.InscripcionCrudServiceImpl;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.crud.AtletaCrudServiceImpl;
import uo.ips.application.business.atleta.crud.CalcularCategoria;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.crud.CompeticionCrudServiceImpl;

public class BusinessFactory {
	
	public static AtletaCrudService forAtletaCrudService() {
		return new AtletaCrudServiceImpl();
	}
	
	public static CompeticionCrudService forCompeticionCrudService() {
		return new CompeticionCrudServiceImpl();
	}
	
	public static CalcularCategoria forCalcularCategoria(int idAtleta, int idCompeticion) {
		return new CalcularCategoria(idAtleta, idCompeticion);
	}
	
	public static InscripcionCrudService forInscripcionCrudService() {
		return new InscripcionCrudServiceImpl();
	}
}
