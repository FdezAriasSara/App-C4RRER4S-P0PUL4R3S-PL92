package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;

public class PagarConTarjeta {
	private PagoDto metodo;

	public PagarConTarjeta(PagoDto pago) {
		this.metodo=pago;
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

			rs = pst.executeQuery();
			// (..)
			pst.close();
			// pst = c.prepareStatement(query);
			// (..)
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return new PagoDto();// (..)
	}

}
