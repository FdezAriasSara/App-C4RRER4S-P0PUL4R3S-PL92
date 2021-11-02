package uo.ips.application.business.Inscripcion.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.DtoAssembler;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.Inscripcion.InscripcionDto.Estado;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;

public class ObtenerAtletas {
	private String SQL = "SELECT * FROM Inscripcion i, Atleta a WHERE idCompeticion = ? and i.idAtleta = a.idAtleta ORDER BY i.fechaInscripcion,i.estado;";
	private String SQL_Existe_Comp = "SELECT * FROM Competicion WHERE idCompeticion = ?";
	private String SQL_Nombre_Cat = "SELECT * FROM Categoria as c WHERE idCategoria = ?";

	private int idCompeticion;
	private List<AtletaInscritoDto> atletas = new ArrayList<AtletaInscritoDto>();


	private Connection c = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	public ObtenerAtletas(int idCompeticion) {
		this.idCompeticion = idCompeticion;
	}

	public List<AtletaInscritoDto> execute() throws BusinessException {
		String res = "";
		try {

			c = Jdbc.getConnection();

			if (!competicionExiste()) {
				throw new BusinessException("La competicion no existe");
			}

			pst = c.prepareStatement(SQL);

			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			AtletaInscritoDto atleta;
			while (rs.next()) {
				
				atleta = new AtletaInscritoDto();
				atleta.dni = rs.getString("dni");
				atleta.nombre = rs.getString("name");
				atleta.apellido = rs.getString("surname");
				atleta.categoria = nombreCat(rs.getInt("idCategoria"));
				atleta.fechaInscripcion = rs.getDate("fechaInscripcion");
				atleta.estado = Estado.valueOf(rs.getString("estado").toUpperCase());
				atletas.add(atleta);
			}


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

		return atletas;
	}

	private boolean competicionExiste() throws SQLException {

		pst = c.prepareStatement(SQL_Existe_Comp);

		pst.setInt(1, idCompeticion);

		rs = pst.executeQuery();

		return rs.next();

	}
	
	
	private String nombreCat(int idCategoria) throws BusinessException  {
		
		
		try {
			Connection cs = Jdbc.getConnection();
	
			
	
			PreparedStatement pst2 = cs.prepareStatement(SQL_Nombre_Cat);
	
			pst2.setInt(1, idCategoria);
	
			ResultSet rs2 = pst2.executeQuery();
		
			return rs2.getString("nombreCategoria");
		}
		catch(SQLException w) {
			
		}
		
		return null;
		
	}
}
