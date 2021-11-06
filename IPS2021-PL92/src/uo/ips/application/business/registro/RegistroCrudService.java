package uo.ips.application.business.registro;

public interface RegistroCrudService {
	/**
	 *
	 * Método que comprueba que el atleta que intenta inscribirse esté registrado.
	 * Si no , se empleará su resultado para darle la posibilidad de hacerlo.
	 * 
	 * @return boolean, true si el mail ya está registrado. Falso si no es así.
	 */
	boolean ComprobarDatosInscripcion(String email);
}
