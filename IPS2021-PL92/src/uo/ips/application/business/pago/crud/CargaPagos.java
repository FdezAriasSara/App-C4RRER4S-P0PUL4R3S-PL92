package uo.ips.application.business.pago.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import uo.ips.application.business.pago.TransferenciaDto;

public class CargaPagos {
	private final String RUTA_ARCHIVO = "pagos.txt";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

	public List<TransferenciaDto> read() throws IOException {
		File file = new File(RUTA_ARCHIVO);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		String[] line;
		List<TransferenciaDto> datosPagos = new ArrayList<>();

		while ((st = br.readLine()) != null) {
			line = st.split("\t");
			datosPagos.add(crearDatos(line));
		}

		br.close();

		return datosPagos;
	}

	private TransferenciaDto crearDatos(String[] line) {
		TransferenciaDto datoPago = new TransferenciaDto();

		datoPago.dni = line[0];
		datoPago.importe = Double.parseDouble(line[1]);
		datoPago.fecha = LocalDate.parse(line[2], formatter);

		return datoPago;
	}
}
