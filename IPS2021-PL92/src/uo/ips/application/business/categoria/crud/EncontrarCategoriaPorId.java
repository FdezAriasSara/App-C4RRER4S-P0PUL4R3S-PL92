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
	private String idCategoria;
	private Connection c;

	public EncontrarCategoriaPorId(String idCategoria) {
		Argument.isNotNull(idCategoria,
				"El id de la categoria no puede ser null");
		Argument.isNotEmpty(idCategoria,
				"El id de la categoria no puede ser vacío");
		this.idCategoria = idCategoria;
	}

	public CategoriaDto execute() throws BusinessException {
		ResultSet rsInscripciones = null;
		PreparedStatement pstInscripciones = null;
		CategoriaDto dto = new CategoriaDto();

		try {
			c = Jdbc.getConnection();

			pstInscripciones = c.prepareStatement(ENCONTRAR_CATEGORIA);

			pstInscripciones.setInt(1, Integer.valueOf(idCategoria));
			rsInscripciones = pstInscripciones.executeQuery();
			dto.edadMax = rsInscripciones.getInt("edadMax");
			dto.edadMin = rsInscripciones.getInt("edadMin");
			dto.idCategoria = String
					.valueOf(rsInscripciones.getInt("idCategoria"));
			dto.nombreCategoria = rsInscripciones.getString("nombreCategoria");
			dto.sexo = rsInscripciones.getString("sexo");
			return dto;

		} catch (

		SQLException w) {
			throw new BusinessException(w.getMessage());
		}

	}

}
