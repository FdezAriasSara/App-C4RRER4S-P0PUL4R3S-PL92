package uo.ips.application.business;

import java.sql.Date;

import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.crud.InscripcionCrudServiceImpl;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.crud.AtletaCrudServiceImpl;
import uo.ips.application.business.atleta.crud.CalcularCategoria;
import uo.ips.application.business.categoria.CategoriaCrudService;
import uo.ips.application.business.categoria.crud.CategoriaCrudServiceImpl;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.crud.CompeticionCrudServiceImpl;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.crud.PagoCrudServiceImpl;
import uo.ips.application.business.plazo.PlazoCrudService;
import uo.ips.application.business.plazo.crud.PlazoCrudServiceImpl;
import uo.ips.application.business.registro.RegistroCrudService;
import uo.ips.application.business.registro.impl.RegistroCrudServiceImpl;
import uo.ips.application.business.plazo.PlazoCrudService;
import uo.ips.application.business.plazo.impl.PlazoCrudServiceImpl;

public class BusinessFactory {
	
	public static AtletaCrudService forAtletaCrudService() {
		return new AtletaCrudServiceImpl();
	}
	
	public static CompeticionCrudService forCompeticionCrudService() {
		return new CompeticionCrudServiceImpl();
	}
	
	public static CalcularCategoria forCalcularCategoria(int idAtleta, int idCompeticion) {
		return new CalcularCategoria(idAtleta, idCompeticion);
	}
	
	public static InscripcionCrudService forInscripcionCrudService() {
		return new InscripcionCrudServiceImpl();
	}
	
	public static PagoCrudService forPagoCrudService() {
		return new PagoCrudServiceImpl();
	}
	
	public static CategoriaCrudService forCategoria() {
		return new CategoriaCrudServiceImpl();
	}

	public static PlazoCrudService forPlazo() {
		return new PlazoCrudServiceImpl();
	}

	public static RegistroCrudService forRegistroCrudService() {
		
		return new RegistroCrudServiceImpl();
	}

	public static PlazoCrudService forPlazoCrudService() {
		
		return new PlazoCrudServiceImpl() ;
	}
}
