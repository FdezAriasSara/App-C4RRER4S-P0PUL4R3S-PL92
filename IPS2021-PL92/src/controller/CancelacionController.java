package controller;

import gui.CrearCarrera;
import gui.MainWindow;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionCrudService;

public class CancelacionController {
	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();
	private MainWindow mainW;
	private CrearCarrera carrera;

	public CancelacionController(MainWindow mainW) {
		this.mainW = mainW;
		this.carrera = carrera;
		this.initActions();
	}

	private void initActions() {

	}
}
