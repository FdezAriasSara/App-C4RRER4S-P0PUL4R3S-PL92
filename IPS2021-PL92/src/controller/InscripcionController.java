package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;


import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.atleta.AtletaDto;
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
		
		
		mainW.getBtnGenerarClasificacion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				obtenerClasificacion(mainW.getTxtIDCompOrg().getText(), mainW.getCbCategoria().getSelectedItem().toString());
			}
		});
		
		mainW.getBtnObtenerAtletas().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerAtletas(mainW.getTxtIDCompOrg().getText());
				
			}

		});
		
	}
	private void obtenerAtletas(String idCompeticion) {
		if(idCompeticion.isBlank() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacío");
		}else {
			int id = -1;
			try {
				id = Integer.parseUnsignedInt(idCompeticion);
				
				String res  = incCrud.obtenerAtletas(id);
				mainW.getTxtPClasificacion().setEditable(true);
				mainW.getTxtPClasificacion().setText("");
				mainW.getTxtPClasificacion().setText(res);
				mainW.getTxtPClasificacion().setEditable(false);
				
			}catch (BusinessException e) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			}catch(NumberFormatException e1) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vacío o menor que 0");
			}
		}
	}
	
	private void obtenerClasificacion(String idCompeticion, String sexo) {
		
		if(idCompeticion.isEmpty() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacío");
		}
		
		int id = -1;
		
		try {
			id = Integer.parseUnsignedInt(idCompeticion);
		}catch(NumberFormatException e1) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vacío o menor que 0");
		}
		
		
		try {
			List<InscripcionDto> clas = incCrud.obtenerClasificaciones(id, sexo);

			String res = "";
			
			for(InscripcionDto d : clas) {
				res += d.toStringParaClasificacion() + " \n\n";
			}
			
			mainW.getTxtPClasificacion().setEditable(true);
			mainW.getTxtPClasificacion().setText("");
			mainW.getTxtPClasificacion().setText(res);
			mainW.getTxtPClasificacion().setEditable(false);
			
			
		} catch (BusinessException e) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
		}
	}
	
	

	private void inscribirse(String emailAtleta, String idCompeticionString) {
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
