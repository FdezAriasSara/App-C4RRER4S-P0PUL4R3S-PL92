package uo.ips.application.business.pago;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public interface PagoCrudService {
	/**
	 *  * Permite el pago de la cuota de inscripciÑn con tarjeta.
	 *  En caso de que el pago dejara de ser una simulaciÑn , deberÑa aÑadirse el parÑmetro TarjetaDto.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException cuando no se encuentra la inscripciÑn que buscamos
	 */
	PagoDto pagarConTarjeta(PagoDto pago,TarjetaDto tarjeta) throws BusinessException ;
	/**
	 * Permite el pago de la cuota de inscripciÑn  con transferencia bancaria.
	 * @param pago
	 * @return DTO del pago realizado
	 * @throws BusinessException 
	 */
	CompeticionDto pagarConTransferencia(int idAtleta, int idCompeticion) throws BusinessException;
}
