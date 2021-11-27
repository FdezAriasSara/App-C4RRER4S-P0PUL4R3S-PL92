package controller;

import gui.MainWindow;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionCrudService;

public class CancelacionController {
	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();
	private MainWindow mainW;

	public CancelacionController(MainWindow mainW) {
		this.mainW = mainW;
		this.initActions();
	}

	private void initActions() {

	}
}
