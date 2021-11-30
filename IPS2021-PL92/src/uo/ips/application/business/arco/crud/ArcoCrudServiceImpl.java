package uo.ips.application.business.arco.crud;

import java.sql.Time;
import java.util.List;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.arco.ArcoCrudService;
import uo.ips.application.business.arco.ArcoDto;

public class ArcoCrudServiceImpl implements ArcoCrudService {

	@Override
	public void AnadirArco(ArcoDto arco) {
		new AnadirArco(arco).execute();

	}

	@Override
	public List<ArcoDto> getArcos(int idCompeticion) {
		return new GetArcos(idCompeticion).execute();
	}

	@Override
	public Time obtenerCuandoPasoPorArco(int idArco, int parseInt, int dorsal)
			throws BusinessException {

		return new ObtenerCuandoPasoPorArco(idArco, parseInt, dorsal).execute();
	}

}
