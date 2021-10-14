package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import alb.util.assertion.Argument;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class PagarConTarjeta {
	private PagoDto pago;
	private TarjetaDto tarjeta;

	private String CAMBIAR_ESTADO_INSCRIPCION = "UPDATE Inscripción WHERE idAtleta=? and idInscripcion=? SET estado='Inscrito'  ";
	private String ENCONTRAR_ID_INSCRIPCION = "SELECT idInscripcion WHERE idAtleta=? and idCompetición=? ";
	private String REALIZAR_PAGO = "INSERT INTO Pago (idAtleta,idPago,fechaPago,importe) VALUES (?,?,?,?) ";
	private String idInscripcion;

	public PagarConTarjeta(PagoDto pago, TarjetaDto tarjeta) {
		Argument.isNotNull(tarjeta, "La tarjeta no puede ser null");
		Argument.isNotNull(pago, "El pago no puede ser null");
		this.pago = pago;
		this.tarjeta = tarjeta;
	}

	public PagarConTarjeta() {

	}

	public PagoDto execute() throws BusinessException {
		// Process
		Connection c = null;

		PreparedStatement pagoPst = null;
		PreparedStatement encontrarIdPst = null;
		PreparedStatement cambiarEstadoPst = null;
		ResultSet rsInscripcion = null;

		try {
			c = Jdbc.getConnection();

			pagoPst = c.prepareStatement(REALIZAR_PAGO);
			// Realización del pago
			pago.pagoId = UUID.randomUUID().toString();
			pagoPst.setString(1, pago.idAtleta);
			pagoPst.setString(2, pago.pagoId);
			pagoPst.setDate(3, pago.fechaPago);
			pagoPst.setLong(4, pago.importe);//el importe sale de la cuota de la inscripición
			
			pagoPst.executeQuery(REALIZAR_PAGO);

			simularPago();
			// Para cambiar el estado de la inscripción necesito el idInscripicón
			// accedo a él mediante los atributos idAtleta e idCompeticion de pagoDto
			encontrarIdPst = c.prepareStatement(ENCONTRAR_ID_INSCRIPCION);
			encontrarIdPst.setString(1, pago.idAtleta);
			encontrarIdPst.setString(2, pago.idCompeticion);
			rsInscripcion = encontrarIdPst.executeQuery(ENCONTRAR_ID_INSCRIPCION);
			if (rsInscripcion.next()) {
				this.idInscripcion = rsInscripcion.getString(0);
				// Al ser una clave primaria , la combinación claveAtleta-competción no puede
				// darse más de una vez

			} else {
				throw new BusinessException("No pudo encontrarse la inscripicón para la competición con id:"
						+ pago.idCompeticion+ " del atleta con id:" + pago.idAtleta);
			}

			// Cambio de 'Pre-inscrito' a 'Inscrito'
			cambiarEstadoPst = c.prepareStatement(CAMBIAR_ESTADO_INSCRIPCION);
			cambiarEstadoPst.setString(1, pago.idAtleta);

			cambiarEstadoPst.setString(2, this.idInscripcion);

			cambiarEstadoPst.executeQuery(CAMBIAR_ESTADO_INSCRIPCION);

			emitirJustificante(pago);

			cambiarEstadoPst.close();

			cambiarEstadoPst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pagoPst);
			Jdbc.close(rsInscripcion, encontrarIdPst);

			Jdbc.close(cambiarEstadoPst);
			Jdbc.close(c);

		}
		return new PagoDto();
	}

	private String emitirJustificante(PagoDto pago) {
		return pago.toString();
	
	}

	private void simularPago() {
		System.out.println("Se ha realizado un pago con la tarjeta " + tarjeta.numeroTarjeta);

	}

}
