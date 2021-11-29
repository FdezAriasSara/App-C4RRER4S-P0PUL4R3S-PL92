package uo.ips.application.business.plazo.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class ObtenerCuotaDeInscripcion {
	private static final String ENCONTRAR_PLAZO = "select * from Plazos where idCompeticion=? and fechaInicio<=? and fechaFin>=?";
	private int idCompeticion;
	private Date fechaInscripcion;
	private Connection c;

	public ObtenerCuotaDeInscripcion(int idCompeticion, Date fechaInscripcion) {
		this.idCompeticion = idCompeticion;
		this.fechaInscripcion = fechaInscripcion;
	}

	public double execute() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(ENCONTRAR_PLAZO);
			pst.setInt(1, idCompeticion);
			pst.setDate(2, fechaInscripcion);
			pst.setDate(3, fechaInscripcion);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getDouble("cuota");
			}
		} catch (SQLException e) {
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return idCompeticion;
	}

}
