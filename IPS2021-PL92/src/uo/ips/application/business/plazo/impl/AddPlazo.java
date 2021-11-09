package uo.ips.application.business.plazo.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.plazo.PlazoDto;

public class AddPlazo {
	private static final String SQL = "INSERT INTO Plazos (idCompeticion,fechaInicio,fechaFin,cuota) VALUES (?,?,?,?)";
	PlazoDto plazo;

	public AddPlazo(PlazoDto plazo) {
		this.plazo = plazo;
	}

	public void execute() {
		// Process
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(SQL);
			pst.setLong(1, plazo.idCompeticion);
			pst.setDate(2, Date.valueOf(plazo.fechaInicio));
			pst.setDate(3, Date.valueOf(plazo.fechaFin));
			pst.setDouble(4, plazo.cuota);
			pst.executeUpdate();	
			
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}

	}
		
	}


