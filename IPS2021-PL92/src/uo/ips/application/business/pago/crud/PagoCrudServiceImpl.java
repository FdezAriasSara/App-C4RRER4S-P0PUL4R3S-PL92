package uo.ips.application.business.pago.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;
import uo.ips.application.business.pago.TransferenciaDto;

public class PagoCrudServiceImpl {
	PagoDto pagarConTarjeta(PagoDto pago,TarjetaDto tarjeta) throws BusinessException {
		return new PagarConTarjeta(pago,tarjeta).execute();
	}
	PagoDto pagarConTransferencia(PagoDto pago,TransferenciaDto transferencia) throws BusinessException {
		return new PagoDto();
		//(...) PUSE CON TARJETA PARA Q NO DIERA ERROR
		
	}
	

}
