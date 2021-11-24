package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;

public class ObtenerAtletasParaComparar {
	private final String datos = "select, c.distanciaKm ,i.dorsal, i.club , i.idCategoria , i.tiempoQueTarda ,i.sexo , i.posicionFinal  from Inscripcion i, Competicion c where ? = i.idAtleta and ? = c.idCompeticion";

	private int idAtleta;
	private Connection c;

	private int competicionId;

	public ObtenerAtletasParaComparar(int idAtleta, int competicionId) {
		Argument.isTrue(competicionId > 0);
		Argument.isTrue(idAtleta > 0);
		this.idAtleta = idAtleta;
		this.competicionId = competicionId;
	}

	public AtletaInscritoDto execute() throws BusinessException {

		ResultSet rsInscripciones = null;
		PreparedStatement pstInscripciones = null;

		try {
			c = Jdbc.getConnection();

			pstInscripciones = c.prepareStatement(datos);

			pstInscripciones.setInt(1, idAtleta);
			pstInscripciones.setInt(2, competicionId);
			rsInscripciones = pstInscripciones.executeQuery();

			AtletaInscritoDto atl = new AtletaInscritoDto();

			atl.idCompeticion = competicionId;
			atl.dorsal = rsInscripciones.getInt("dorsal");
			atl.club = rsInscripciones.getString("club");
			atl.idCategoria = rsInscripciones.getInt("idCategoria");
			atl.posicionFinal = rsInscripciones.getInt("posicionFinal");
			atl.tiempoQueTarda = rsInscripciones.getTime("tiempoQueTarda");
			atl.sexo = rsInscripciones.getString("sexo");

			atl.ritmoPorKm = calcularRitmoPorKm(atl)
					/ rsInscripciones.getInt("distanciaKm");

			return atl;

		} catch (

		SQLException w) {
			throw new BusinessException(w.getMessage());
		}

	}

	private int calcularRitmoPorKm(AtletaInscritoDto atl) {
		int minutos = atl.tiempoQueTarda.toLocalTime().getMinute()
				+ atl.tiempoQueTarda.toLocalTime().getHour() * 60;
		return minutos;
	}
}