package uo.ips.application.business;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.Inscripcion.PlazoDto;
import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.competicion.CompeticionDto;

public class DtoAssembler {
	public static AtletaDto toAtletaDto(ResultSet m) throws SQLException, BusinessException {
		AtletaDto dto = new AtletaDto();
		if (m.next()) {
			dto.idAtleta = m.getInt("idAtleta");
			dto.dni = m.getString("DNI");
			dto.nombre = m.getString("Name");
			dto.apellido = m.getString("Surname");
			dto.email = m.getString("Email");
			dto.fechaNacimiento = m.getDate("fechaNacimiento");
			dto.sexo = m.getString("sexo");
		} else {
			throw new BusinessException("El atleta no está registrado");

		}

		return dto;
	}

	public static Optional<CompeticionDto> toCompeticion(ResultSet rs) throws SQLException {
		CompeticionDto dto = null;

		if (rs.next()) {
			dto = toCompeticionDto(rs);
		}
		return Optional.ofNullable(dto);
	}

	private static CompeticionDto toCompeticionDto(ResultSet rs) throws SQLException {
		CompeticionDto com = new CompeticionDto();

		com.idCompeticion = rs.getInt("idCompeticion");
		com.nombre = rs.getString("nombre");
		com.fechaCompeticion = rs.getDate("fechaCompeticion");
		com.organizador = rs.getString("organizador");
		com.tipoCompeticion = rs.getString("tipoCompeticion");
		com.distanciaKm = rs.getInt("distanciaKm");
		com.plazasDisponibles = rs.getInt("plazasDisponibles");
		com.cuentaBancaria = rs.getString("cuentaBancaria");

		return com;
	}

	public static Optional<InscripcionDto> toInscripcion(ResultSet rs) throws SQLException {
		InscripcionDto dto = null;

		if (rs.next()) {
			dto = toInscripcionDto(rs);
		}
		return Optional.ofNullable(dto);
	}

	public static Optional<InscripcionDto> toInscripcionConDorsal(ResultSet rs) throws SQLException {
		InscripcionDto dto = null;

		if (rs.next()) {
			dto = toInscripcionDtoConDorsal(rs);
		}
		return Optional.ofNullable(dto);
	}

	private static InscripcionDto toInscripcionDtoConDorsal(ResultSet rs) throws SQLException {
		InscripcionDto com = new InscripcionDto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
				rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getTime(8), rs.getInt(9));

		return com;
	}

	private static InscripcionDto toInscripcionDto(ResultSet rs) throws SQLException {
		InscripcionDto com = new InscripcionDto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
				rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getTime(8));

		return com;
	}
	public static List<InscripcionDto> toInscripcionConDorsalList(ResultSet rs) throws SQLException {
		List<InscripcionDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add( toInscripcionDtoConDorsal(rs));
		}

		return res;
	}

	public static List<InscripcionDto> toInscripcionDtoList(ResultSet rs) throws SQLException {
		List<InscripcionDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(toInscripcionDto(rs));
		}

		return res;
	}

	public static Optional<PlazoDto> toPlazo(ResultSet rs) throws SQLException {
		PlazoDto dto = null;

		if (rs.next()) {
			dto = toPlazoDto(rs);
		}
		return Optional.ofNullable(dto);
	}

	private static PlazoDto toPlazoDto(ResultSet rs) throws SQLException {
		PlazoDto com = new PlazoDto();
		com.idCompeticion = rs.getInt("idCompeticion");

		Date sqlDate = rs.getDate("fechaInicio");
		com.fechaInicio = sqlDate.toLocalDate();
		sqlDate = rs.getDate("fechaFin");
		com.fechaFin = sqlDate.toLocalDate();
		com.cuota = rs.getDouble("cuota");
		com.idPlazo = rs.getInt("idPlazo");

		return com;
	}
}