package uo.ips.application.business.atleta;

import uo.ips.application.business.BusinessException;

public interface AtletaCrudService {

	AtletaDto añadirAtleta(AtletaDto atleta) throws BusinessException;
}
