package uo.ips.application.business.pago.crud;


import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class PagoCrudServiceImpl implements PagoCrudService{
	public PagoDto pagarConTarjeta(PagoDto pago ,TarjetaDto tarjeta) throws BusinessException {
		return new PagarConTarjeta(pago,tarjeta).execute();
	}
	public CompeticionDto pagarConTransferencia(int idAtleta, int idCompeticion)throws BusinessException {
		return new PagarConTransferencia(idAtleta,idCompeticion).execute();
	}
	

}
