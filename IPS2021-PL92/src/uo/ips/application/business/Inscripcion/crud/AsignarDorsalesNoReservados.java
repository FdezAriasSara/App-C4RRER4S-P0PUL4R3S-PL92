package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class AsignarDorsalesNoReservados {
	private static final String INSCRIPCIONES_SIN_DORSAL = "SELECT * FROM Inscripcion WHERE idCompeticion=? and estado='INSCRITO' and  isnull(dorsal) ";
	private static final String ASIGNAR_DORSAL="UPDATE Inscripcion SET dorsal=? WHERE idAtleta=?";
	private static final String DORSALES_RESERVADOS= "SELECT dorsalesReservados FROM Competicion WHERE idCompeticion=?";
	private int id;
	private Connection c;

	public AsignarDorsalesNoReservados(int idCompeticion) {
		Argument.isTrue(idCompeticion > 0, "El id de la competición no puede ser negativo");
		this.id = idCompeticion;
	}

	public void execute() throws BusinessException {
		int dorsal=primerDorsalNoReservado();
		List<InscripcionDto> inscripciones = inscripcionesPorId();
		for (InscripcionDto ins : inscripciones) {
			
			asignarDorsal(ins.idAtleta,dorsal);
			dorsal++;
		}

	}

	private int primerDorsalNoReservado() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(DORSALES_RESERVADOS);
			pst.setInt(1, id);
			

			rs = pst.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 0;
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs,pst);

		}
	}

	private void asignarDorsal(int atleta,int dorsal) throws BusinessException {
		PreparedStatement pst = null;
		
		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(ASIGNAR_DORSAL);
			pst.setInt(1, dorsal);
			pst.setInt(2, atleta);

			 pst.executeUpdate();
		
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(pst);
			Jdbc.close(c);

		}

	}

	private List<InscripcionDto> inscripcionesPorId() throws BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(INSCRIPCIONES_SIN_DORSAL);
			pst.setInt(1, id);

			rs = pst.executeQuery();
			return DtoAssembler.toInscripcionConDorsalList(rs);
		} catch (SQLException e) {
			throw new BusinessException(e);
		} finally {

			Jdbc.close(rs,pst);
	
		}
	}

}
