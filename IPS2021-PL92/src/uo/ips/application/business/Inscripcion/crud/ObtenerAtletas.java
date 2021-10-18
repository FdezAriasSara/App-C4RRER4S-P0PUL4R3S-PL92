package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.atleta.AtletaDto;

public class ObtenerAtletas {
	private String SQL = "SELECT * FROM Inscripcion i, Atleta a WHERE idCompeticion = ? and i.idAtleta = a.idAtleta ORDER BY i.fechaInscripcion,i.estado;";
	private String SQL_Existe_Comp = "SELECT * FROM Competicion WHERE idCompeticion = ?";

	private int idCompeticion;
	private List<AtletaDto> atletas = new ArrayList<AtletaDto>();

	private Connection c = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	public ObtenerAtletas(int idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public String execute() throws BusinessException {
		String res = "";
		try {

			c = Jdbc.getConnection();

			if (!competicionExiste()) {
				throw new BusinessException("La competicion no existe");
			}

			pst = c.prepareStatement(SQL);

			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			while (rs.next()) {
				res += "dni: " + rs.getString("dni");
				res += " - nombre: " + rs.getString("name");
				res += " - categoria: " + rs.getString("idCategoria");
				res += " - fecha de inscripcion: " + rs.getString("fechaInscripcion");
				res += " - estado: " + rs.getString("estado") + "\n\n";
			}

			atletas = DtoAssembler.toAtletaDtoList(rs);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return res;
	}

	private boolean competicionExiste() throws SQLException {

		pst = c.prepareStatement(SQL_Existe_Comp);

		pst.setInt(1, idCompeticion);

		rs = pst.executeQuery();

		return rs.next();

	}
}
