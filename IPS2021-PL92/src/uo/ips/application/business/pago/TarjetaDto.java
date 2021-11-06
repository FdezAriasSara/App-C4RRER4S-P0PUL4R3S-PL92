package uo.ips.application.business.pago;

import java.time.LocalDate;

public class TarjetaDto {
	public int cvc;
	public String numeroTarjeta;
	public LocalDate fechaCaducidad;
	public int atletaId;
	public TarjetaDto( String numeroTarjeta, LocalDate fechaCaducidad, int cvc,int atletaId) {


		this.cvc = cvc;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaCaducidad = fechaCaducidad;
		this.atletaId = atletaId;
	}
	public TarjetaDto() {


	}
	

}
