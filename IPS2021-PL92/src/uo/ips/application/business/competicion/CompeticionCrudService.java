package uo.ips.application.business.competicion;



import uo.ips.application.business.BusinessException;
import java.util.List;


public interface CompeticionCrudService {
	
	public CompeticionDto A�adirCompeticion(CompeticionDto competicion) throws BusinessException;
	

	public List<CompeticionDto> ListarCompeticionesInscripcionesAbiertas() throws BusinessException;

}
