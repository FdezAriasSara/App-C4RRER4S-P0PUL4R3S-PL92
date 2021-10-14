package main;

import java.awt.EventQueue;

import controller.CompeticionController;
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
					CompeticionController cc = new CompeticionController(frame, BusinessFactory.forCompeticionCrudService() );
					cc.initActions();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


}
