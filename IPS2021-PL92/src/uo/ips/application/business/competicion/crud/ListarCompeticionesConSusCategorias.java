package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionCategoriaDto;

public class ListarCompeticionesConSusCategorias {
	
	private int idCompeticon;
	
	private String SQL_Select_Categorias = "select c.idCategoria, cc.idCompeticion, c.nombreCategoria  from ContieneCategoria cc , Categoria c where cc.idCategoria = c.idCategoria and cc.idCompeticion = ?";
	 
	public ListarCompeticionesConSusCategorias(int idCompeticion) {
		
		if(idCompeticion < 0) {
			throw new IllegalArgumentException("Id de competicion no puede ser negativo ni cero: " + idCompeticion);
		}
		
		this.idCompeticon = idCompeticion;
	}
	
	public List<CompeticionCategoriaDto> execute() throws BusinessException{
		
		List<CompeticionCategoriaDto> dtos = new ArrayList<CompeticionCategoriaDto>();
		CompeticionCategoriaDto dto;
		
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL_Select_Categorias);
			pst.setInt(1, idCompeticon);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				dto = new CompeticionCategoriaDto();
				dto.idCategoria = rs.getInt("idCategoria");
				dto.idCompeticion = rs.getInt("idCompeticion");
				dto.nombreCategoría = rs.getString("nombreCategoria");
				dtos.add(dto);
				
			}
			
			
		} catch (SQLException e) {
			throw new BusinessException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
		return dtos;
		
		
	}

}
