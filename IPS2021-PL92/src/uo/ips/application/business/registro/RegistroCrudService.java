package uo.ips.application.business.registro;

public interface RegistroCrudService {
	/**
	 *
	 * MÑtodo que comprueba que el atleta que intenta inscribirse estÑ registrado.
	 * Si no , se emplearÑ su resultado para darle la posibilidad de hacerlo.
	 * 
	 * @return boolean, true si el mail ya estÑ registrado. Falso si no es asÑ.
	 */
	boolean ComprobarDatosInscripcion(String email);
}
