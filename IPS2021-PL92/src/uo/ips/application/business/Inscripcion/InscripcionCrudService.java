package uo.ips.application.business.Inscripcion;

import java.util.List;

import uo.ips.application.business.BusinessException;

public interface InscripcionCrudService {
	/**
	 * Permite realizar la inscripci�n en la competici�n
	 * @param solicitud
	 * @return
	 */
	InscripcionDto anadirInscripci�n(InscripcionDto solicitud);
	
	public void inscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) throws BusinessException;

	public void inscribirAtleta(String email, int idCompeticion) throws BusinessException;
	
	List<InscripcionDto> obtenerClasificaciones(int idCompeticion, String sexo) throws BusinessException;

	
}
