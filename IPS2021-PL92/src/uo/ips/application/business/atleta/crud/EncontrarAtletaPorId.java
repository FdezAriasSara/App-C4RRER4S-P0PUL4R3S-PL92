package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaDto;

public class EncontrarAtletaPorId {

	private String SQL_FindByid = "select * from Atleta where idAtleta = ?";
	private int id;

	public EncontrarAtletaPorId(int idAtleta) {
		Argument.isTrue(idAtleta > 0, "El id del atleta no puede ser negativo");
		this.id = idAtleta;
	}

	public AtletaDto execute() throws BusinessException {

		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		AtletaDto atleta = new AtletaDto();
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_FindByid);
			pst.setInt(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {
				atleta.apellido = rs.getString("Surname");
				atleta.dni = rs.getString("dni");
				atleta.idAtleta = rs.getInt("idAtleta");
				atleta.email = rs.getString("email");
				atleta.nombre = rs.getString("Name");
				atleta.fechaNacimiento = rs.getDate("fechaNacimiento");
				atleta.sexo = rs.getString("sexo");
				System.out.println(atleta.sexo);

			} else {
				throw new BusinessException("No se encontró al atleta");
			}

		} catch (SQLException e) {
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return atleta;
	}

}
