package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.toedter.calendar.IDateEditor;

import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.arco.ArcoDto;



/**
 * 
 * Formato nombre archivo ==> Comp-<idCompeticion>.txt
 * Un atleta en cada linea con formato ==> <dorsal>;<tiempoInicio>;<timepoFinal>
 * @author usuario
 *
 */



public class ParseadorTiempos {
	
	
	private static int repeatedInLastExe;
	private static int wrongParseInLastExe;
	
	
	public static List<InscripcionDto> parse(String fileName) throws IOException, FileNotFoundException{
		
		repeatedInLastExe = 0;
		wrongParseInLastExe = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		List<InscripcionDto> inscripciones = new ArrayList<InscripcionDto>();
		Set<Integer> dorsales = new HashSet<Integer>();
		
		InscripcionDto dto;
		String[] elems;
		Time[] times;
		int dorsal;
		String initTime;
		String finalTime;
		Time initialTime;
		Time endTime;
		int idCompeticion;
		int numeroDeArcos;
		try {
		   
		    String line = br.readLine();
		    idCompeticion = Integer.parseInt(line);
		    numeroDeArcos = BusinessFactory.forArco().getArcos(idCompeticion).size();
		    line = br.readLine();

		    while (line != null) {
		    	
		    	 elems = line.split(";");
		    	 times = new Time[numeroDeArcos+2];
		    	 
		    	 
		    	 if(isValid(elems,idCompeticion)) {
		    		 
		    		 dorsal = Integer.parseInt(elems[0]);
		    		 initTime = elems[1];
		    		 finalTime = elems[elems.length-1];
		    		 
		    		 for (int i = 1; i < elems.length; i++) {
		    			 if(elems[i].toUpperCase().equals("DNS") || elems[i].toUpperCase().equals("DNF"))
		    				 times[i-1] = null;
		    			 else {
		    				 times[i-1] = Time.valueOf(elems[i]);
		    				 
		    			 }
		    			 
					}
		    		
		    		
		    		if(dorsales.contains(dorsal)) {
		    			repeatedInLastExe = getRepeatedInLastExe() + 1;
		    		}
		    		else {
		    			dorsales.add(dorsal);
		    			
		    			dto = new InscripcionDto();
			    		dto.tiempoQueTarda = calcularDiferencia(times[times.length-1], times[0]);
		    			dto.dorsal = dorsal;
		    			dto.idCompeticion = idCompeticion;
		    			dto.tiempoInicio = times[0];
		    			dto.tiempoFinal = times[times.length-1];
		    			dto.tiempos = times;
		    			inscripciones.add(dto);
		    		}
		    		
		    		
		    	 }
		    	 
		        
		        
		        line = br.readLine();
		    }
		    
		} finally {
		    br.close();
		}
		
		return inscripciones;
		
	}
	
	
	private static boolean isValid(String[] elems,int idCompeticion) {
		List<ArcoDto> arcos = BusinessFactory.forArco().getArcos(idCompeticion);
		if(elems.length != arcos.size()+3) {
   		 wrongParseInLastExe = getWrongParseInLastExe() + 1;
   		 return false;
	   	 }
		for (String string : elems) {
			if(string.isBlank()) {
				wrongParseInLastExe = getWrongParseInLastExe() + 1;
				return false;
				
			}
		}
	   	 
		try {
			Integer.parseInt(elems[0]);
		}catch(NumberFormatException e) {
			wrongParseInLastExe = getWrongParseInLastExe() + 1;
			return false;
		}
		for (int i = 1; i < elems.length; i++) {
			if(!canParse(elems[i]))
				return false;			
		}
		return true;
	}
	
	
	private static boolean canParse(String time) {
		
		if(time.toUpperCase().equals("DNF")|| time.toUpperCase().equals("DNS")) {
			return true;
		}else{
			try {
				Time.valueOf(time);
				return true;
			}catch(IllegalArgumentException e) {
				wrongParseInLastExe = getWrongParseInLastExe() + 1;
				return false;
			}
			
		}
		
	}
	

	private static Time calcularDiferencia(Time endTime, Time initTime) {
		
		if(endTime == null || initTime == null) {
			
			return Time.valueOf("00:00:00");
			
		}
		
		long endMillis = endTime.getTime();
		long initMillis = initTime.getTime();
		
		return new Time(endMillis - initMillis);
		
	}


	public static int getRepeatedInLastExe() {
		return repeatedInLastExe;
	}


	public static int getWrongParseInLastExe() {
		return wrongParseInLastExe;
	}

}
