package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.assertion.Argument;
import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public class AnadirCompeticion {

	private String SQL = "INSERT INTO Competicion (nombre,fechaCompeticion,organizador,"
			+ " tipoCompeticion, distanciaKm, plazasDisponibles,estado,cuentaBancaria,dorsalesReservados,limiteCancelacion,aDevolver)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	
	private String SQL_lista = "INSERT INTO Competicion (nombre,fechaCompeticion,organizador,"
			+ " tipoCompeticion, distanciaKm, plazasDisponibles,estado,cuentaBancaria,dorsalesReservados,limiteCancelacion,aDevolver,tieneListaEspera)"
			+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private String SQLGetCompeticion = "SELECT * FROM Competicion WHERE idCompeticion = ?";
	private CompeticionDto competicion;

	public AnadirCompeticion(CompeticionDto competicion) {
		Argument.isNotNull(competicion);
		this.competicion = competicion;
	}

	public CompeticionDto execute() throws BusinessException {
		// Process
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getConnection();

//		pst = c.prepareStatement(SQLGetCompeticion);
//		pst.setInt(1, competicion.idCompeticion);
//		
//		rs = pst.executeQuery();
//		
//		while(rs.next()) {
//			throw new BusinessException("Ya existe la competiciÑn");
//		}
//		pst.close();
			pst = c.prepareStatement(SQL_lista, pst.RETURN_GENERATED_KEYS);
			pst.setString(1, competicion.nombre);
			pst.setDate(2, competicion.fechaCompeticion);
			pst.setString(3, competicion.organizador);
			pst.setString(4, competicion.tipoCompeticion);
			pst.setInt(5, competicion.distanciaKm);
			pst.setInt(6, competicion.plazasDisponibles);
			pst.setString(7, "PENDIENTE");
			pst.setString(8, competicion.cuentaBancaria);
			pst.setInt(9, competicion.dorsalesReservados);
			pst.setDate(10, competicion.limiteCancelacion);
			pst.setInt(11, competicion.aDevolver);
			pst.setBoolean(12, competicion.hasListaDeEspera);

			pst.executeUpdate();

			ResultSet id = pst.getGeneratedKeys();
			if (id.next()) {
				competicion.idCompeticion = id.getInt(1);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
		return competicion;
	}

}
