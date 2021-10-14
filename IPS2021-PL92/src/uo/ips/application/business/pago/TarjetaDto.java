package uo.ips.application.business.pago;

import java.sql.Date;

public class TarjetaDto {
	public int cvc;
	public String numeroTarjeta;
	public Date fechaCaducidad;
	public String atletaDni;
	public TarjetaDto(int cvc, String numeroTarjeta, Date fechaCaducidad, String atletaDni) {


		this.cvc = cvc;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaCaducidad = fechaCaducidad;
		this.atletaDni = atletaDni;
	}
	public TarjetaDto() {


	}

}
