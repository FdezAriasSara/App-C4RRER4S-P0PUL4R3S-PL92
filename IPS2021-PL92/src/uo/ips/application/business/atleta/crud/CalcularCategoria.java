package uo.ips.application.business.atleta.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.atleta.AtletaDto;

public class CalcularCategoria {
	private String SQLGetAtleta = "SELECT * FROM Atleta WHERE idAtleta = ?";
	private String SLQGetIdCategoria = "SELECT cat.idCategoria FROM Competicion co INNER JOIN ContieneCategoria cc ON co.idCompeticion = cc.idCompeticion "
			+ "INNER JOIN Categoria cat ON cat.idCategoria = cc.idCategoria "
			+ "WHERE co.idCompeticion = ? AND cat.edadMin < ? AND cat.edadMax > ? AND cat.sexo = ?";

	private int idAtleta;
	private int idCompeticon;

	public CalcularCategoria(int idAtleta, int idCompeticion) {
		this.idAtleta = idAtleta;
		this.idCompeticon = idCompeticion;
	}

	public int excute() {
		AtletaDto atleta;
		int idCategoria = 0;
		int edad;
		try {
			atleta = getAtleta();
			edad = getEdad(atleta);
			idCategoria = getIdCategoria(edad, atleta.sexo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idCategoria;
	}

	/**
	 * Busca el atleta con la id que se le pasa y lo devuelve en forma e dto
	 * 
	 * @return atletaDto con la información del atleta buscado
	 * @throws SQLException
	 */
	private AtletaDto getAtleta() throws SQLException {
		Connection c = null;
		PreparedStatement pst = null;
		AtletaDto atleta = null;

		try {
			c = Jdbc.getConnection();

			pst = c.prepareStatement(SQLGetAtleta);
			pst.setInt(1, idAtleta);

			atleta = DtoAssembler.toAtletaDto(pst.executeQuery());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			pst.close();
			c.close();
		}

		return atleta;
	}
	
	/**
	 * Calcula la edad del atleta que se le pasa
	 * 
	 * @param atleta
	 * @return edad en forma de int
	 */
	private int getEdad(AtletaDto atleta) {
		LocalDate hoy = LocalDate.now();
		LocalDate nacimiento = atleta.fechaNacimiento.toLocalDate();

		long edad = ChronoUnit.YEARS.between(nacimiento, hoy);

		return (int) edad;
	}
	
	/**
	 * 
	 * @param edad
	 * @param sexo
	 * @return
	 */
	private int getIdCategoria(int edad, String sexo) {
		// Process
				Connection c = null;
				PreparedStatement pst = null;
				ResultSet rs = null;
				int id = 0;
				
				try {
					c = Jdbc.getConnection();

					pst = c.prepareStatement(SLQGetIdCategoria);
					
					pst.setInt(1, idCompeticon);
					pst.setInt(2, edad);
					pst.setInt(3, edad);
					pst.setString(4, sexo);
					
					rs = pst.executeQuery();
					
					id = rs.getInt(1);

				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					Jdbc.close(rs, pst, c);
				}
				return id;
	}
}