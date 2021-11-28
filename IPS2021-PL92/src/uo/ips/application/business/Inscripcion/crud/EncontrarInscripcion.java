package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class EncontrarInscripcion {
	private static final String INSCRIPCION = "select * from Inscripcion where idAtleta=? and idCompeticion=?";
	private int idAtleta;
	private int idCompeticion;
	private Connection c;

	public EncontrarInscripcion(int idAtleta, int idCompeticionSeleccionada) {
		Argument.isTrue(idAtleta > 0);
		Argument.isTrue(idCompeticionSeleccionada > 0);
		this.idAtleta = idAtleta;
		this.idCompeticion = idCompeticionSeleccionada;
	}

	public InscripcionDto execute() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		InscripcionDto dto = new InscripcionDto();
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(INSCRIPCION);
			pst.setInt(1, idAtleta);
			pst.setInt(2, idCompeticion);

			rs = pst.executeQuery();
			if (rs.next()) {
				dto = ensamblarDto(rs);

			} else {
				throw new BusinessException("La inscripción  no existe");
			}

		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs, pst);

		}
		return dto;

	}

	public InscripcionDto ensamblarDto(ResultSet rs) throws SQLException {
		InscripcionDto dto;
		String club = rs.getString("club");
		int dorsal = rs.getInt("dorsal");
		String estado = rs.getString("estado");
		Date fechaInscripcion = rs.getDate("fechaInscripcion");
		int idCategoria = rs.getInt("idCategoria");
		Date ultimoCambio = new Date(System.currentTimeMillis());
		int posicionFinal = rs.getInt("posicionFinal");
		Time tiempoQueTarda = rs.getTime("tiempoQueTarda");
		dto = new InscripcionDto(idCompeticion, idAtleta, estado,
				fechaInscripcion, ultimoCambio, idCategoria, posicionFinal,
				tiempoQueTarda);
		dto.club = club;
		dto.dorsal = dorsal;
		return dto;
	}

}
