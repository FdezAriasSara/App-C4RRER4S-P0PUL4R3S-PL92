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

import uo.ips.application.business.Inscripcion.InscripcionDto;

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
		int dorsal;
		String initTime;
		String finalTime;
		Time initialTime;
		Time endTime;
		try {
		   
		    String line = br.readLine();

		    while (line != null) {
		    	
		    	 elems = line.split(";");
		    	 
		    	 if(isValid(elems)) {
		    		 
		    		 dorsal = Integer.parseInt(elems[0]);
		    		 initTime = elems[1];
		    		 finalTime = elems[2];
		    		 
		    		if(initTime.toUpperCase().equals("DNS")) {
		    			initialTime = null;
		    			
		    		}else {
		    			initialTime = Time.valueOf(elems[1]);
		    			
		    		}
		    		
		    		if(initTime.toUpperCase().equals("DNS") || finalTime.toUpperCase().equals("DNF")) {
	    				endTime = null;
	    			}
	    			else {
	    				endTime = Time.valueOf(elems[2]);
	    			}
		    		
		    		
		    		
		    		if(dorsales.contains(dorsal)) {
		    			repeatedInLastExe = getRepeatedInLastExe() + 1;
		    		}
		    		else {
		    			dorsales.add(dorsal);
		    			
		    			dto = new InscripcionDto();
			    		dto.tiempoQueTarda = calcularDiferencia(endTime, initialTime);
		    			dto.dorsal = dorsal;
		    			dto.idCompeticion = Integer.parseInt(fileName.split("\\.")[0].split("-")[1]);
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
	
	
	private static boolean isValid(String[] elems) {
		
		if(elems.length != 3) {
   		 wrongParseInLastExe = getWrongParseInLastExe() + 1;
   		 return false;
	   	 }
	   	 else if(elems[0].isBlank() || elems[1].isBlank() || elems[2].isBlank()){
	   		 wrongParseInLastExe = getWrongParseInLastExe() + 1;
	   		 return false;
	   	 }
	   	 
		try {
			Integer.parseInt(elems[0]);
		}catch(NumberFormatException e) {
			wrongParseInLastExe = getWrongParseInLastExe() + 1;
			return false;
		}
		
		
		
		return canParse(elems[1],"DNS") && canParse(elems[2],"DNF");
	}
	
	
	private static boolean canParse(String time, String status) {
		
		if(time.toUpperCase().equals(status)) {
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
