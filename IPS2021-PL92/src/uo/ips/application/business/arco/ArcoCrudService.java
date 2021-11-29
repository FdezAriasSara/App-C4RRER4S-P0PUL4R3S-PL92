package uo.ips.application.business.arco;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;

public interface ArcoCrudService {
	public void AnadirArco(ArcoDto arco);
	public List<ArcoDto> getArcos(int idCompeticion);
	public List<Time> getTiempos(int idAtleta, int idCompeticion);
	public HashMap<Integer, List<Time>> getAllTiempos(int idCompeticion);
	
}
