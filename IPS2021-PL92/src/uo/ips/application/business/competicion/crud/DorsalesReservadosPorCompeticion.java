package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class DorsalesReservadosPorCompeticion {
	private static final String DORSALES_RESERVADOS= "SELECT dorsalesReservados FROM Competicion WHERE idCompeticion=?";
	private static final String EXISTE = "SELECT * FROM Competicion WHERE idCompeticion=?";
	private int id;
	private Connection c;

	public DorsalesReservadosPorCompeticion(int competicionId) {
		Argument.isTrue(competicionId>0,"El id de la competiciÑn no puede ser negativo");
		this.id=competicionId;
	}

	public int execute() throws BusinessException {
		existeCompeticion(id);
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(DORSALES_RESERVADOS);
			pst.setInt(1, id);
			

			rs = pst.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs,pst);

		
	}
	}

	private boolean existeCompeticion(int id) throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(EXISTE);
			pst.setInt(1, id);
			

			rs = pst.executeQuery();
			if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs,pst);

		
	}
		
	}

}
