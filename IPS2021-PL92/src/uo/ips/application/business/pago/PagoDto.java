package uo.ips.application.business.pago;

import java.sql.Date;


public class PagoDto {

	public Date fechaPago;	
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
