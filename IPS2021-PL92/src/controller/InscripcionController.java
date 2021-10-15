package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.competicion.CompeticionCrudService;

public class InscripcionController {
	
	private MainWindow mainW;
	private InscripcionCrudService incCrud;
	
	
	
	public InscripcionController(MainWindow main, InscripcionCrudService incCrud) {
		this.mainW = main;
		this.incCrud = incCrud;
		this.initActions();
	}
	

	private void initActions() {
		

		mainW.getBtnInscribirse().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inscribirse(mainW.getTxtFEmail().getText(),mainW.getTxtFIDCompeticion().getText());				
			}
		});
		
	}
	
	

	protected void inscribirse(String emailAtleta, String idCompeticionString) {
		try {
			int idCompeticion = Integer.parseInt(idCompeticionString);
			incCrud.inscribirAtleta(emailAtleta, idCompeticion);
			JOptionPane.showMessageDialog(null, "Atleta Inscrito");
			mainW.getLblError().setVisible(false);
		} catch (BusinessException e) {
			mainW.getLblError().setText("Error: " + e.getMessage());
			mainW.getLblError().setVisible(true);
		}
		
	}
	

}
