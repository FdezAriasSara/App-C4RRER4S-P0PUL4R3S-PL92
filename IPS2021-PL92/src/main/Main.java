package main;

import java.awt.EventQueue;

import controller.*;
import gui.MainWindow;
import uo.ips.application.business.BusinessFactory;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					new CompeticionController(frame, BusinessFactory.forCompeticionCrudService() );
					new InscripcionController(frame, BusinessFactory.forInscripcionCrudService(), BusinessFactory.forPagoCrudService());
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
