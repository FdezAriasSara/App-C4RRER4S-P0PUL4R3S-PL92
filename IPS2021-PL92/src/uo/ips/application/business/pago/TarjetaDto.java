package uo.ips.application.business.pago;

public class TarjetaDto {
	public int cvc;
	public String numeroTarjeta;
	public String fechaCaducidad;
	public String atletaDni;
	public TarjetaDto(int cvc, String numeroTarjeta, String fechaCaducidad, String atletaDni) {


		this.cvc = cvc;
		this.numeroTarjeta = numeroTarjeta;
		this.fechaCaducidad = fechaCaducidad;
		this.atletaDni = atletaDni;
	}
	public TarjetaDto() {


	}

}
