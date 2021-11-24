package uo.ips.application.business.arco.crud;

import uo.ips.application.business.arco.ArcoCrudService;
import uo.ips.application.business.arco.ArcoDto;

public class ArcoCrudServiceImpl implements ArcoCrudService{

	@Override
	public void AnadirArco(ArcoDto arco) {
		new AnadirArco(arco).execute();
		
	}

}
