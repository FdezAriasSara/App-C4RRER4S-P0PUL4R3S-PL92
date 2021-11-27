package uo.ips.application.business.Inscripcion.crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.Inscripcion.PlazoDto;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.TransferenciaDto;
import uo.ips.application.business.pago.crud.CargaPagos;

public class ActualizarEstadoInscripcion {
	private String SQL_BUSCAR_COMPETICION = "SELECT * from Competicion where idCompeticion = ?";
	private String SQL_BUSCAR_INSCRIPCION = "SELECT * from Inscripcion where idCompeticion = ? and idAtleta = ?";
	private String SQL_BUSCAR_ID_ATLETA = "SELECT idAtleta from Atleta where dni = ?";
	private String SQL_BUSCAR_PLAZO = "select * from Plazos where idCompeticion = ? and ? >= fechaInicio and ? <= fechaFin";
	private String SQL_UPDATE_INSCRIPCION = "update Inscripcion set estado = ?, fecha_pago = ?, importe_pago = ?, importe_devolver = ? where idAtleta = ? and idCompeticion = ?";

	private int idCompeticion;
	private List<TransferenciaDto> datosPagos;
	private final double PRECISION = 0.0001;

	private Connection c = null;

	public ActualizarEstadoInscripcion(int idCompeticion) {
		this.idCompeticion = idCompeticion;
		try {
			c = Jdbc.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void execute() throws BusinessException {

		Optional<CompeticionDto> competicion = buscarCompeticion();
		if (!competicion.isPresent()) {
			throw new BusinessException("No existe la competiciÑn");
		}

		cargaPagos();

		int idAtleta;

		for (TransferenciaDto transferenciaDto : datosPagos) {
			idAtleta = getIdAtleta(transferenciaDto.dni);
			if (idAtleta == -1)
				throw new BusinessException("Atleta no existente en fichero de pagos");
			Optional<InscripcionDto> oi = findInscripcionByIds(idAtleta);
			if (!oi.isPresent())
				throw new BusinessException("Hay pago de atleta no inscrito para la competiciÑn seleccionada");
			InscripcionDto inscripcion = oi.get();
			Optional<PlazoDto> op = findPlazo(inscripcion.fechaInscripcion);
			if (!op.isPresent())
				throw new BusinessException("Hay pago de atleta no inscrito para la competiciÑn seleccionada");
			PlazoDto plazo = op.get();

			compruebaPago(plazo.cuota, inscripcion.fechaInscripcion, transferenciaDto, idAtleta);

			Jdbc.close(c);
		}
	}

	private void compruebaPago(double cuota, Date fechaInscripcion, TransferenciaDto transferenciaDto, int idAtleta)
			throws BusinessException {
		LocalDate localFechaInscripcion = fechaInscripcion.toLocalDate();

		if (isInPlazo(localFechaInscripcion, transferenciaDto.fecha)) {
			if (Math.abs(transferenciaDto.importe - cuota) < PRECISION) {
				updateInscripcion("INSCRITO", transferenciaDto.fecha, transferenciaDto.importe, 0, idAtleta);
			} else if ((transferenciaDto.importe - cuota) > 0.001) {
				updateInscripcion("INSCRITO", transferenciaDto.fecha, cuota, (transferenciaDto.importe - cuota),
						idAtleta);
			} else if ((transferenciaDto.importe - cuota) < 0.001) {
				updateInscripcion("ANULADA", transferenciaDto.fecha, 0, transferenciaDto.importe, idAtleta);
			}
		} else {
			updateInscripcion("ANULADA", transferenciaDto.fecha, 0, transferenciaDto.importe, idAtleta);
		}
	}

	private boolean isInPlazo(LocalDate localFechaInscripcion, LocalDate fechaTransferencia) {
		LocalDate fechaLimite = localFechaInscripcion.plusDays(2);
		return !fechaTransferencia.isAfter(fechaLimite);
	}

	private void updateInscripcion(String estado, LocalDate fecha, double importe_pago, double importe_devolver,
			int idAtleta) {

		PreparedStatement pst = null;

		try {

			pst = c.prepareStatement(SQL_UPDATE_INSCRIPCION);
			pst.setString(1, estado);
			pst.setDate(2, Date.valueOf(fecha));
			pst.setDouble(3, importe_pago);
			pst.setDouble(4, importe_devolver);
			pst.setInt(5, idAtleta);
			pst.setInt(6, idCompeticion);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	private void cargaPagos() throws BusinessException {
		CargaPagos cargaPagos = new CargaPagos();
		try {
			datosPagos = cargaPagos.read();
		} catch (IOException e) {
			throw new BusinessException("No existe el fichero de pagos");
		}
	}

	private Optional<CompeticionDto> buscarCompeticion() {

		PreparedStatement pst = null;
		Optional<CompeticionDto> competicion = null;

		try {

			pst = c.prepareStatement(SQL_BUSCAR_COMPETICION);
			pst.setInt(1, idCompeticion);

			competicion = DtoAssembler.toCompeticion(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return competicion;
	}

	private Optional<InscripcionDto> findInscripcionByIds(int idAtleta) {
		PreparedStatement pst = null;
		Optional<InscripcionDto> inscripcion = null;

		try {

			pst = c.prepareStatement(SQL_BUSCAR_INSCRIPCION);
			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);

			inscripcion = DtoAssembler.toInscripcion(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return inscripcion;
	}

	private int getIdAtleta(String dni) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = -1;

		try {

			pst = c.prepareStatement(SQL_BUSCAR_ID_ATLETA);
			pst.setString(1, dni);

			rs = pst.executeQuery();

			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return id;
	}

	private Optional<PlazoDto> findPlazo(Date fechaInscripcion) {
		PreparedStatement pst = null;
		Optional<PlazoDto> plazo = null;

		try {

			pst = c.prepareStatement(SQL_BUSCAR_PLAZO);
			pst.setInt(1, idCompeticion);
			pst.setDate(2, fechaInscripcion);
			pst.setDate(3, fechaInscripcion);

			plazo = DtoAssembler.toPlazo(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return plazo;
	}
}
