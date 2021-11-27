package uo.ips.application.business.categoria;

import uo.ips.application.business.BusinessException;

public interface CategoriaCrudService {

	public void añadirCategoria(CategoriaDto categoria, int idCompeticion)
			throws BusinessException;

	public CategoriaDto encontrarCategoriaPorId(int idCategoria)
			throws BusinessException;
}
