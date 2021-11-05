package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaDto;

public class AnadirAtleta {

	private String AÑADIR_ATLETA = "INSERT INTO Atleta (DNI,Email,fechaNacimiento,Name,sexo,Surname) VALUES (?,?,?,?,?,?)";
	private String BUSCAR_ATLETA_POR_DNI = "SELECT * FROM Atleta WHERE dni = ?";
	private String BUSCAR_ATLETA_POR_EMAIL = "SELECT * FROM Atleta WHERE email = ?";
	private AtletaDto atleta;

	public AnadirAtleta(AtletaDto atleta) {
		this.atleta = atleta;
	}

	public AtletaDto execute() throws BusinessException {

		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			checkDni();
			checkEmail();
			c = Jdbc.getConnection();

			pst = c.prepareStatement(AÑADIR_ATLETA);
			pst.setString(1, atleta.dni);
			pst.setString(2, atleta.email);
			pst.setDate(3, atleta.fechaNacimiento);
			pst.setString(4, atleta.nombre);
			pst.setString(5, atleta.sexo);
			pst.setString(6, atleta.apellido);
		

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
		return atleta;
	}

	/**
	 * IMPORTANTE separar el chequeo del dni , de el del email. Así se comprueba
	 * también que el email no esté registrado en el sistema ya, pero con otro
	 * atleta con dni diferente.
	 * 
	 * @throws BusinessException
	 */
	private void checkEmail() throws BusinessException {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(BUSCAR_ATLETA_POR_EMAIL);
			pst.setString(1, atleta.email);

			rs = pst.executeQuery();

			if (rs.next()) {
				throw new BusinessException(String.format("El correo %s ya está registrado.", atleta.email));
			}
			pst.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}
	/**
	 * Comprueba que no hay un atleta con el mismo dni.
	 * @throws BusinessException
	 */

	private void checkDni() throws BusinessException {

		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(BUSCAR_ATLETA_POR_DNI);
			pst.setString(1, atleta.dni);

			rs = pst.executeQuery();

			if (rs.next()) {
				throw new BusinessException(String.format("El atleta con dni %s ya está registrado.", atleta.dni));
			}
			pst.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}
}
