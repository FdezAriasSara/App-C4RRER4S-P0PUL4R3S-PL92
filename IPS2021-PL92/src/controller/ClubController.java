package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
	
	private List<AtletaDto> allAtletas;
	private List<AtletaDto> añadidosPorAhora = new ArrayList<AtletaDto>();
	String[] columnNames = { "Nombre", "Apellido", "Email", "Sexo", "Fecha de Nacimiento", "DNI" };
	String[][] valuesToTable;
	Connection c ;
	int currPlazasDisp;
	
	public ClubController(MainWindow main) {
		this.mainW = main;
		
		this.initActions();
	}
	
	
	
	private void initActions() {
		
		
		mainW.getBtnBorrarAtletaDeFormulario().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				eliminarAtletaDeTabla();
				
			}
				
			});
		
		
			mainW.getBtnTerminar().addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					inscribirAtletasFormulario();
					
				}
				
			});
		
		
		
		mainW.getBtnInscribirClubFormulario().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainW.getBtnVolverBienvenida().setEnabled(true);
				
				
				if(mainW.getTableCompeticion().
						getModel() == null || mainW.getTableCompeticion().getSelectedRow() < 0 || 
						mainW.getTableCompeticion().
						getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0) == null) {
					JOptionPane.showMessageDialog(mainW,"Primero selecciona una competicion.");
					return;
				}
				
				mainW.getTxtFCompeticionForm().setEditable(true);
				mainW.getTxtFCompeticionForm().setText(mainW.getTableCompeticion().
					getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString());
				mainW.getTxtFCompeticionForm().setEditable(false);
				
				
				String plazasDisp = mainW.getTableCompeticion().
						getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 6).toString();
				
				currPlazasDisp = Integer.parseInt(plazasDisp);
				mainW.getTxtPlazasDispnibles().setEditable(true);
				mainW.getTxtPlazasDispnibles().setText(plazasDisp);
				mainW.getTxtPlazasDispnibles().setEditable(false);
				
				
				
				try {
					allAtletas = atlCrud.findAll();
				} catch (BusinessException e1) {
					mainW.getLblError().setText("BD en mantenimiento.");
					return;
				}
				
				reiniciarValoresFormulario();
				mostrarAtletasEnTabla();
				
				((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "formulario");
				
				
				

			}
		});
		
		
		mainW.getBtnInscribirClubArch().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(mainW.getTableCompeticion().
						getModel() == null || mainW.getTableCompeticion().getSelectedRow() < 0 || 
						mainW.getTableCompeticion().
						getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0) == null) {
					JOptionPane.showMessageDialog(mainW,"Primero selecciona una competicion.");
					
					return;
				}
				
				initImportarArchivoAtletas();
			}

		});
		
		mainW.getBtnInscribirFormulario().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				leerDatosFormulario();
			}

		});
		
	}
	
	private void inscribirAtletasFormulario() {
		
		
		try {
			
			List<AtletaDto> noRegistrados = new ArrayList<AtletaDto>();
			
			boolean found = false;
			
			for(AtletaDto atleta : añadidosPorAhora) {
				found = false;
				atleta.club = mainW.getTxtFNombreClub().getText();
				for(AtletaDto registrado : this.allAtletas) {
					
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
			String club = añadidosPorAhora.get(0).club;
			c = Jdbc.getConnection();
			String[] emails = new String[añadidosPorAhora.size()];
			for(int i = 0; i < añadidosPorAhora.size(); i++) {
				emails[i] = añadidosPorAhora.get(i).email;
				
			}
			
			incCrud.inscribirClusterEmails(idCompeticion, c,club, emails);
			
			
			
			JOptionPane.showMessageDialog(
					   mainW,
					   "Atletas registrados con exito.\nSe han registrado " 
					   + noRegistrados.size() 
					   + " atletas \nque no estaban registrados previamente.");

			
			
			
		}catch(SQLException  e) {
			
			
			JOptionPane.showMessageDialog(mainW,"Han quedado atletas sin inscribir debido a falta de plazas.");
			
		}catch( BusinessException e) {
			JOptionPane.showMessageDialog(mainW,"Ha habido errors con la BD");
		}finally{
			Jdbc.close(c);
		}
		
		
	}
	
	
	private void eliminarAtletaDeTabla() {
		

		if(mainW.getTableFormulario().
				getModel() == null || mainW.getTableFormulario().getSelectedRow() < 0
				) {
			JOptionPane.showMessageDialog(mainW,"Seleccione al atleta.");
			return;
		}else {
			
			String dni = (String) mainW.getTableFormulario().
			getModel().getValueAt(mainW.getTableFormulario().getSelectedRow(), columnNames.length - 1);
			
			
			for(int i = 0; i < añadidosPorAhora.size() ; i++) {
				if(añadidosPorAhora.get(i).dni.equals(dni)) {
					añadidosPorAhora.remove(i);
					currPlazasDisp++;
				}
			}
			
			
		}
		
		mostrarAtletasEnTabla();
		mainW.getTxtPlazasDispnibles().setEditable(true);
		mainW.getTxtPlazasDispnibles().setText(""+currPlazasDisp);
		mainW.getTxtPlazasDispnibles().setEditable(false);
		
		
	}
	
	
	private void mostrarAtletasEnTabla() {
		

		
		int counter = 0;
		valuesToTable = new String[añadidosPorAhora.size()][columnNames.length];
		for (AtletaDto c : añadidosPorAhora) {
			int col = 0;
			valuesToTable[counter][col++] = "" + c.nombre;
			valuesToTable[counter][col++] = "" + c.apellido;
			valuesToTable[counter][col++] = "" + c.email;
			valuesToTable[counter][col++] = "" + c.sexo;
			valuesToTable[counter][col++] = "" + c.fechaNacimiento.toString();
			valuesToTable[counter][col++] = "" + c.dni;
			counter++;
		}
		

		TableModel model = new DefaultTableModel(valuesToTable, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		mainW.getTableFormulario().setModel(model);
		
		
	}
	
	
	private void reiniciarValoresFormulario() {
		
		
		mainW.getTxtFNombreClub().setText("");
		
		mainW.getTxtFNombreAtl().setText("");
		mainW.getTxtFEmailFormulario().setText("");
		mainW.getTxtFDniForm().setText("");
		mainW.getTxtFApellido().setText("");
		mainW.getFechaFinal().setDate(null);
		
		this.añadidosPorAhora = new ArrayList<AtletaDto>();
		this.valuesToTable = null;
		
	}
	
	
	private void leerDatosFormulario() {
		
		
		
		if(mainW.getTxtFNombreAtl().getText().isBlank() ||
				mainW.getTxtFApellido().getText().isBlank()||
				mainW.getTxtFEmailFormulario().getText().isBlank() ||
				mainW.getTxtFDniForm().getText().isBlank() ||
				mainW.getFechaFinal().getDate() == null) {
			
			
			mainW.getLblErrorForm().setText("Error: hay campos incompletos");
			return;
		}
		
		if(!mainW.getTxtFEmailFormulario().getText().matches("[a-zA-Z0-9]{0,100}@[a-zA-Z0-9]{0,40}+.(es|com|org)")) {
			mainW.getLblErrorForm().setText("Error: mail con formato erroneo");
			return;
		}
		
		Date formDate = mainW.getFechaFinal().getDate();
		Date currDate = new Date(System.currentTimeMillis());
		
		long milliseconds18Años = 18*365*24*60*60*1000;
		
		if(currDate.getTime() - formDate.getTime() < milliseconds18Años) {
			mainW.getLblErrorForm().setText("Error: menores de 18 no pueden inscribirse");
			return;
		}
		
		if(this.currPlazasDisp <= 0) {
			mainW.getLblErrorForm().setText("Error: no quedan suficientes plazas disponibles");
			return;
		}
		
		AtletaDto atleta = new AtletaDto();
		
		atleta.nombre = mainW.getTxtFNombreAtl().getText();
		atleta.apellido = mainW.getTxtFApellido().getText();
		atleta.email = mainW.getTxtFEmailFormulario().getText();
		atleta.dni = mainW.getTxtFDniForm().getText();
		atleta.fechaNacimiento = new java.sql.Date( mainW.getFechaFinal().getDate().getTime());
		atleta.sexo = mainW.getCBoxSexo().getSelectedItem().toString();
		
		for(AtletaDto atl : añadidosPorAhora) {
			if(atl.dni.equals(atleta.dni)) {
				mainW.getLblErrorForm().setText("Error: atleta con mismo dni ya añadido");
				return;
			}
		}
		
		añadidosPorAhora.add(atleta);
		currPlazasDisp--;
		mainW.getTxtPlazasDispnibles().setEditable(true);
		mainW.getTxtPlazasDispnibles().setText(""+currPlazasDisp);
		mainW.getTxtPlazasDispnibles().setEditable(false);
		mostrarAtletasEnTabla();
		
		mainW.getLblErrorForm().setText("Error:");
		return;
		
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
				this.c = Jdbc.getConnection();
				String[] emails = new String[dtos.size()];
				for(int i = 0; i < dtos.size(); i++) {
					emails[i] = dtos.get(i).email;
					
				}
				
				incCrud.inscribirClusterEmails(idCompeticion, c,club, emails);
				
				
				
				JOptionPane.showMessageDialog(
						   mainW,
						   "Atletas registrados con exito.\nSe han registrado " 
						   + noRegistrados.size() 
						   + " atletas \nque no estaban registrados previamente.");

				
				
				
			} catch (IOException e) {
				mainW.getLblErrorOrg().setText("Error: imposible encontrar archivo o leer sus datos");;
			} catch(BusinessException e) {
				mainW.getLblErrorOrg().setText("Error: imposible enviar datos a la base de datos");
			} catch (SQLException e) {
				mainW.getLblErrorOrg().setText("Error: imposible enviar datos a la base de datos");
			}finally{
				Jdbc.close(c);
			}
			
		}
	}
}
