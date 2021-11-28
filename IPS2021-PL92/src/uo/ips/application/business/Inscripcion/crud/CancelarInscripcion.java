package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class CancelarInscripcion {

	private static final String CANCELAR_INSCRIPCION = "UPDATE Inscripcion SET estado='CANCELADA' WHERE idAtleta=? and idCompeticion=?";
	private static final String EXISTE_INSCRIPCION = "select * from Inscripcion where idAtleta=? and idCompeticion=?";
	private int idAtleta;
	private Connection c;
	private int idCompeticion;

	public CancelarInscripcion(int idAtleta, int idCompeticion) {
		Argument.isTrue(idAtleta > 0);
		Argument.isTrue(idCompeticion > 0);
		this.idAtleta = idAtleta;
		this.idCompeticion = idCompeticion;
	}

	public void execute() throws BusinessException {
		checkInscripcionExiste();
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(CANCELAR_INSCRIPCION);
			pst.setInt(1, idAtleta);
			pst.setInt(2, idCompeticion);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {
			Jdbc.close(c);
			Jdbc.close(pst);

		}
	}

	public void checkInscripcionExiste() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(EXISTE_INSCRIPCION);
			pst.setInt(1, idAtleta);
			pst.setInt(2, idCompeticion);

			rs = pst.executeQuery();
			if (rs.next()) {

			} else {
				throw new BusinessException(
						"La inscripción que intentas cancelar no existe");
			}

		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs, pst);

		}
	}

}
