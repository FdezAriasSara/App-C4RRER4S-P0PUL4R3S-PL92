package uo.ips.application.business.pago.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ips.application.business.pago.ContabilidadDto;

public class Contabilidad {
	private String SQL_INGRESOS = "select importe_pago from Inscripcion where idCompeticion = ? and importe_pago > 0";
	private String SQL_GASTOS = "select importe_devolver from Inscripcion where idCompeticion = ? and importe_devolver > 0";

	private int idCompeticion;
	private Connection c = null;

	public Contabilidad(int idCompeticion) {
		this.idCompeticion = idCompeticion;
		try {
			c = Jdbc.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ContabilidadDto> execute() {
		List<ContabilidadDto> contabilidades = new ArrayList<>();

		ContabilidadDto ingresos = creaDto(getIngresos());
		ingresos.devolver = false;
		contabilidades.add(ingresos);

		ContabilidadDto devoluciones = creaDto(getDevoluciones());
		devoluciones.devolver = true;
		contabilidades.add(devoluciones);

		Jdbc.close(c);
		return contabilidades;
	}

	private List<Double> getIngresos() {
		PreparedStatement pst = null;
		List<Double> ingresos = new ArrayList<>();
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_INGRESOS);
			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			while (rs.next()) {
				ingresos.add(rs.getDouble(1));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return ingresos;
	}

	private List<Double> getDevoluciones() {
		PreparedStatement pst = null;
		List<Double> gastos = new ArrayList<>();
		ResultSet rs = null;

		try {

			pst = c.prepareStatement(SQL_GASTOS);
			pst.setInt(1, idCompeticion);

			rs = pst.executeQuery();

			while (rs.next()) {
				gastos.add(rs.getDouble(1));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}

		return gastos;
	}

	private ContabilidadDto creaDto(List<Double> dinero) {
		ContabilidadDto dto = new ContabilidadDto();
		dto.cantidad = dinero.size();
		dto.importe = sumar(dinero);

		return dto;
	}

	private double sumar(List<Double> numeros) {
		double suma = 0;

		for (Double numero : numeros) {
			suma += numero;
		}

		return suma;
	}
}
