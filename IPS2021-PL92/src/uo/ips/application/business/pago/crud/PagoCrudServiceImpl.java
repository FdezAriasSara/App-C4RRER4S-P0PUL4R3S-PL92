package uo.ips.application.business.pago.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.pago.PagoDto;

public class PagoCrudServiceImpl {
	PagoDto pagarConTarjeta(PagoDto pago) throws BusinessException {
		return new PagarConTarjeta(pago).execute();
	}
	PagoDto pagarConTransferencia(PagoDto pago) throws BusinessException {
		return new PagarConTarjeta(pago).execute();
		//(...) PUSE CON TARJETA PARA Q NO DIERA ERROR
		
	}
	

}
