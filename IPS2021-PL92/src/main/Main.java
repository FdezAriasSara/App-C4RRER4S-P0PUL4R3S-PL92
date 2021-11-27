package main;

import java.awt.EventQueue;

import controller.CancelacionController;
import controller.ClubController;
import controller.ComparacionController;
import controller.CompeticionController;
import controller.InscripcionController;
import gui.MainWindow;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					MainWindow frame = new MainWindow();
					new CompeticionController(frame);
					new InscripcionController(frame);
					new ClubController(frame);
					new ComparacionController(frame);
					new CancelacionController(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
