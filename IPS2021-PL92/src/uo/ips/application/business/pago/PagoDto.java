package uo.ips.application.business.pago;

import java.time.LocalDate;


public class PagoDto {

	public LocalDate fechaPago;	
	public double importe;
	public String pagoId;

	//public String dniAtleta;
	public int idAtleta;
	public int idCompeticion;
	public PagoDto() {
		
	}
	public PagoDto(LocalDate fechaPago,  int idAtleta, int idCompeticion) {
		this.fechaPago = fechaPago;		
	    this.idAtleta = idAtleta;
		this.idCompeticion = idCompeticion;
	}
	/**
	 * Para emitir justificantes de pago
	 */
	@Override
	public String toString() {
		return "fecha: " + fechaPago+"\n"+ " importe:" + importe +"$\n"+ " ID pago:" + pagoId +"\n"+  " ID atleta:"
				+ idAtleta +"\n"+  " ID Competición:" + idCompeticion +"\n" ;
	}


	
	
}
