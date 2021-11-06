package uo.ips.application.business.Inscripcion;

import java.util.List;

import uo.ips.application.business.BusinessException;

public interface InscripcionCrudService {
	/**
	 * Permite realizar la inscripción en la competición
	 * @param solicitud
	 * @return
	 */
	InscripcionDto anadirInscripción(InscripcionDto solicitud);
	
	public void inscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) throws BusinessException;

	public void inscribirAtleta(String email, int idCompeticion) throws BusinessException;
	
	List<InscripcionDto> obtenerClasificaciones(int idCompeticion, int categoria) throws BusinessException;

	List<AtletaInscritoDto> obtenerAtletasParaCompeticion(int idCompeticion)throws BusinessException;


	List<String> listarInscripcionesDelAtleta(int idAtleta) throws BusinessException;
	
	List<AtletaInscritoDto> listarInscripcionesAtletaConDto(int idAtleta) throws BusinessException;
	
	/**
	 * Devuelve el numero de inscripciones NO actualizadas, es decir, que tenian algun error
	 * @param inscripciones
	 * @return
	 * @throws BusinessException
	 */
	int registrarTiempos(List<InscripcionDto> inscripciones) throws BusinessException;
	
}
