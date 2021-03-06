package uo.ips.application.business.plazo.crud;

import java.sql.Date;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.plazo.PlazoCrudService;
import uo.ips.application.business.plazo.PlazoDto;

public class PlazoCrudServiceImpl implements PlazoCrudService {

	@Override
	public Date getUltimoPlazoByCompeticionId(int competicionId)
			throws BusinessException {

		return new UltimoPlazoByCompeticionId(competicionId).execute();

	}

	@Override
	public void addPlazo(PlazoDto plazo) {
		new AddPlazo(plazo).execute();

	}

	@Override
	public double obtenerCuotaDeInscripcion(int idCompeticion,
			Date fechaInscripcion) throws BusinessException {
		return new ObtenerCuotaDeInscripcion(idCompeticion, fechaInscripcion)
				.execute();
	}

}
