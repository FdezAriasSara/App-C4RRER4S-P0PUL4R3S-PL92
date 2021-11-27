package uo.ips.application.business.categoria.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.categoria.CategoriaCrudService;
import uo.ips.application.business.categoria.CategoriaDto;

public class CategoriaCrudServiceImpl implements CategoriaCrudService {

	@Override

	public void añadirCategoria(CategoriaDto categoria, int competicionId)

			throws BusinessException {
		new AñadirCategoria(categoria, competicionId).execute();

	}

	@Override
	public CategoriaDto encontrarCategoriaPorId(int idCategoria)
			throws BusinessException {

		return new EncontrarCategoriaPorId(idCategoria).execute();
	}

}
