package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class TieneListaEspera {
	private String SQL_Tiene_lista_espera = "select tieneListaEspera from Competicion where idCompeticion = ?";

	private Connection c = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	private int idCompeticion;

	public TieneListaEspera(int idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public boolean execute() throws BusinessException {
		boolean tiene = false;
		try {

			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_Tiene_lista_espera);

			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			if (rs.next())
				tiene = rs.getBoolean(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return tiene;
	}
}
