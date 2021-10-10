package uo.ips.application.business.competicion;

import uo.ips.application.business.BusinessException;

public interface CompeticionCrudService {
	
	public CompeticionDto AñadirCompeticion(CompeticionDto competicion) throws BusinessException;

}
