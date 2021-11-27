package uo.ips.application.business.Inscripcion;

import java.sql.Connection;
import java.util.List;

import uo.ips.application.business.BusinessException;

public interface InscripcionCrudService {
	/**
	 * Permite realizar la inscripci�n en la competici�n
	 * 
	 * @param solicitud
	 * @return
	 */

	InscripcionDto anadirInscripcion(InscripcionDto solicitud);


	public void inscribirAtleta(int idCompeticion, int idAtleta,
			int idCategoria) throws BusinessException;

	public void inscribirAtleta(String email, int idCompeticion)
			throws BusinessException;

	List<InscripcionDto> obtenerClasificaciones(int idCompeticion,
			int categoria) throws BusinessException;

	List<AtletaInscritoDto> obtenerAtletasParaCompeticion(int idCompeticion)
			throws BusinessException;

	List<String> listarInscripcionesDelAtleta(int idAtleta)
			throws BusinessException;

	List<AtletaInscritoDto> listarInscripcionesAtletaConDto(int idAtleta)
			throws BusinessException;

	/**
	 * Devuelve el numero de inscripciones NO actualizadas, es decir, que tenian
	 * algun error
	 * 
	 * @param inscripciones
	 * @return
	 * @throws BusinessException
	 */
	int registrarTiempos(List<InscripcionDto> inscripciones)
			throws BusinessException;

	/**
	 * Para cada inscripcion perteneciente a una competici�n cuyos plazos han
	 * terminado, se asignar� un dorsal a aquellos competidores que no lo tengan
	 * asignado a�n.
	 * 
	 * @throws BusinessException
	 */
	void asignarDorsalesNoReservados(int idCompeticion)
			throws BusinessException;

	/**
	 * Asigna a un atleta uno de los dorsales reservados.
	 * 
	 * @param email  del competidor
	 * @param dorsal a asignar de los reservados
	 * @throws BusinessException si no encuentra al competidor
	 */
	void asignarDorsalReservado(String email, int dorsal, int idCompeticion)
			throws BusinessException;

	int inscribirClusterEmails(int idCompeticion, Connection c, String club,
			String... email) throws BusinessException;

	/**
	 * Busca todos los datos deun atleta usando su dorsal y la competici�n en la
	 * que ha participado
	 * 
	 * @param idAtleta
	 * @param idCompeticion
	 * @return
	 * @throws BusinessException
	 */
	AtletaInscritoDto obtenerAtletaParaComparar(int dorsal, int idCompeticion)
			throws BusinessException;
}
