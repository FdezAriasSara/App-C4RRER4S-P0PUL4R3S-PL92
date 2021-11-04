package uo.ips.application.business.pago;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public interface PagoCrudService {
	/**
	 *  * Permite el pago de la cuota de inscripción con tarjeta.
	 *  En caso de que el pago dejara de ser una simulación , debería añadirse el parámetro TarjetaDto.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException cuando no se encuentra la inscripción que buscamos
	 */
	PagoDto pagarConTarjeta(PagoDto pago,TarjetaDto tarjeta) throws BusinessException ;
	/**
	 * Permite el pago de la cuota de inscripción  con transferencia bancaria.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException 
	 */
	CompeticionDto pagarConTransferencia(int idAtleta, int idCompeticion) throws BusinessException;
}
