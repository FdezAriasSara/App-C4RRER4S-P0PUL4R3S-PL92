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
	private final String datos = "select c.distanciaKm ,i.idAtleta, i.club , i.idCategoria , i.tiempoQueTarda , i.posicionFinal  from Inscripcion i, Competicion c where ? = i.dorsal and ? = c.idCompeticion";

	private int dorsal;
	private Connection c;

	private int competicionId;

	public ObtenerAtletasParaComparar(int dorsal, int competicionId) {
		Argument.isTrue(competicionId > 0);
		Argument.isTrue(dorsal > 0);
		this.dorsal = dorsal;
		this.competicionId = competicionId;
	}

	public AtletaInscritoDto execute() throws BusinessException {

		ResultSet rsInscripciones = null;
		PreparedStatement pstInscripciones = null;

		try {
			c = Jdbc.getConnection();

			pstInscripciones = c.prepareStatement(datos);

			pstInscripciones.setInt(1, dorsal);
			pstInscripciones.setInt(2, competicionId);
			rsInscripciones = pstInscripciones.executeQuery();
			if (rsInscripciones.next()) {
				AtletaInscritoDto atl = new AtletaInscritoDto();

				atl.idCompeticion = competicionId;
				atl.club = rsInscripciones.getString("club");
				atl.idCategoria = rsInscripciones.getInt("idCategoria");
				atl.posicionFinal = rsInscripciones.getInt("posicionFinal");
				atl.tiempoQueTarda = rsInscripciones.getTime("tiempoQueTarda");
				atl.idAtleta = rsInscripciones.getInt("idAtleta");

				atl.ritmoPorKm = calcularRitmoPorKm(atl)
						/ rsInscripciones.getInt("distanciaKm");

				return atl;
			}
			throw new BusinessException("No se encontró la inscripcion");

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