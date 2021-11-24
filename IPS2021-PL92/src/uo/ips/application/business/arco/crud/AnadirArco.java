package uo.ips.application.business.arco.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.arco.ArcoDto;

public class AnadirArco {
	
	private String SQL = "INSERT INTO Arco(nombre,idCompeticion,km) VALUES (?,?,?)";

	
	ArcoDto arco ;

	public AnadirArco(ArcoDto arco) {
		this.arco = arco;
	}

	public void execute() {
		// Process
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		

		try {
			c = Jdbc.getConnection();
			pst = c.prepareStatement(SQL);
			pst.setString(1, arco.name);
			pst.setInt(2, arco.idCompeticion);
			pst.setDouble(3, arco.km);
			pst.executeUpdate();	
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
			finally {
				Jdbc.close(rs, pst, c);
			}			
		
	}

}
