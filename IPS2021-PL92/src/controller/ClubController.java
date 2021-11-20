package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import alb.util.jdbc.Jdbc;
import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;

public class ClubController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory.forInscripcionCrudService();
	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();
	
	public ClubController(MainWindow main) {
		this.mainW = main;
		
		this.initActions();
	}
	
	
	
	private void initActions() {
		
		
		mainW.getBtnInscribirClubArch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initImportarArchivoAtletas();
			}

		});
		
	}
	
	
	
	private void initImportarArchivoAtletas() {
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(mainW.getPanel_organizador());
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			try {
				List<AtletaDto> dtos = ParseadorClubes.parse(selectedFile);
				List<AtletaDto> noRegistrados = new ArrayList<AtletaDto>();
				
				List<AtletaDto> allAtletas = atlCrud.findAll();
				boolean found = false;
				
				for(AtletaDto atleta : dtos) {
					found = false;
					for(AtletaDto registrado : allAtletas) {
						
						if(atleta.dni.equals(registrado.dni)) {
							found = true;
							break;
						}
						
						
						
					}
					
					if(!found) {
						atlCrud.anadirAtleta(atleta);
						noRegistrados.add(atleta);
					}
					
				}
				
				int idCompeticion = Integer.parseInt(mainW.getTableCompeticion().
						getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString());
				String club = dtos.get(0).club;
				Connection c = Jdbc.getConnection();
				String[] emails = new String[dtos.size()];
				for(int i = 0; i < dtos.size(); i++) {
					emails[i] = dtos.get(i).email;
					
				}
				
				int alre = incCrud.inscribirClusterEmails(idCompeticion, c,club, emails);
				
				
				
				JOptionPane.showMessageDialog(
						   mainW,
						   "Atletas registrados con exito.\nSe han registrado " 
						   + noRegistrados.size() 
						   + " atletas \nque no estaban registrados previamente.\nHabía " 
						   + alre + " atletas ya inscritos.");

				
			} catch (IOException e) {
				mainW.getLblErrorOrg().setText("Error: imposible encontrar archivo o leer sus datos");;
			} catch(BusinessException e) {
				mainW.getLblErrorOrg().setText("Error: imposible enviar datos a la base de datos");
			} catch (SQLException e) {
				mainW.getLblErrorOrg().setText("Error: imposible enviar datos a la base de datos");
			}
			
		}
	}
}
