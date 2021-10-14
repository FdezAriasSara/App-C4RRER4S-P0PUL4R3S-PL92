package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class AnadirInscripcion {
	private String SQL = "INSERT INTO Inscripcion (idCompeticion,idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria) VALUES ,(?,?,?,?,?,?,?)";
	private String SQLGetInscripcion = "SELECT * FROM Inscripcion WHERE idCompeticion = ? AND idAtleta = ?";
	private String SQLGetCompeticionPlazas = "SELECT nPlazas FROM Competicion WHERE idCompeticion = ?";
	private String SQLGetNumeroInscritos = "SELECT Count(*) FROM Inscritos WhERE idCompeticion = ?";

	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	InscripcionDto inscripcion ;

	public AnadirInscripcion(InscripcionDto inscripcion) {
		this.inscripcion =inscripcion;
	}

	public void execute() throws BusinessException {
		// Process

		try {
			c = Jdbc.getConnection();


			if(yaEstaInscrito()) {
				throw new BusinessException("Ya existe la competición");				
			}
			if(!haySitio()) {
				throw new BusinessException("La competicion está llena");
			}

			pst = c.prepareStatement(SQL);
			pst.setInt(1, inscripcion.idCompeticion);
			pst.setInt(2, inscripcion.idAtleta);
			pst.setString(3, "Pre-inscrito");
			pst.setDate(4, new Date(System.currentTimeMillis()));
			pst.setDate(5, new Date(System.currentTimeMillis()));
			pst.setInt(6, inscripcion.idCategoria);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}

	private boolean yaEstaInscrito() throws SQLException {
		pst = c.prepareStatement(SQLGetInscripcion);
		pst.setInt(1, inscripcion.idCompeticion);
		pst.setInt(2, inscripcion.idAtleta);
		rs = pst.executeQuery();
		boolean inscrito = rs.next();
		Jdbc.close(rs,pst);
		return inscrito;
	}
	
	private boolean haySitio() throws SQLException {
		pst = c.prepareStatement(SQLGetCompeticionPlazas);
		pst.setInt(1, inscripcion.idCompeticion);
		rs = pst.executeQuery();
		rs.next();
		int nplazas = rs.getInt(1);
		Jdbc.close(rs,pst);
		
		pst = c.prepareStatement(SQLGetNumeroInscritos);
		pst.setInt(1, inscripcion.idCompeticion);
		rs = pst.executeQuery();
		rs.next();
		int nInscritos = rs.getInt(1);
		Jdbc.close(rs,pst);
		
		boolean haySitio = nInscritos < nplazas;
		return  rs.next();
	}
}
