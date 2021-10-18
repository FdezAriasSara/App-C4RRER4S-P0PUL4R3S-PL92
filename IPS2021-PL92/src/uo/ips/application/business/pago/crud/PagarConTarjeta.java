package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import alb.util.assertion.Argument;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class PagarConTarjeta {
	private PagoDto pago;
	private TarjetaDto tarjeta;

	private static String CAMBIAR_ESTADO_INSCRIPCION = "UPDATE Inscripcion  SET estado='INSCRITO'  WHERE idAtleta=? and idCompeticion=?";
	private static String ESTADO_ACTUAL_INSCRIPCION = "SELECT estado FROM Inscripcion WHERE idAtleta=? and idCompeticion=?";
	private static String EXISTE_INSCRIPCION = "SELECT * FROM Inscripcion WHERE idAtleta=? and idCompeticion=?";
	private static String REALIZAR_PAGO = "INSERT INTO Pago (idAtleta,idPago,fechaPago,importe,idCompeticion) VALUES (?,?,?,?,?) ";

	private Connection c;

	public PagarConTarjeta(PagoDto pago, TarjetaDto tarjeta) {
		Argument.isNotNull(tarjeta, "La tarjeta no puede ser null");
		Argument.isNotNull(pago, "El pago no puede ser null");
		this.pago = pago;
		this.tarjeta = tarjeta;
	}

	public PagarConTarjeta() {

	}

	public PagoDto execute() throws BusinessException {

		c = null;

		PreparedStatement pagoPst = null;

		PreparedStatement cambiarEstadoPst = null;

		try {
			c = Jdbc.getConnection();
			// Para realizar el pago primero debo comprobar que existe la inscripción, al
			// igual que para cambiar el estado de la misma.
			if (!existeInscripcion()) {

				throw new BusinessException("La inscripción del atleta con id :" + pago.idAtleta
						+ " para la competición con id: " + pago.idCompeticion + " no existe.");

			}
			//Si la inscripción ya ha sido marcada como pagada , tenemos que evitar el pago
			//la inscripción que no tiene pago es aquella que esta en estado pre-inscrito
			if (!estadoPreInscrito()) {

				throw new BusinessException("La inscripción del atleta con id :" + pago.idAtleta
						+ " para la competición con id: " + pago.idCompeticion + " ya está pagada.");

			}

			// Realización del pago una vez sabemos que existe la inscripción
			pagoPst = c.prepareStatement(REALIZAR_PAGO);
			pago.pagoId = UUID.randomUUID().toString();
			pagoPst.setInt(1, pago.idAtleta);
			pagoPst.setString(2, pago.pagoId);
			pago.fechaPago = LocalDate.now();
			Date fechaPago = Date.valueOf(pago.fechaPago);
			pagoPst.setDate(3, fechaPago);
			pagoPst.setLong(4, pago.importe);// el importe sale de la cuota de la inscripición
			pagoPst.setInt(5, pago.idCompeticion);
			pagoPst.executeUpdate();

			simularPago();

			// Cambio de 'Pre-inscrito' a 'Inscrito'
			cambiarEstadoPst = c.prepareStatement(CAMBIAR_ESTADO_INSCRIPCION);
			cambiarEstadoPst.setInt(1, pago.idAtleta);

			cambiarEstadoPst.setInt(2, pago.idCompeticion);

			cambiarEstadoPst.executeUpdate();

			emitirJustificante(pago);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pagoPst);

			Jdbc.close(cambiarEstadoPst);
			Jdbc.close(c);

		}
		return this.pago;
	}
/**
 * Comprueba el estado de la inscripción.
 * @return true si el estado es "pre-inscrito". Falso si no.
 */
	private boolean estadoPreInscrito() {
		PreparedStatement encontrarIdPst = null;
		ResultSet rsInscripcion = null;
		try {
			encontrarIdPst = c.prepareStatement(ESTADO_ACTUAL_INSCRIPCION);
			encontrarIdPst.setInt(1, pago.idAtleta);
			encontrarIdPst.setInt(2, pago.idCompeticion);
			rsInscripcion = encontrarIdPst.executeQuery();
			if (rsInscripcion.next()) {
				if (rsInscripcion.getString(1).equals("PRE-INSCRITO"))
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
	 * Comprueba la existencia de la inscripción.
	 * @return true si existe una inscripción. Falso si no.
	 */
	private boolean existeInscripcion() {
		PreparedStatement encontrarIdPst = null;
		ResultSet rsInscripcion = null;
		try {
			encontrarIdPst = c.prepareStatement(EXISTE_INSCRIPCION);
			encontrarIdPst.setInt(1, pago.idAtleta);
			encontrarIdPst.setInt(2, pago.idCompeticion);
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

	private String emitirJustificante(PagoDto pago) {
		return pago.toString();

	}

	private void simularPago() {
		System.out.println("Se ha realizado un pago con la tarjeta " + tarjeta.numeroTarjeta);

	}

}
