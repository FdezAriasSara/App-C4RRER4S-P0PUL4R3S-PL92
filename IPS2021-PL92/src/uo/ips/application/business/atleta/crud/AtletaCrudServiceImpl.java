package uo.ips.application.business.atleta.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;

public class AtletaCrudServiceImpl implements AtletaCrudService{

	@Override
	public AtletaDto añadirAtleta(AtletaDto atleta) throws BusinessException {
		return new AñadirAtleta(atleta).execute();
	}

}
