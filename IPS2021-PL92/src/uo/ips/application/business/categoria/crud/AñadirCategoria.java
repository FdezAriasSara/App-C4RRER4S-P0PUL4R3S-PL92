package uo.ips.application.business.categoria.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.categoria.CategoriaDto;

public class AñadirCategoria {

	private String SQL = "INSERT INTO Categoria (nombreCategoria,edadMin,edadMax,sexo) VALUES (?,?,?,?)";
	private String SQL_CONTIENE = "INSERT INTO ContieneCategoria (idCompeticion,idCategoria) VALUES (?,?)";
	private String SQLGetCompeticion = "SELECT * FROM Categoria WHERE idCategoria = ?";
	private CategoriaDto categoria;
	private int competicionId;

	public AñadirCategoria(CategoriaDto categoria, int competicionId) {
		Argument.isNotNull(categoria);
		this.categoria = categoria;
		this.competicionId = competicionId;
	}

	public CategoriaDto execute() throws BusinessException {
		// Process
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQLGetCompeticion);
			pst.setString(1, categoria.idCategoria);

			rs = pst.executeQuery();

			while (rs.next()) {
				throw new BusinessException("Ya existe la categoria");
			}
			pst.close();
			pst = c.prepareStatement(SQL, new String[] { "idCompeticion" });
			pst.setString(1, categoria.nombreCategoria);
			pst.setInt(2, categoria.edadMin);
			pst.setInt(3, categoria.edadMax);
			pst.setString(4, categoria.sexo);
			pst.executeUpdate();

			ResultSet rsId = pst.getGeneratedKeys();
			if (rsId.next()) {
				int id = rsId.getInt(1);
				pst.close();
				pst = c.prepareStatement(SQL_CONTIENE);
				pst.setInt(1, competicionId);
				pst.setString(2, Integer.toString(id));
				pst.executeUpdate();

			}
			rsId.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return categoria;
	}

}
