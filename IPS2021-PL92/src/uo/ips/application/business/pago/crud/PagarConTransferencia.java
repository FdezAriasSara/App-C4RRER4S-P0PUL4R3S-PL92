package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.competicion.CompeticionDto;

public class PagarConTransferencia {
	//private String REALIZAR_PAGO = "INSERT INTO Pago (idAtleta,idPago,fechaPago,importe) VALUES (?,?,?,?)";
	private String SQL_CAMBIAR_ESTADO_INSCRIPCION = "UPDATE Inscripción WHERE idAtleta=? and idInscripcion=? SET estado='PENDIENTE DE PAGO'";
	private String  SQL_ENCONTRAR_ID_INSCRIPCION = "SELECT idInscripcion WHERE idAtleta=? and idCompetición=?";
	private String  SQL_BUSCAR_DATOS_TRANSFERENCIA = "SELECT cuentaBancaria, cuota from Competicion where idCompeticion = ?";
	private int idAtleta;
	private int idCompeticon;
	
	public PagarConTransferencia(int idAtleta, int idCompeticion) {
		this.idAtleta = idAtleta;
		this.idCompeticon = idCompeticion;
	}
	
	public CompeticionDto execute() throws SQLException, BusinessException {
		cambiarEstadoInscripcion();
		return datos_Transferencia();
	}
	
	private CompeticionDto datos_Transferencia() throws BusinessException {
		Connection c = null;
		PreparedStatement pst = null;
		CompeticionDto competicion = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement( SQL_BUSCAR_DATOS_TRANSFERENCIA);
			pst.setInt(1, idCompeticon);

			competicion = DtoAssembler.toCompeticionDto(pst.executeQuery());
			

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}

		return competicion;
	}
	
	private void cambiarEstadoInscripcion() throws BusinessException {
		Connection c = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement( SQL_ENCONTRAR_ID_INSCRIPCION);
			pst2 = c.prepareStatement( SQL_CAMBIAR_ESTADO_INSCRIPCION);
			
			pst.setInt(1, idCompeticon);
			pst.setInt(2, idAtleta);
			pst.setInt(3, idCompeticon);
			
			rs = pst.executeQuery();
			
			pst2.setInt(1, idCompeticon);
			
			if(rs.next()) {
				pst2.setInt(2, rs.getInt(1));		
				pst2.executeUpdate();
			} else {
				throw new BusinessException("Todavía no estás inscrito");
			}
			
				
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
			Jdbc.close(pst2);
		}
	}
}
