package uo.ips.application.business.arco.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class ObtenerCuandoPasoPorArco {

	private int idArco;
	private int dorsal;
	private int idCompeticion;
	private final static String ENCONTRAR_TIEMPO = "SELECT tiempo FROM ArcoTiempo where idArco=? and idCompeticion=? and dorsal=?";
	private Connection c;

	public ObtenerCuandoPasoPorArco(int idArco, int idCompeticion, int dorsal) {
		this.idArco = idArco;
		this.dorsal = dorsal;
		this.idCompeticion = idCompeticion;
	}

	public Time execute() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(ENCONTRAR_TIEMPO);
			pst.setInt(1, idArco);
			pst.setInt(2, idCompeticion);
			pst.setInt(3, dorsal);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getTime(1);
			}
		} catch (SQLException e) {
			throw new BusinessException(e.getMessage());
		}
		return null;

	}

}
