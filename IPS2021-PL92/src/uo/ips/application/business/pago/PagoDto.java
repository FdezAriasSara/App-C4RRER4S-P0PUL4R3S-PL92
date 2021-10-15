package uo.ips.application.business.pago;

import java.sql.Date;
import java.time.LocalDate;


public class PagoDto {

	public LocalDate fechaPago;	
	public int importe;
	public String pagoId;

	//public String dniAtleta;
	public String idAtleta;
	public String idCompeticion;
	/**
	 * Para emitir justificantes de pago
	 */
	@Override
	public String toString() {
		return "fecha" + fechaPago+"\n"+ "importe:" + importe +"\n"+ "ID pago:" + pagoId +"\n"+  ", ID atleta:"
				+ idAtleta +"\n"+  "idCompetición:" + idCompeticion +"\n" ;
	}


	
	
}
