package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;

public class InscribirAtleta {
	
	private String SQL = "INSERT INTO Inscripcion (idCompeticion,idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria,posicionFinal,tiempoQueTarda) VALUES (?,?,?,?,?,?,?,?)";
	private String SQL_Club = "INSERT INTO Inscripcion (idCompeticion,idAtleta,estado,fechaInscripcion,fechaUltimoCambio,idCategoria,posicionFinal,tiempoQueTarda,club) VALUES (?,?,?,?,?,?,?,?,?)";
	private String SQLGetInscripcion = "SELECT * FROM Inscripcion WHERE idCompeticion = ? AND idAtleta = ?";
	private String SQLGetNumeroPlazas = "SELECT plazasDisponibles FROM Competicion WHERE idCompeticion = ?";
	//private String SQLGetNumeroInscritos = "SELECT Count(*) FROM Inscritos WhERE idCompeticion = ?";
	private int  idCompeticion;
	private int idAtleta;
	private int idCategoria;
	private String club = "";
	
	Connection c = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public InscribirAtleta(int idCompeticion, int idAtleta, int idCategoria) {
		
		
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
		this.idCategoria = idCategoria;
		try {
			this.c = Jdbc.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public InscribirAtleta(int idCompeticion, int idAtleta, int idCategoria, Connection c) {
		this.idCompeticion = idCompeticion;
		this.idAtleta = idAtleta;
		this.idCategoria = idCategoria;
		
		this.c = c;
		
	}
	
	public InscribirAtleta(int idCompeticion, int idAtleta, int idCategoria, Connection c, String club) {
		this(idCompeticion, idAtleta, idCategoria,c);
		this.club = club;
	}

	public void execute() throws BusinessException {
		// Process

		try {
			


			if(yaEstaInscrito()) {
				throw new BusinessException("El atleta ya estÑ inscrito en la competiciÑn");				
			}
			if(!haySitio()) {
				throw new BusinessException("La competiciÑn estÑ llena");				
			}
			if(this.club.isBlank()) {
				pst = c.prepareStatement(SQL);
			}else {
				pst = c.prepareStatement(SQL_Club);
			}
			
			pst.setInt(1, idCompeticion);
			pst.setInt(2, idAtleta);
			
			pst.setDate(4, new Date(System.currentTimeMillis()));
			pst.setDate(5, new Date(System.currentTimeMillis()));
			pst.setInt(6, idCategoria);
			pst.setInt(7, -1);
			pst.setString(8, "00:00:00");
			
			if(!this.club.isBlank()) {
				pst.setString(9, this.club);
				pst.setString(3, "INSCRITO");
			}else {
				pst.setString(3, "PRE_INSCRITO");
			}

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
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
