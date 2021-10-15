package uo.ips.application.business.competicion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public class InscribirAtleta {
	private String SQL = "INSERT INTO Inscripcion (idCompeticion,idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria,posicionFinal,tiempoQueTarda) VALUES (?,?,?,?,?,?,?,?)";
	private String SQLGetInscripcion = "SELECT * FROM Inscripcion WHERE idCompeticion = ? AND idAtleta = ?";
	private String SQLGetNumeroPlazas = "SELECT plazasDisponibles FROM Competicion WHERE idCompeticion = ?";
	private String SQLGetNumeroInscritos = "SELECT Count(*) FROM Inscritos WhERE idCompeticion = ?";
	private int  idCompeticion;
	private int idAtleta;
	private int idCategoria;
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public InscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) {
		
		
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
		this.idCategoria = idCategoria;
	}

	public void execute() throws BusinessException {
		// Process

		try {
			c = Jdbc.getConnection();


			if(yaEstaInscrito()) {
				throw new BusinessException("El atleta ya está inscrito en la competición");				
			}
			if(!haySitio()) {
				throw new BusinessException("La competición está llena");				
			}

			pst = c.prepareStatement(SQL);
			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);
			pst.setString(3, "PRE_INSCRITO");
			pst.setDate(4, new Date(System.currentTimeMillis()));
			pst.setDate(5, new Date(System.currentTimeMillis()));
			pst.setInt(6, idCategoria);
			pst.setInt(7, -1);
			pst.setString(8, "00:00:00");

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}
	}

	private boolean yaEstaInscrito() throws SQLException {
		pst = c.prepareStatement(SQLGetInscripcion);
		pst.setInt(1, idCompeticion);
		pst.setInt(2, idAtleta);
		rs = pst.executeQuery();
		boolean inscrito = rs.next();
		Jdbc.close(rs,pst);
		return inscrito;
	}
	
	private boolean haySitio() throws SQLException {
		
		pst = c.prepareStatement(SQLGetNumeroPlazas);
		pst.setInt(1, idCompeticion);
		rs = pst.executeQuery();
		rs.next();
		int nPlazasDisponibles = rs.getInt(1);
		Jdbc.close(rs,pst);
		
		return nPlazasDisponibles >0;
	}

}
