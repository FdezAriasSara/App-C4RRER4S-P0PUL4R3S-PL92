package uo.ips.application.business.pago;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;

public interface PagoCrudService {
	/**
	 *  * Permite el pago de la cuota de inscripci�n con tarjeta.
	 *  En caso de que el pago dejara de ser una simulaci�n , deber�a a�adirse el par�metro TarjetaDto.
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
	CompeticionDto pagarConTransferencia(int idAtleta, int idCompeticion) throws BusinessException;
}
