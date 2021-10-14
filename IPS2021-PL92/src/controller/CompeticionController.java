package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;

public class CompeticionController {
	
	private MainWindow mainW;
	private CompeticionCrudService competicionModel ;
	
	public CompeticionController(MainWindow main, CompeticionCrudService comMod) {
		this.mainW = main;
		this.competicionModel = comMod;
		
	}
	
	
	public void initActions() {
		
		mainW.getBtnListarCompeticionesAbiertas().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();
				String allComp = "";
				
				try {
					competiciones = competicionModel.ListarCompeticionesInscripcionesAbiertas();
					
				} catch (BusinessException e1) {
					mainW.getLblError().setText("Problemas al listar las carreras");
				}
				
				for(CompeticionDto c : competiciones) {
					allComp += c.toString() + "\n\n";
				}
				
				mainW.getTxtPCompeticiones().setEditable(true);
				
				mainW.getTxtPCompeticiones().setText(allComp);
				
				mainW.getTxtPCompeticiones().setEditable(false);
			}
		});
		
		
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
			competicionModel.inscribirAtleta(emailAtleta, idCompeticion);
			JOptionPane.showMessageDialog(null, "Atleta Inscrito");
			mainW.getLblError().setVisible(false);
		} catch (BusinessException e) {
			mainW.getLblError().setText(e.getMessage());
			mainW.getLblError().setVisible(true);
		}
		
	}
	
	

}
