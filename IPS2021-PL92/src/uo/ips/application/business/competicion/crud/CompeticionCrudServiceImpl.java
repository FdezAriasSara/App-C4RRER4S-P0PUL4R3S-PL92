package uo.ips.application.business.competicion.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	
	public CompeticionDto añadirCompeticion(CompeticionDto competicion) throws BusinessException {
		return new AnadirCompeticion(competicion).execute();
		
	}

	@Override
	public void inscribirAtleta(int idCompeticion, int idAtleta,int idCategoria) throws BusinessException {
		new InscribirAtleta(idCompeticion,idAtleta,idCategoria).execute();
		
	}

}
