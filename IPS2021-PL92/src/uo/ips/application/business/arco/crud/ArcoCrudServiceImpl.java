package uo.ips.application.business.arco.crud;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;

import uo.ips.application.business.arco.ArcoCrudService;
import uo.ips.application.business.arco.ArcoDto;

public class ArcoCrudServiceImpl implements ArcoCrudService{

	@Override
	public void AnadirArco(ArcoDto arco) {
		new AnadirArco(arco).execute();
		
	}

	@Override
	public List<ArcoDto> getArcos(int idCompeticion) {
		return new GetArcos(idCompeticion).execute();
	}

	@Override
	public List<Time> getTiempos(int dorsal, int idCompeticion) {
		return new GetTiempos(dorsal, idCompeticion).execute();
	}

	@Override
	public HashMap<Integer, List<Time>> getAllTiempos(int idCompeticion) {
		return new GetAllTiempos(idCompeticion).execute();
	}
	
	
	
	

}
