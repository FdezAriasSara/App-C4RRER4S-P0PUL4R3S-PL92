package uo.ips.application.business.pago.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class PagoCrudServiceImpl implements PagoCrudService {
	@Override
	public PagoDto pagarConTarjeta(int idAtleta, int idCompeticion,
			TarjetaDto tarjeta) throws BusinessException {
		return new PagarConTarjeta(idAtleta, idCompeticion, tarjeta).execute();
	}

	@Override
	public CompeticionDto pagarConTransferencia(int idAtleta, int idCompeticion)
			throws BusinessException {
		return new PagarConTransferencia(idAtleta, idCompeticion).execute();
	}
}
