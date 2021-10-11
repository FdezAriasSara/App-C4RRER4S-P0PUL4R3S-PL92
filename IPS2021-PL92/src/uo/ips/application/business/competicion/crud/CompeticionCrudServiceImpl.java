package uo.ips.application.business.competicion.crud;

import java.util.List;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	
	public CompeticionDto AñadirCompeticion(CompeticionDto competicion) throws BusinessException {
		return new AñadirCompeticion(competicion).execute();
		
	}

	@Override
	public List<CompeticionDto> ListarCompeticionesInscripcionesAbiertas() throws BusinessException {
		return new ListarCompeticionesInscripcionesAbiertas().execute();
	}

}
