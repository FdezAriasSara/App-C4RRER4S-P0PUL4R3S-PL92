package uo.ips.application.business.arco;

import java.util.List;

public interface ArcoCrudService {
	public void AnadirArco(ArcoDto arco);
	public List<ArcoDto> getArcos(int idCompeticion);
}
