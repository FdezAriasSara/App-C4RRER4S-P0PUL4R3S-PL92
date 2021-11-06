package uo.ips.application.business.registro.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;

public class ComprobarDatos {
	private String BUSCAR_ATLETA_POR_EMAIL = "SELECT * FROM Atleta WHERE email = ?";
	private String email;
	public ComprobarDatos(String email) {
		Argument.isNotEmpty(email);
		Argument.isNotNull(email);
		this.email=email;
	}
	public boolean execute()  {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(BUSCAR_ATLETA_POR_EMAIL);
			pst.setString(1, email);

			rs = pst.executeQuery();

			if (rs.next()) {
				return true;//el atleta está registrado
			}
			return false;//el atleta no está registrado
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}
	}


