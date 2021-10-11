package uo.ips.application.business.competicion;

import uo.ips.application.business.BusinessException;

public interface CompeticionCrudService {
	
	public CompeticionDto a�adirCompeticion(CompeticionDto competicion) throws BusinessException;
	
	public void inscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) throws BusinessException;
}
