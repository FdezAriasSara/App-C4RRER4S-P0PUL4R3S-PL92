package uo.ips.application.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;

public class Sesion {
	public static final int NO_INICIADO = 0;
	private int idAtleta;
	private int idCompeticion;

	private String SQLGetId = "SELECT idAtleta FROM Atleta WHERE email = ?";
	private String SQLGetDorsal = "SELECT dorsal FROM Inscripcion i WHERE i.idCompeticion=?  and i.idAtleta=?";
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public Sesion(String email, int idCompeticion) {
		this.idAtleta = getIdAtleta(email);
		this.idCompeticion = idCompeticion;
	}

	/*
	 * Constructor creado para funcionalidad de registrarse con email.
	 */
	public Sesion(String email) {
		this.idAtleta = getIdAtleta(email);

	}

	public int getIdAtleta() {

		return idAtleta;
	}

	public int getIdCompeticion() {
		return idCompeticion;
	}

	private int getIdAtleta(String email) {
		int idAtleta = NO_INICIADO;
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQLGetId);
			pst.setString(1, email);

			ResultSet emailRs = pst.executeQuery();

			if (emailRs.next())
				idAtleta = emailRs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return idAtleta;
	}

	public int getDorsalAtleta(int idCompeticion) {
		int dorsal = NO_INICIADO;
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQLGetDorsal);
			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				dorsal = rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return dorsal;
	}

}
