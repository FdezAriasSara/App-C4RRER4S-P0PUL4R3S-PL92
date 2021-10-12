package uo.ips.application.business.competicion;



import uo.ips.application.business.BusinessException;
import java.util.List;


public interface CompeticionCrudService {
	

	public CompeticionDto anadirCompeticion(CompeticionDto competicion) throws BusinessException;
	
	public void inscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) throws BusinessException;

	public List<CompeticionDto> ListarCompeticionesInscripcionesAbiertas() throws BusinessException;
}
