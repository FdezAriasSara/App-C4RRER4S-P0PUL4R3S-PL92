package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;

public class InscribirAtletaListaEspera {
	private String SqlPuestoEspera = "select max(puesto_espera) from Inscripcion where idCompeticion = ?";
	private String SQLGetId = "SELECT idAtleta FROM Atleta WHERE email = ?";
	private String SQLGetInscripcion = "SELECT * FROM Inscripcion WHERE idCompeticion = ? AND idAtleta = ?";
	private String SQLAddInscripcion = "INSERT INTO Inscripcion (idCompeticion,idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria,posicionFinal,tiempoQueTarda, puesto_espera) VALUES (?,?,?,?,?,?,?,?,?)";

	private String emailAtleta;
	private int idCompeticion;
	private int idAtleta;
	private int idCategoria;

	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public InscribirAtletaListaEspera(String emailAtleta, int idCompeticion) throws BusinessException {
		this.emailAtleta = emailAtleta;
		this.idCompeticion = idCompeticion;
		try {
			c = Jdbc.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.idAtleta = getIdAtleta();
		this.idCategoria = getIdCategoria();
	}

	public int execute() throws BusinessException {
		int posLista;
		try {

			if (yaEstaInscrito()) {
				throw new BusinessException("El atleta ya esta inscrito en la competicion");
			}

			posLista = getPosListaEspera() + 1;

			pst = c.prepareStatement(SQLAddInscripcion);

			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);
			pst.setString(3, "LISTA_ESPERA");
			pst.setDate(4, new Date(System.currentTimeMillis()));
			pst.setDate(5, new Date(System.currentTimeMillis()));
			pst.setInt(6, idCategoria);
			pst.setInt(7, -1);
			pst.setString(8, "00:00:00");
			pst.setInt(9, posLista);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return posLista;
	}

	private int getIdAtleta() {
		int idAtleta = 0;
		try {

			pst = c.prepareStatement(SQLGetId);
			pst.setString(1, emailAtleta);

			rs = pst.executeQuery();

			if (rs.next())
				idAtleta = rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return idAtleta;
	}

	private int getPosListaEspera() {
		int pos = 1;
		try {

			pst = c.prepareStatement(SqlPuestoEspera);
			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			if (rs.next())
				pos = rs.getInt(1);
			if (pos == 0)
				pos = 1;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

		return pos;
	}

	private int getIdCategoria() throws BusinessException {
		return BusinessFactory.forAtletaCrudService().CalcularCategoria(idAtleta, idCompeticion);
	}

	private boolean yaEstaInscrito() throws SQLException {
		pst = c.prepareStatement(SQLGetInscripcion);
		pst.setInt(1, idCompeticion);
		pst.setInt(2, idAtleta);
		rs = pst.executeQuery();
		boolean inscrito = rs.next();
		Jdbc.close(rs, pst);
		return inscrito;
	}
}
