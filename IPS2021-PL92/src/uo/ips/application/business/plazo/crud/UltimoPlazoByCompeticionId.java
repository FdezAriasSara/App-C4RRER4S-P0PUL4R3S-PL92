package uo.ips.application.business.plazo.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class UltimoPlazoByCompeticionId {
	private int id;
	private Connection c;
	private static final String FIND_ULTIMO_PLAZO = "SELECT fechaFin from Plazos WHERE idCompeticion=? and fechaFin =( SELECT MAX(fechaFin) FROM Plazos WHERE idCompeticion=?)";

	public UltimoPlazoByCompeticionId(int competicionId) {
		Argument.isTrue(competicionId > 0, "El id de la competici�n no puede ser negativo!");
		this.id = competicionId;

	}

	public Date execute() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(FIND_ULTIMO_PLAZO);
			pst.setInt(1, id);
			pst.setInt(2, id);
			rs = pst.executeQuery();
			if (rs.next()) {

				return rs.getDate(1);
			} else {
				throw new BusinessException("No se ha encontrado el �ltimo plazo.");
			}
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(pst);
			Jdbc.close(c);
		}
	}

}
