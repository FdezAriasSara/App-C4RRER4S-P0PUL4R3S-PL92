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

	private String CAMBIAR_ESTADO_INSCRIPCION = "UPDATE Inscripción WHERE idAtleta=? SET estado='Inscrito'  ";
	private String REALIZAR_PAGO = "INSERT INTO Pago (idAtleta,idPago,fechaPago,importe) VALUES (?,?,?,?) ";

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
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(REALIZAR_PAGO);
			//Realización del pago 
			pago.pagoId= UUID.randomUUID().toString();
			pst.setString(1, pago.idAtleta);
			pst.setString(2, pago.pagoId);
			pst.setDate(3, pago.fechaPago);
			pst.setLong(4, pago.importe);
			
			pst.executeQuery(REALIZAR_PAGO);
			simularPago();
			//Cambio de 'Pre-inscrito' a 'Inscrito' 
			pst = c.prepareStatement(CAMBIAR_ESTADO_INSCRIPCION);
			pst.setString(1, pago.idAtleta);
			rs = pst.executeQuery();

			emitirJustificante();

			pst.close();

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return new PagoDto();
	}

	private void emitirJustificante() {
	//TODO: Hacer operaciones fileutil necesarias para emitir el justificante
	}

	private void simularPago() {
		System.out.println("Se ha realizado un pago con la tarjeta " + tarjeta.numeroTarjeta);

	}

}
