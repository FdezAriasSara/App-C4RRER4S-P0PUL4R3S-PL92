package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class PagarConTarjeta {

	private TarjetaDto tarjeta;

	private static String ESTADO_ACTUAL_INSCRIPCION = "SELECT estado FROM Inscripcion WHERE idAtleta=? and idCompeticion=?";
	private static String EXISTE_INSCRIPCION = "SELECT * FROM Inscripcion WHERE idAtleta=? and idCompeticion=?";
	private static String REALIZAR_PAGO = "UPDATE Inscripcion SET importe_pago=? ,fecha_Pago=? ,fechaUltimoCambio=? , estado='INSCRITO' WHERE idAtleta=? and idCompeticion=? ";
	private static String IMPORTE_PAGO = "select * from Plazos where idCompeticion=? and fechaInicio<=? and fechaFin>=?";
	private Connection c;
	private int idCompeticion;
	private int idAtleta;

	private double importe;

	public PagarConTarjeta(int idAtleta, int idCompeticion,
			TarjetaDto tarjeta) {
		Argument.isTrue(idAtleta > 0, "El id del atleta ha de ser positivo");
		Argument.isTrue(idCompeticion > 0,
				"El id de la competicion ha de ser positivo");
		Argument.isNotNull(tarjeta, "La tarjeta no puede ser null");

		this.idAtleta = idAtleta;
		this.idCompeticion = idCompeticion;
		this.tarjeta = tarjeta;

	}

	private void comprobarTarjeta(TarjetaDto tarjeta) throws BusinessException {
		if (tarjeta.fechaCaducidad.isBefore(LocalDate.now())) {
			throw new BusinessException("La tarjeta estÑ caducada.");
		}

	}

	public PagoDto execute() throws BusinessException {

		c = null;

		PreparedStatement pagoPst = null;

		PreparedStatement cambiarEstadoPst = null;
		PreparedStatement pstCuota = null;
		ResultSet rsCuota = null;

		try {
			c = Jdbc.getConnection();
			// Para realizar el pago primero debo comprobar que existe la
			// inscripciÑn, al
			// igual que para cambiar el estado de la misma.
			comprobarTarjeta(tarjeta);
			if (!existeInscripcion()) {

				throw new BusinessException("La inscripciÑn del atleta con id :"
						+ String.valueOf(this.idAtleta)
						+ " para la competiciÑn con id: "
						+ String.valueOf(this.idCompeticion) + " no existe.");

			}
			Date fechaPago = new Date(System.currentTimeMillis());
			// Si la inscripciÑn ya ha sido marcada como pagada , tenemos que
			// evitar el pago
			// la inscripciÑn que no tiene pago es aquella que esta en estado
			// pre-inscrito
			if (!estadoPreInscrito()) {

				throw new BusinessException("La inscripciÑn del atleta con id :"
						+ String.valueOf(this.idAtleta)
						+ " para la competiciÑn con id: "
						+ String.valueOf(this.idCompeticion)
						+ " ya estÑ pagada.");

			}
			pstCuota = c.prepareStatement(IMPORTE_PAGO);

			pstCuota.setInt(1, this.idCompeticion);
			pstCuota.setDate(2, fechaPago);
			pstCuota.setDate(3, fechaPago);
			rsCuota = pstCuota.executeQuery();
			if (rsCuota.next()) {
				this.importe = rsCuota.getDouble(1);
			} else {
				throw new BusinessException("No fue posible obtener la cuota");
			}

			// RealizaciÑn del pago una vez sabemos que existe la inscripciÑn
			pagoPst = c.prepareStatement(REALIZAR_PAGO);

			pagoPst.setDouble(1, this.importe);// el importe sale de la cuota

			pagoPst.setDate(2, fechaPago);
			pagoPst.setDate(3, fechaPago);
			pagoPst.setInt(4, idAtleta);
			pagoPst.setInt(5, idCompeticion);
			pagoPst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsCuota, pagoPst);

			Jdbc.close(cambiarEstadoPst);
			Jdbc.close(c);

		}
		return new PagoDto(LocalDate.now(), idAtleta, idCompeticion, importe,
				0);
	}

	/**
	 * Comprueba el estado de la inscripciÑn.
	 * 
	 * @return true si el estado es "pre-inscrito". Falso si no.
	 */
	private boolean estadoPreInscrito() {
		PreparedStatement encontrarIdPst = null;
		ResultSet rsInscripcion = null;
		try {
			encontrarIdPst = c.prepareStatement(ESTADO_ACTUAL_INSCRIPCION);
			encontrarIdPst.setInt(1, this.idAtleta);
			encontrarIdPst.setInt(2, this.idCompeticion);
			rsInscripcion = encontrarIdPst.executeQuery();
			if (rsInscripcion.next()) {
				if (rsInscripcion.getString(1).equals("PRE_INSCRITO"))
					return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsInscripcion, encontrarIdPst);
		}
		return false;
	}

	/**
	 * Comprueba la existencia de la inscripciÑn.
	 * 
	 * @return true si existe una inscripciÑn. Falso si no.
	 */
	private boolean existeInscripcion() {
		PreparedStatement encontrarIdPst = null;
		ResultSet rsInscripcion = null;
		try {
			encontrarIdPst = c.prepareStatement(EXISTE_INSCRIPCION);
			encontrarIdPst.setInt(1, this.idAtleta);
			encontrarIdPst.setInt(2, this.idCompeticion);
			rsInscripcion = encontrarIdPst.executeQuery();
			if (rsInscripcion.next()) {

				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsInscripcion, encontrarIdPst);
		}
		return false;
	}

}
