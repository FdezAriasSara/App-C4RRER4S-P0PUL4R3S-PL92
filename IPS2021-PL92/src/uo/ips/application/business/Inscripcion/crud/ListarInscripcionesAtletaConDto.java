package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.Estado;

public class ListarInscripcionesAtletaConDto {
	
	private final String SQL_Select_DatosCompIns = "select c.Nombre, i.estado, i.fechaUltimoCambio from Inscripcion i, Competicion c where ? = i.idAtleta and i.idCompeticion = c.idCompeticion";
	
	private int idAtleta;
	private Connection c;
	
	
	public ListarInscripcionesAtletaConDto(int idAtleta) {
		Argument.isTrue(idAtleta > 0);
		this.idAtleta = idAtleta;
	}
	
	
	public List<AtletaInscritoDto> execute() throws BusinessException{
		
		List<AtletaInscritoDto> inscripciones = new ArrayList<AtletaInscritoDto>();
		ResultSet rsInscripciones = null;
		
		PreparedStatement pstInscripciones = null;
	

		try {
			c = Jdbc.getConnection();
			
			pstInscripciones = c.prepareStatement(SQL_Select_DatosCompIns);

			// Busamos las inscripciones asociadas al id.
			pstInscripciones.setInt(1, idAtleta);
			rsInscripciones = pstInscripciones.executeQuery();
			
			AtletaInscritoDto atl;
			while(rsInscripciones.next()) {
				atl = new AtletaInscritoDto();
				atl.nombreCompeticion = rsInscripciones.getString("nombre");
				atl.fechaUltimoCambio = rsInscripciones.getDate("fechaUltimoCambio");
				String state = rsInscripciones.getString("estado");
				
				if(state.toUpperCase().equals("PENDIENTE DE PAGO")) {
					atl.estado = Estado.PENDIENTE_DE_PAGO;
				}else {
					atl.estado = Estado.valueOf(state);
				}
				
				inscripciones.add(atl);
			}
			
		}catch(SQLException w) {
			throw new BusinessException(w.getMessage());
		}
		
		
		
		return inscripciones;
		
	}

}
