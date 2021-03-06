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
	// private String REALIZAR_PAGO = "INSERT INTO Pago
	// (idAtleta,idPago,fechaPago,importe) VALUES (?,?,?,?)";
	private String SQL_CAMBIAR_ESTADO_INSCRIPCION = "UPDATE Inscripcion SET estado='PENDIENTE DE PAGO' WHERE idAtleta=? and idCompeticion=?";
	private String SQL_BUSCAR_DATOS_TRANSFERENCIA = "SELECT * from Competicion where idCompeticion = ?";
	private String SQL_BUSCAR_IMPORTE_PLAZO = "select Plazos.cuota from Plazos where idCompeticion = ? and NOW() >= fechaInicio and NOW() <= fechaFin";
	private int idAtleta;
	private int idCompeticon;

	public PagarConTransferencia(int idAtleta, int idCompeticion) {
		this.idAtleta = idAtleta;
		this.idCompeticon = idCompeticion;
	}

	public CompeticionDto execute() throws BusinessException {
		cambiarEstadoInscripcion();
		return datos_Transferencia();
	}

	private CompeticionDto datos_Transferencia() throws BusinessException {
		Connection c = null;
		PreparedStatement pst = null;
		CompeticionDto competicion = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_BUSCAR_DATOS_TRANSFERENCIA);
			pst.setInt(1, idCompeticon);

			competicion = DtoAssembler.toCompeticion(pst.executeQuery()).get();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
			Jdbc.close(c);
		}

		competicion.cuota = getImporte();

		return competicion;
	}

	private double getImporte() throws BusinessException {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		double importe = 0.0;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_BUSCAR_IMPORTE_PLAZO);

			pst.setInt(1, idCompeticon);

			rs = pst.executeQuery();

			if (rs.next()) {
				importe = rs.getDouble(1);
			} else {
				throw new BusinessException("Competicion sin plazos de inscripcion abiertos");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return importe;
	}

	private void cambiarEstadoInscripcion() throws BusinessException {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL_CAMBIAR_ESTADO_INSCRIPCION);

			pst.setInt(2, idCompeticon);

			pst.setInt(1, idAtleta);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}
}
