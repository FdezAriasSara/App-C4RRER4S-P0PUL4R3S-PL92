package uo.ips.application.business.categoria.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.categoria.CategoriaCrudService;
import uo.ips.application.business.categoria.CategoriaDto;

public class CategoriaCrudServiceImpl implements CategoriaCrudService {

	@Override
	public void A�adirCategoria(CategoriaDto categoria,int competicionId) throws BusinessException {
		new A�adirCategoria(categoria,competicionId).execute();
		
	}

}