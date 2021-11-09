package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class AsignarDorsalReservado {
	private String email;
	private int dorsal;
	private static final String ASIGNAR_DORSAL="UPDATE Inscripcion SET dorsal=? WHERE idAtleta=? and idCompeticion=? ";
	private static final String BUSCAR_ID_ATLETA="SELECT idAtleta FROM Atleta WHERE email=?";
	private Connection c;
	private int idCompeticion;
	public AsignarDorsalReservado(String email, int dorsal,int idCompeticion) {
		Argument.isNotEmpty(email,"El email no puede estar vacío");
		Argument.isNotNull(email,"El email no puede ser nulo");
		Argument.isTrue(dorsal>0,"El dorsal no puede ser negativo");
		Argument.isTrue(idCompeticion>0,"El id de la competición no puede ser negativo");
		this.email=email;
		this.dorsal=dorsal;
		this.idCompeticion=idCompeticion;
	}

	public void execute() throws BusinessException {
		int idAtleta=buscarIdAtletaPorEmail();
		PreparedStatement pst = null;
		

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(ASIGNAR_DORSAL);
			pst.setInt(1, dorsal);
			pst.setInt(2,idAtleta);
			pst.setInt(3,idCompeticion);
		
			pst.executeUpdate();
			
			

		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(pst);
			Jdbc.close(c);

		}
		
		
	}

	private int buscarIdAtletaPorEmail() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(BUSCAR_ID_ATLETA);
			pst.setString(1,email);
			

			rs = pst.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}else {
				throw new BusinessException(String.format("No se ha encontrado el atleta con email:%s", email));
			}

		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs,pst);

		}
		
	}

}
