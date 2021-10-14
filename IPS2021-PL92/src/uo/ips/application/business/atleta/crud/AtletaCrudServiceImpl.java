package uo.ips.application.business.atleta.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;

public class AtletaCrudServiceImpl implements AtletaCrudService{

	@Override
	public AtletaDto anadirAtleta(AtletaDto atleta) throws BusinessException {
		return new AnadirAtleta(atleta).execute();
	}

	@Override
	public int CalcularCategoria(int idAtleta, int idCompeticion) {
		// TODO Auto-generated method stub
		return new CalcularCategoria(idAtleta, idCompeticion).excute();
	}

}
