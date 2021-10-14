package uo.ips.application.business.atleta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionDto;

public class Main {

	public static void main(String[] args) throws SQLException, BusinessException {
		
		Connection con = Jdbc.getConnection();
		Statement st = con.createStatement();
		
		 boolean valid = con.isValid(50000);
         System.out.println(valid ? "TEST OK" : "TEST FAIL");
         AtletaDto atleta = new AtletaDto();
         atleta.dni = "1234";
         atleta.apellido = "alvarez";
         atleta.email = "email@gmail.com";
         atleta.nombre = "Daniel";
         atleta.fechaNacimiento = new Date(2001,01,29);
         try {
        	 BusinessFactory.forAtletaCrudService().anadirAtleta(atleta);        	 
         }catch (Exception e) {
			// TODO: handle exception
		}
         
         
         CompeticionDto competicion = new CompeticionDto();
         competicion.nombre = "test";
         competicion.fechaCompeticion = new Date(99999);
         competicion.organizador = "testOrganizador";
         BusinessFactory.forCompeticionCrudService().anadirCompeticion(competicion);
	}

}
