package uo.ips.application.business.Inscripcion.crud;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;

public class InscripcionCrudServiceImpl implements InscripcionCrudService{
	
	@Override
	public void inscribirAtleta(int idCompeticion, int idAtleta,int idCategoria) throws BusinessException {
		new InscribirAtleta(idCompeticion,idAtleta,idCategoria).execute();
		
	}
	

	@Override
	public void inscribirAtleta(String email, int idCompeticion) throws BusinessException {
		new InscribirAtletaPorEmail(email,idCompeticion).execute();
		
	}


	@Override
	public InscripcionDto anadirInscripción(InscripcionDto solicitud) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
