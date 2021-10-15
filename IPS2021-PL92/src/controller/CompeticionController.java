package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


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
		this.initActions();
	}
	
	
	private void initActions() {
		
		mainW.getBtnListarCompeticionesAbiertas().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initBotonListarComp();
			}
			
		});
		
	}
	
	
	private void initBotonListarComp() {
		
<<<<<<< HEAD
		mainW.getBtnListarCompeticionesAbiertas().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();
				String allComp = "";
				
				try {
					competiciones = competicionModel.ListarCompeticionesInscripcionesAbiertas();
					
				} catch (BusinessException e1) {
					mainW.setErrorAlListarCompeticiones();
				}
				
				for(CompeticionDto c : competiciones) {
					allComp += c.toString() + "\n\n";
				}
				mainW.presentarCompeticiones(allComp);
			}
		});
		
		
		///////////////////////////////////////////////////////////////
		
		
		
		
		
=======
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
	
>>>>>>> refs/remotes/origin/master
	}
	
	

}
