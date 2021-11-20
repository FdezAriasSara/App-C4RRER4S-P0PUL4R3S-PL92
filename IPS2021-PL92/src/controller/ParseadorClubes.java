package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ips.application.business.atleta.AtletaDto;

public class ParseadorClubes {
	
	public static String nombreClub;
	public static int repetidos;
	public static int formatoErroneo;

	// Nombre;Apellidos;Email;sexo;fecha nac;dni
	public static List<AtletaDto> parse(File file) throws IOException {

		repetidos = 0;
		nombreClub = null;
		formatoErroneo
		 = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
		List<AtletaDto> atletas = new ArrayList<AtletaDto>();
		List<String> dnis = new ArrayList<String>();

		AtletaDto dto;
		String[] elems;

		String nombre;
		String apellido;
		String email;
		String sexo;
		Date fechaNat;
		String dni;

		try {

			String line = br.readLine();

			int linecount = 0;

			while (line != null) {

				if (linecount == 0) {
					nombreClub = line;
					linecount++;
				} else {

					elems = line.split(";");

					if (isValid(elems)) {

						nombre = elems[0];
						apellido = elems[1];
						email = elems[2];
						sexo = elems[3];
						fechaNat = parsearFecha(elems[4]);
						dni = elems[5];
						
						if(!dnis.contains(dni)) {
							dto = new AtletaDto();
							
							dto.nombre = nombre;
							dto.apellido = apellido;
							dto.email = email;
							dto.sexo = sexo;
							dto.fechaNacimiento = fechaNat;
							dto.dni = dni;
							dto.club = nombreClub;
							
							atletas.add(dto);
						}else {
							repetidos++;
						}
						
					

					}

				}

				line = br.readLine();

			}
			
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			br.close();
		}
		

		return atletas;

	}
	
	private static  Date parsearFecha(String fecha) {
		
		String[] dates = fecha.split("-");
		
		Date date = Date.valueOf(LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0])));
		
		return date ;
		
	}
	
	


	private static boolean isValid(String[] elems) {
		if(elems.length != 6) {
			formatoErroneo ++;
			return false;
		}
		if(!canParseNombre(elems[0]) || !canParseSurname(elems[1]) || !canParseEmail(elems[2])
				|| !canParseSexo(elems[3]) || !canParseFecha(elems[4]) || !canParseDni(elems[5])) {
			formatoErroneo++;
			return false;
		}
			
		
		
		return true;
		
	}

	private static boolean canParseNombre(String nombre) {

		return !nombre.trim().isBlank();

	}

	private static boolean canParseSurname(String apellido) {
		return !apellido.trim().isBlank();
	}

	private static boolean canParseEmail(String email) {
		
		if(email.isBlank())return false;

		String[] elems = email.split("@");
		if (elems.length != 2) {
			return false;
		} else {
			String[] com = elems[1].split("[.]");
			if (com.length < 2) {
				return false;
			}
		}

		return true;
	}

	private static boolean canParseSexo(String sexo) {
		if(sexo.isBlank())return false;
		return sexo.equals("femenino") || sexo.equals("masculino");
	}

	private static boolean canParseFecha(String fecha) {
		if(fecha.isBlank())return false;

		String[] elems = fecha.split("-");
		if (elems.length != 3)
			return false;

		try {

			Integer.parseInt(elems[0]);
			Integer.parseInt(elems[1]);
			Integer.parseInt(elems[2]);

		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	
	private static boolean canParseDni(String dni) {
		if(dni.isBlank())return false;
		
		return true;
		
	}

}
