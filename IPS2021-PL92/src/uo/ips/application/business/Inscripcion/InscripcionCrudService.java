package uo.ips.application.business.Inscripcion;

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

	
}
