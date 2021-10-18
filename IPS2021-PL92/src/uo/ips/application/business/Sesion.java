package uo.ips.application.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;

public class Sesion {
	private int idAtleta;
	private int idCompeticion;

	private String SQLGetId = "SELECT idAtleta FROM Atleta WHERE email = ?";

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
		int idAtleta = 0;
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

}
