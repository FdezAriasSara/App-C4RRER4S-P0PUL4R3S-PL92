package uo.ips.application.business.pago;

public interface PagoCrudService {
	/**
	 *  * Permite el pago de la cuota de inscripci�n con tarjeta.
	 * @param pago
	 * @return DTO del pago realizado
	 */
	PagoDto pagarConTarjeta(PagoDto pago) ;
	/**
	 * Permite el pago de la cuota de inscripci�n  con transferencia bancaria.
	 * @param pago
	 * @return DTO del pago realizado
	 */
	PagoDto pagarConTransferencia(PagoDto pago);
}
