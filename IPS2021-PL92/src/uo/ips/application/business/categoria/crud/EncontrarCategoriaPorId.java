package uo.ips.application.business.categoria.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.categoria.CategoriaDto;

public class EncontrarCategoriaPorId {
	private static final String ENCONTRAR_CATEGORIA = "select * from Categoria where idCategoria=?";
	private int idCategoria;
	private Connection c;

	public EncontrarCategoriaPorId(int idCategoria) {
		Argument.isTrue(idCategoria > 0);
		this.idCategoria = idCategoria;
	}

	public CategoriaDto execute() throws BusinessException {
		ResultSet rs = null;
		PreparedStatement pst = null;
		CategoriaDto dto = new CategoriaDto();

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(ENCONTRAR_CATEGORIA);

			pst.setInt(1, idCategoria);
			rs = pst.executeQuery();
			if (rs.next()) {
				dto.edadMax = rs.getInt("edadMax");
				dto.edadMin = rs.getInt("edadMin");
				dto.idCategoria = String.valueOf(rs.getInt("idCategoria"));
				dto.nombreCategoria = rs.getString("nombreCategoria");
				dto.sexo = rs.getString("sexo");
				return dto;

			}
			throw new BusinessException("No se encontró la categoría");

		} catch (

		SQLException w) {
			throw new BusinessException(w.getMessage());
		}

	}

}
