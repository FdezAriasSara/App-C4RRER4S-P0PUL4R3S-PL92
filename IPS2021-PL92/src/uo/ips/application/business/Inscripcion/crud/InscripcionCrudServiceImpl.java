package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.util.List;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class InscripcionCrudServiceImpl implements InscripcionCrudService {

	@Override
	public void inscribirAtleta(int idCompeticion, int idAtleta,
			int idCategoria) throws BusinessException {
		new InscribirAtleta(idCompeticion, idAtleta, idCategoria).execute();

	}

	@Override
	public void inscribirAtleta(String email, int idCompeticion)
			throws BusinessException {
		new InscribirAtletaPorEmail(email, idCompeticion).execute();

	}

	@Override

	public InscripcionDto anadirInscripcion(InscripcionDto solicitud) {

		return null;
	}

	@Override
	public List<InscripcionDto> obtenerClasificaciones(int idCompeticion,
			int idCategoria) throws BusinessException {
		return new ObtenerClasificaciones(idCompeticion, idCategoria).execute();
	}

	@Override
	public List<AtletaInscritoDto> obtenerAtletasParaCompeticion(
			int idCompeticion) throws BusinessException {
		return new ObtenerAtletas(idCompeticion).execute();
	}

	@Override
	public List<String> listarInscripcionesDelAtleta(int idAtleta)
			throws BusinessException {

		return new ListarInscripcionesAtleta(idAtleta).execute();
	}

	@Override
	public List<AtletaInscritoDto> listarInscripcionesAtletaConDto(int idAtleta)
			throws BusinessException {
		return new ListarInscripcionesAtletaConDto(idAtleta).execute();
	}

	@Override
	public int registrarTiempos(List<InscripcionDto> inscripciones)
			throws BusinessException {
		return new RegistrarTiempos(inscripciones).execute();

	}

	@Override
	public void asignarDorsalesNoReservados(int idCompeticion)
			throws BusinessException {
		new AsignarDorsalesNoReservados(idCompeticion).execute();

	}

	@Override
	public void asignarDorsalReservado(String email, int dorsal,
			int idCompeticion) throws BusinessException {
		new AsignarDorsalReservado(email, dorsal, idCompeticion).execute();
	}

	@Override
	public int inscribirClusterEmails(int idCompeticion, Connection c,
			String club, String... email) throws BusinessException {
		return new IncribirClusterEmails(idCompeticion, c, club, email)
				.execute();
	}

	@Override
	public AtletaInscritoDto obtenerAtletaParaComparar(int dorsal,
			int idCompeticion) throws BusinessException {

		return new ObtenerAtletasParaComparar(dorsal, idCompeticion).execute();
	}

	@Override
	public int inscribirAtletaListaEspera(String emailAtleta, int idCompeticion)
			throws BusinessException {
		return new InscribirAtletaListaEspera(emailAtleta, idCompeticion)
				.execute();
	}

	@Override
	public boolean tieneListaEspera(int idCompeticion)
			throws BusinessException {
		return new TieneListaEspera(idCompeticion).execute();
	}

	@Override
	public void cancelarInscricpion(int idAtleta, int idCompeticion)
			throws BusinessException {
		new CancelarInscripcion(idAtleta, idCompeticion).execute();

	}

	@Override
	public InscripcionDto encontarInscripcion(int idAtleta,
			int idCompeticionSeleccionada) throws BusinessException {
		return new EncontrarInscripcion(idAtleta, idCompeticionSeleccionada)
				.execute();

	}

}
