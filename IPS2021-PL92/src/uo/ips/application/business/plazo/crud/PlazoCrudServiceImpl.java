package uo.ips.application.business.plazo.crud;

import java.sql.Date;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.plazo.PlazoCrudService;

public class PlazoCrudServiceImpl implements PlazoCrudService {

	@Override
	public Date getUltimoPlazoByCompeticionId(int competicionId) throws BusinessException {

		return new UltimoPlazoByCompeticiónId(competicionId).execute();
	}

}
