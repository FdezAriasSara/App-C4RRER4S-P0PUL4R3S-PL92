package uo.ips.application.business.arco;

import java.sql.Time;
import java.util.List;

import uo.ips.application.business.BusinessException;

public interface ArcoCrudService {
	public void AnadirArco(ArcoDto arco);

	public List<ArcoDto> getArcos(int idCompeticion);

	public Time obtenerCuandoPasoPorArco(int idArco, int parseInt, int dorsal)
			throws BusinessException;
}
