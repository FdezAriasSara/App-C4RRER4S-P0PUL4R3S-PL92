package uo.ips.application.business.registro.impl;

import uo.ips.application.business.registro.RegistroCrudService;
import uo.ips.application.business.registro.crud.ComprobarDatos;

public class RegistroCrudServiceImpl implements RegistroCrudService {

	@Override
	public boolean ComprobarDatosInscripcion(String email)  {
		return new ComprobarDatos(email).execute();

	}

}
