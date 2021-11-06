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
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TransferenciaDto;
import uo.ips.application.business.pago.crud.CargaPagos;

public class ActualizarEstadoInscripcion {
	private String SQL_BUSCAR_COMPETICION = "SELECT * from Competicion where idCompeticion = ?";
	private String SQL_BUSCAR_INSCRIPCION = "SELECT * from Inscripcion where idCompeticion = ? and adAtleta = ?";
	private String SQL_BUSCAR_ID_ATLETA = "SELECT idAtleta from Atleta where dni = ?";
	private String SQL_BUSCAR_PLAZO = "select * from Plazos where idCompeticion = ? and ? >= fechaInicio and ? =< fechaFin";
	private String SQL_ADD_PAGO = "insert into Pago(idAtleta, idPago, fechaPago, importe, idCompeticion, importe_devolver) values(?,?,?,?,?,?)";
	private String SQL_UPDATE_INSCRIPCION = "update Inscripcion set estado = ? where idAtleta = ? and idCompeticion = ?";

	private int idCompeticion;
	private List<TransferenciaDto> datosPagos;
	private final double PRECISION = 0.0001;

	public ActualizarEstadoInscripcion(int idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public void execute() throws BusinessException {

		Optional<CompeticionDto> competicion = buscarCompeticion();
		if (!competicion.isPresent()) {
			throw new BusinessException("No existe la competición");
		}

		cargaPagos();

		int idAtleta;

		for (TransferenciaDto transferenciaDto : datosPagos) {
			idAtleta = getIdAtleta(transferenciaDto.dni);
			if (idAtleta == -1)
				throw new BusinessException("Atleta no existente en ficehro de pagos");
			InscripcionDto inscripcion = findInscripcionByIds(idAtleta).get();
			PlazoDto plazo = findPlazo(inscripcion.fechaInscripcion);
			compruebaPago(plazo.cuota, inscripcion.fechaInscripcion, transferenciaDto, idAtleta);
		}
	}

	private void compruebaPago(double cuota, Date fechaInscripcion, TransferenciaDto transferenciaDto, int idAtleta) {
		LocalDate localFechaInscripcion = fechaInscripcion.toLocalDate();

		if (isInPlazo(localFechaInscripcion, transferenciaDto.fecha)) {
			if (Math.abs(transferenciaDto.importe - cuota) < PRECISION) {
				updateInscripcion("INSCRITO", idAtleta);
				addPago(new PagoDto(transferenciaDto.fecha, idAtleta, idCompeticion, transferenciaDto.importe, 0));
			} else if ((transferenciaDto.importe - cuota) > 0.001) {
				updateInscripcion("INSCRITO", idAtleta);
				addPago(new PagoDto(transferenciaDto.fecha, idAtleta, idCompeticion, cuota,
						(transferenciaDto.importe - cuota)));
			} else if ((transferenciaDto.importe - cuota) < 0.001) {
				updateInscripcion("ANULADA", idAtleta);
				addPago(new PagoDto(transferenciaDto.fecha, idAtleta, idCompeticion, 0, transferenciaDto.importe));
			}
		} else {
			updateInscripcion("ANULADA", idAtleta);
			addPago(new PagoDto(transferenciaDto.fecha, idAtleta, idCompeticion, 0, transferenciaDto.importe));
		}
	}

	private boolean isInPlazo(LocalDate localFechaInscripcion, LocalDate fechaTransferencia) {
		localFechaInscripcion.plusDays(2);
		return !fechaTransferencia.isAfter(localFechaInscripcion);
	}

	private void addPago(PagoDto pago) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_ADD_PAGO);
			pst.setInt(1, pago.idAtleta);
			pst.setString(2, pago.pagoId);
			pst.setDate(3, Date.valueOf(pago.fechaPago));
			pst.setDouble(4, pago.importe);
			pst.setInt(5, idCompeticion);
			pst.setDouble(6, pago.importe_devolver);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}

	private void updateInscripcion(String estado, int idAtleta) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_UPDATE_INSCRIPCION);
			pst.setString(1, estado);
			pst.setInt(2, idAtleta);
			pst.setInt(3, idCompeticion);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
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

		Connection c = null;
		PreparedStatement pst = null;
		Optional<CompeticionDto> competicion = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_BUSCAR_COMPETICION);
			pst.setInt(1, idCompeticion);

			competicion = DtoAssembler.toCompeticion(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}

		return competicion;
	}

	private Optional<InscripcionDto> findInscripcionByIds(int idAtleta) {
		Connection c = null;
		PreparedStatement pst = null;
		Optional<InscripcionDto> inscripcion = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_BUSCAR_INSCRIPCION);
			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);

			inscripcion = DtoAssembler.toInscripcion(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}

		return inscripcion;
	}

	private int getIdAtleta(String dni) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = -1;

		try {
			c = Jdbc.getConnection();

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
			Jdbc.close(c);
		}

		return id;
	}

	private PlazoDto findPlazo(Date fechaInscripcion) {
		Connection c = null;
		PreparedStatement pst = null;
		Optional<PlazoDto> plazo = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_BUSCAR_PLAZO);
			pst.setInt(1, idCompeticion);
			pst.setDate(2, fechaInscripcion);
			pst.setDate(3, fechaInscripcion);

			plazo = DtoAssembler.toPlazo(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}

		return plazo.get();
	}
}
