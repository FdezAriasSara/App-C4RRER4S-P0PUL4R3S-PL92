package uo.ips.application.business.atleta;

import java.util.List;

import uo.ips.application.business.BusinessException;

public interface AtletaCrudService {

	AtletaDto anadirAtleta(AtletaDto atleta) throws BusinessException;
	
	int CalcularCategoria(int idAtleta, int idCompeticion) throws BusinessException;
	
	AtletaDto encontrarPorId(int idAtleta) throws BusinessException;
	AtletaDto encontrarPorEmail(String email) throws BusinessException;
	
	List<AtletaDto> findAll() throws BusinessException;

}
