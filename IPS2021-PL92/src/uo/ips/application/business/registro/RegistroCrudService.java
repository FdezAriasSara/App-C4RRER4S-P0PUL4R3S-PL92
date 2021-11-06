package uo.ips.application.business.registro;

public interface RegistroCrudService {
	/**
	 *
	 * M�todo que comprueba que el atleta que intenta inscribirse est� registrado.
	 * Si no , se emplear� su resultado para darle la posibilidad de hacerlo.
	 * 
	 * @return boolean, true si el mail ya est� registrado. Falso si no es as�.
	 */
	boolean ComprobarDatosInscripcion(String email);
}
