package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class ObtenerClasificaciones {
	



	private String SQL_Select_Terminadas = "SELECT i.* FROM Inscripcion i, Atleta a WHERE i.estado = 'TERMINADA' AND idCompeticion = ? and i.idAtleta = a.idAtleta and a.sexo = ?";
	private String SQL_Select_NOTerminadas = "SELECT i.* FROM Inscripcion i, Atleta a WHERE i.estado <> 'TERMINADA' AND idCompeticion = ? and i.idAtleta = a.idAtleta and a.sexo = ?";
	
	private String SQL_Existe_Comp = "SELECT * FROM Competicion WHERE idCompeticion = ?";

	private String SQL_Competicion_Terminada = "SELECT estado FROM Competicion WHERE idCompeticion = ?";
	private String SQL_Update = "UPDATE Inscripcion set posicionFinal = ? where idAtleta = ? and idCompeticion = ?";
	private int idCompeticion; 
	private String categoria;
	

	private Connection c = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	private List<InscripcionDto> inscripciones;
	
	

	public ObtenerClasificaciones(int idCompeticion, String categoria) throws BusinessException {
		
		
		this.categoria = categoria;
		
		this.inscripciones = new ArrayList<InscripcionDto>();
		this.idCompeticion = idCompeticion;
	}

	public List<InscripcionDto> execute() throws BusinessException {
	

		try {
			
			c = Jdbc.getConnection();
			

			if(!competicionExiste()) {
				throw new BusinessException("La competicion no existe");
			}
			if(!competicionHaTerminado()) {
				throw new BusinessException("La competicion aun no terminó, por lo que no podemos crear la clasificacion.");
			}
			
			
			
	
			pst = c.prepareStatement(SQL_Select_Terminadas);
			
			pst.setInt(1,idCompeticion);
			pst.setString(2,categoria);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				inscripciones.add(new InscripcionDto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
						rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getTime(8)));
				
			}
			
			
			ordenarClasificacion();
			otorgarPuestos();
			
			
			pst = c.prepareStatement(SQL_Select_NOTerminadas);
			
			pst.setInt(1,idCompeticion);
			pst.setString(2,categoria);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				
				inscripciones.add(new InscripcionDto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
						rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getTime(8)));
				
			}
			
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
		
		
		return inscripciones;
	}
	
	
	
	private boolean competicionExiste() throws SQLException {
		

		pst = c.prepareStatement(SQL_Existe_Comp);
		
		pst.setInt(1,idCompeticion);
		
		rs = pst.executeQuery();
		
		return rs.next();
		
		
	}
	
	private boolean competicionHaTerminado() throws SQLException {
		
		pst = c.prepareStatement(SQL_Competicion_Terminada);
		pst.setInt(1,idCompeticion);
		rs = pst.executeQuery();
		
		rs.next();
		String estado = rs.getString(1);
		
		return estado.equals("TERMINADA");
		
	}
	
	
	private void otorgarPuestos() throws SQLException {
		
		pst = c.prepareStatement(SQL_Update);
		int pos = 1;
		for(InscripcionDto ins : inscripciones) {
			
			ins.posicionFinal = pos;
			
			pst.setInt(1,pos);
			pst.setInt(2, ins.idAtleta);
			pst.setInt(3, ins.idCompeticion);
			
			pst.executeUpdate();
			
			pos++;
			
		}
		
	}
	
	
	
	private void ordenarClasificacion() {
		quicksort(0,inscripciones.size()-1);
	}
	
	/**
	 * 
	 * @param left init position
	 * @param right Ultima posicion (de inicio debe ser size()-1)
	 */
	private void quicksort(int left, int right){
		
		
		int i = left;
		int j = right;
		InscripcionDto pivot;
		
		if (left < right){ //if there is one element it is not necessary
			int center = (left + right)/2;
			//if there are less than or equal to 3 elements, there are just ordered

				pivot = inscripciones.get(center); //choose the pivot
				interchange(center, right); //hide the pivot

				do {         
			    	while (inscripciones.get(i).tiempoQueTardaEnSegundos <= pivot.tiempoQueTardaEnSegundos && i < j) i++; //first element > pivot
			    	while (inscripciones.get(j).tiempoQueTardaEnSegundos >= pivot.tiempoQueTardaEnSegundos && j > i) j--; //first element < pivot
			        if (i < j) interchange(i, j);
			    } while (i < j);   //end while
				
				//we set the position of the pivot
				interchange(i, right);
				quicksort(left, i-1);
				quicksort(i+1, right);		

		} //if
		
		
	}
	
	

	private void interchange(int i, int j) {
		InscripcionDto t;
		t = inscripciones.get(i);
		inscripciones.set(i, inscripciones.get(j));
		inscripciones.set(j, t);
	}

}
