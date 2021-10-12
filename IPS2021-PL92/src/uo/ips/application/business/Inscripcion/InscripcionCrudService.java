package uo.ips.application.business.Inscripcion;

public interface InscripcionCrudService {
	/**
	 * Permite realizar la inscripción en la competición
	 * @param solicitud
	 * @return
	 */
	InscripcionDto añadirInscripción(InscripcionDto solicitud);
	

}
