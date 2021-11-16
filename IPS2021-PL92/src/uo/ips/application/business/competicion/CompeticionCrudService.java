package uo.ips.application.business.competicion;



import uo.ips.application.business.BusinessException;
import java.util.List;


public interface CompeticionCrudService {
	

	public CompeticionDto anadirCompeticion(CompeticionDto competicion) throws BusinessException;
	
	
	public List<CompeticionDto> ListarCompeticionesInscripcionesAbiertas() throws BusinessException;
	
	public void terminarCompeticion(int idCompeticion) throws BusinessException;
	
	public List<CompeticionCategoriaDto> listarCompeticionesConSusCategorias(int idCompeticion) throws BusinessException;

	public int dorsalesReservados(int idCompeticion) throws BusinessException;
	
	
	public List<CompeticionDto> listarTodasCompeticiones() throws BusinessException;
}
