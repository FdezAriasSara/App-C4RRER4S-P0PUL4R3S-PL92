package uo.ips.application.business.plazo;

import java.sql.Date;

import uo.ips.application.business.BusinessException;

public interface PlazoCrudService {
	/**
	 * Encuentra la fecha final del último plazo de una competición.
	 * @param competicionId 
	 * @return
	 * @throws BusinessException si no se encuentra la fecha
	 */
	Date getUltimoPlazoByCompeticionId(int competicionId) throws BusinessException;
}
