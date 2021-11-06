package uo.ips.application.business.Inscripcion.crud;

import java.util.List;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class InscripcionCrudServiceImpl implements InscripcionCrudService {

	@Override
	public void inscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) throws BusinessException {
		new InscribirAtleta(idCompeticion, idAtleta, idCategoria).execute();

	}

	@Override
	public void inscribirAtleta(String email, int idCompeticion) throws BusinessException {
		new InscribirAtletaPorEmail(email, idCompeticion).execute();

	}

	@Override
	public InscripcionDto anadirInscripción(InscripcionDto solicitud) {
		return null;
	}

	@Override
	public List<InscripcionDto> obtenerClasificaciones(int idCompeticion, String sexo) throws BusinessException {
		return new ObtenerClasificaciones(idCompeticion, sexo).execute();
	}

	@Override
	public List<AtletaInscritoDto> obtenerAtletasParaCompeticion(int idCompeticion) throws BusinessException {
		return new ObtenerAtletas(idCompeticion).execute();
	}
	@Override
	public List<String> listarInscripcionesDelAtleta(int idAtleta) throws BusinessException {
		
		return new ListarInscripcionesAtleta(idAtleta).execute();
	}
	
	@Override
	public List<AtletaInscritoDto> listarInscripcionesAtletaConDto(int idAtleta) throws BusinessException{
		return new ListarInscripcionesAtletaConDto(idAtleta).execute();
	}

	@Override
	public int registrarTiempos(List<InscripcionDto> inscripciones) throws BusinessException {
		return new RegistrarTiempos(inscripciones).execute();
		
	}

	
}
