package uo.ips.application.business.plazo.impl;

import uo.ips.application.business.plazo.PlazoCrudService;
import uo.ips.application.business.plazo.PlazoDto;

public class PlazoCrudServiceImpl implements PlazoCrudService {

	@Override
	public void addPlazo(PlazoDto plazo) {
		new AddPlazo(plazo).execute();
		
	}

}
