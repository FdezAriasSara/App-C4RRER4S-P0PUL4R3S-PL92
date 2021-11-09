package uo.ips.application.business.competicion.crud;

import java.util.List;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionCategoriaDto;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;

public class CompeticionCrudServiceImpl implements CompeticionCrudService {

	
	public CompeticionDto anadirCompeticion(CompeticionDto competicion) throws BusinessException {
		return new AnadirCompeticion(competicion).execute();
		
	}


	@Override
	public List<CompeticionDto> ListarCompeticionesInscripcionesAbiertas() throws BusinessException {
		return new ListarCompeticionesInscripcionesAbiertas().execute();
	}


	@Override
	public void terminarCompeticion(int idCompeticion) throws BusinessException {
		new TerminarCarrera(idCompeticion).execute();
		
	}


	@Override

	public List<CompeticionCategoriaDto> listarCompeticionesConSusCategorias(int idCompeticion)
			throws BusinessException {
		return new ListarCompeticionesConSusCategorias(idCompeticion).execute();}

	public int dorsalesReservados(int competicionId) throws BusinessException {
	
		return new DorsalesReservadosPorCompeticion(competicionId).execute();
	}


}
