package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class RegistrarTiempos {
	
	private String SQL_RegsitrarTiempos = "UPDATE Inscripcion set estado = 'TERMINADA' , tiempoQueTarda = ? where dorsal = ? and idCompeticion = ?";
	private String SQL_INSERT_ARCOTIEMPO = "INSERT INTO ArcoTiempo (idArco,tiempo,idCompeticion,dorsal) VALUES (?,?,?,?)";
	private List<InscripcionDto> inscripciones;
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	
	public RegistrarTiempos(List<InscripcionDto> inscripciones){
		Argument.isNotNull(inscripciones);
		this.inscripciones = inscripciones;
	}
	
	public int execute() throws BusinessException{
		
		int counterNotUpdate = 0;

		try {
			c = Jdbc.getConnection();



			pst = c.prepareStatement(SQL_RegsitrarTiempos);
			
			for(InscripcionDto dto : inscripciones) {
				try {
					pst.setTime(1, dto.tiempoQueTarda);
					pst.setInt(2, dto.dorsal);
					pst.setInt(3, dto.idCompeticion);
					pst.executeUpdate();
					
					for (int i = 1; i < dto.tiempos.length-1; i++) {
						PreparedStatement apst = c.prepareStatement(SQL_INSERT_ARCOTIEMPO);
						apst.setInt(1,i);
						apst.setTime(2,dto.tiempos[i] );
						apst.setInt(3, dto.idCompeticion);
						apst.setInt(4,dto.dorsal);
						apst.executeUpdate();
						
					}
				}catch(SQLException e) {
					counterNotUpdate++;
				}
				
			}
			

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		
		return counterNotUpdate;
		
	}

}
