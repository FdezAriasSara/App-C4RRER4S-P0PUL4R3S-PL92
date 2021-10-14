package uo.ips.application.business.pago;

import uo.ips.application.business.BusinessException;

public interface PagoCrudService {
	/**
	 *  * Permite el pago de la cuota de inscripci�n con tarjeta.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException cuando no se encuentra la inscripci�n que buscamos
	 */
	PagoDto pagarConTarjeta(PagoDto pago,TarjetaDto tarjeta) throws BusinessException ;
	/**
	 * Permite el pago de la cuota de inscripci�n  con transferencia bancaria.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException 
	 */
	PagoDto pagarConTransferencia(PagoDto pago,TransferenciaDto transferencia) throws BusinessException;
}
