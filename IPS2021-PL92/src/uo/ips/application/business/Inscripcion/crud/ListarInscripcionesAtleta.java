package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class ListarInscripcionesAtleta {
	private final String FORMATO_INFORMACION = "CompeticiÑn:%s \n\t-Estado de la inscripciÑn:%s\n\t-Fecha del Ñltimo cambio :%s\n";
	private final String INSCRIPCIONES_ASOCIADAS = "SELECT estado, idCompeticion, fechaUltimoCambio FROM Inscripcion WHERE idAtleta=? ORDER BY fechaUltimoCambio DESC";
	// El nombre de la competiciÑn a la que pertenece la inscripciÑn estÑ en la
	// tabla competiciÑn, no en InscripciÑn. Por ello:
	private final String NOMBRE_COMPETICION = "SELECT nombre FROM Competicion WHERE idCompeticion=?";
	private final String EXISTE_COMPETICION = "SELECT * FROM Competicion WHERE idCompeticion=?";
	private final String EXISTE_ATLETA = "SELECT * FROM Atleta WHERE idAtleta=?";
	private int idAtleta;
	private Connection c;
	private List<String> inscripciones;

	public ListarInscripcionesAtleta(int idAtleta) {
		this.idAtleta = idAtleta;
		
	}

	public List<String> execute() throws BusinessException {

		this.inscripciones = new ArrayList<String>();
		ResultSet rsInscripciones = null;
		ResultSet rsNombres = null;
		PreparedStatement pstInscripciones = null;
		PreparedStatement pstNombresComp = null;

		try {
			c = Jdbc.getConnection();
			pstNombresComp = c.prepareStatement(NOMBRE_COMPETICION);
			pstInscripciones = c.prepareStatement(INSCRIPCIONES_ASOCIADAS);

			// Busamos las inscripciones asociadas al id.
			pstInscripciones.setInt(1, idAtleta);
			rsInscripciones = pstInscripciones.executeQuery();
			String estado, fechaCambios, nombreCompeticion, actual;
			int idCompeticion;
			while (rsInscripciones.next()) {
				// recogemos los datos de InscripciÑn que necesitamos

				estado = rsInscripciones.getString(1);
				// necesitamos el id de la competiciÑn para poder acceder al nombre.
				idCompeticion = Integer.parseInt(rsInscripciones.getString(2));
				fechaCambios = rsInscripciones.getString(3);

				if (!competicionExiste(idCompeticion)) {
					throw new BusinessException(String.format("No existe competiciÑn con  id (%d). ", idCompeticion));
				} else if(!atletaExiste(idAtleta)){
					throw new BusinessException(String.format("No existe el atleta con  id (%d). ", idAtleta));
				}else {// por cada inscripciÑn , necesitamos encontrar tambiÑn el nombre de la
				
						// competiciÑn.
					pstNombresComp.setInt(1, idCompeticion);
					rsNombres = pstNombresComp.executeQuery();
					if(rsNombres.next()) {
						nombreCompeticion=	rsNombres.getString(1);
						actual = String.format(FORMATO_INFORMACION, nombreCompeticion, estado, fechaCambios);// Creamos la
						// string
						this.inscripciones.add(actual);// la aÑadimos a la lista de datos.
					}
						
				}
				
				
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsInscripciones, pstInscripciones);
			Jdbc.close(rsNombres, pstNombresComp);
			Jdbc.close(c);

		}

		return this.inscripciones;// devolvemos la lista con los "toString" de cada inscripciÑn.
	}

	private boolean competicionExiste(int idCompeticion) {
		ResultSet rsComp = null;
		PreparedStatement pstComp = null;
		try {
			
			pstComp = c.prepareStatement(EXISTE_COMPETICION);
			pstComp.setInt(1, idCompeticion);
			rsComp = pstComp.executeQuery();
			if (rsComp.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsComp, pstComp);
		}
		return false;
	}
	private boolean atletaExiste(int idAtleta) {
		ResultSet rsComp = null;
		PreparedStatement pstComp = null;
		try {

			pstComp = c.prepareStatement(EXISTE_ATLETA);
			pstComp.setInt(1, idAtleta);
			rsComp = pstComp.executeQuery();
			if (rsComp.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rsComp, pstComp);
		
			
		}
		return false;
	}

}
