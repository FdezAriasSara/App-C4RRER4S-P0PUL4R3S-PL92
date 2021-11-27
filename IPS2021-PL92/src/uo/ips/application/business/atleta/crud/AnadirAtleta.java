package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaDto;

public class AnadirAtleta {

	private String AÑADIR_ATLETA = "INSERT INTO Atleta (DNI,Email,fechaNacimiento,Name,sexo,Surname,idAtleta) VALUES (?,?,?,?,?,?,?)";
	private String BUSCAR_ATLETA_POR_DNI = "SELECT * FROM Atleta WHERE dni = ?";
	private String BUSCAR_ATLETA_POR_EMAIL = "SELECT * FROM Atleta WHERE email = ?";
	private String ID_MAXIMO = "select max(idAtleta) from Atleta";

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
			int id = getId();
			pst.setInt(7, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}
		return atleta;
	}

	private int getId() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			c = Jdbc.getConnection();

			pst = c.prepareStatement(ID_MAXIMO);

			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);

		}
		return 0;
	}

	/**
	 * IMPORTANTE separar el chequeo del dni , de el del email. As� se comprueba
	 * tambi�n que el email no est� registrado en el sistema ya, pero con otro
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
				throw new BusinessException(String.format(
						"El correo %s ya est� registrado.", atleta.email));
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
	 * 
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
				throw new BusinessException(String.format(
						"El atleta con dni %s ya est� registrado.",
						atleta.dni));
			}
			pst.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}
}
