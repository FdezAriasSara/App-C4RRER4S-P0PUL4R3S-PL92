package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


import gui.MainWindow;
import main.Main;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.Sesion;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;

public class InscripcionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud;
	private Sesion sesion;
	private PagoCrudService pagCrud;

	public InscripcionController(MainWindow main, InscripcionCrudService incCrud, PagoCrudService pagCrud) {
		this.mainW = main;
		this.incCrud = incCrud;
		this.initActions();
		this.pagCrud = pagCrud;
	}

	private void iniciarSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	private void initActions() {
		
		
		
		mainW.getBtnVolverBienvenida().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				iniciarSesion(null);
				mainW.getBtnListarInscripciones().setEnabled(false);// una vez haya iniciado sesión , el atleta puede
																	// acceder a sus inscripciones.
				mainW.vaciarCampoIniciarSesion();
				
				((CardLayout)mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg1");
				mainW.getTxtPCompeticiones().setText("");
				mainW.getTxtPClasificacion().setText("");
				mainW.getBtnVolverBienvenida().setEnabled(false);
			}
		});
		
		

		mainW.getBtnInscribirse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainW.getLblError().setVisible(false);
				inscribirse(mainW.getTxtFEmail().getText(), mainW.getTxtFIDCompeticion().getText());
			}
		});

		mainW.getBtnGenerarClasificacion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainW.getLblError().setVisible(false);
				obtenerClasificacion(mainW.getTxtIDCompOrg().getText(),
						mainW.getCbCategoria().getSelectedItem().toString());
			}
		});

		mainW.getBtnObtenerAtletas().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.getLblError().setVisible(false);
				obtenerAtletas(mainW.getTxtIDCompOrg().getText());

			}

		});
		/*
		 * Botón para que el atleta acceda a sus inscripciones.
		 */
		mainW.getBtnIniciarSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg5");

			}
		});
		/**
		 * Implementa la funcionalidad de inicio de sesión necesaria para listar las
		 * inscripciones.
		 */
		mainW.getBtnSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String email = mainW.getTextFieldIniciarSesion().getText();
				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes rellenar tu email para iniciar sesion.");
				} else {
					iniciarSesion(new Sesion(email));
					mainW.getBtnListarInscripciones().setEnabled(true);// una vez haya iniciado sesión , el atleta puede
																		// acceder a sus inscripciones.
					((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg2");

					mainW.vaciarCampoIniciarSesion();
				}

			}
		});

		/*
		 * Boton que despliega las inscripciones que corresponden al atleta.
		 */
		mainW.getBtnListarInscripciones().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String aux = "";

				try {

					List<String> inscripciones = new ArrayList<String>(
							incCrud.listarInscripcionesDelAtleta(sesion.getIdAtleta()));
					if (inscripciones.isEmpty()) {
						mainW.getTxtPCompeticiones().setText("No te has inscrito a ninguna competición.");
					}

					mainW.getTxtPCompeticiones().setText("");
					for (String info : inscripciones) {
						aux += info + "\n";

						mainW.getTxtPCompeticiones().setText(aux);
					}
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}

		});
		/*
		 * Metodo para cambiar al panel de pago con tarjeta.
		 */
		mainW.getBtTarjeta_1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				((CardLayout) mainW.getPanel_pago().getLayout()).show(mainW.getPanel_pago(), "tarjeta");
			}

		});
		/*
		 * Método para EFECTUAR el pago tras meter datos de tarjeta bancaria.
		 */
		mainW.getBtnPagarTarjeta2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PagoDto pagoDto = new PagoDto(LocalDate.now(), sesion.getIdAtleta(), sesion.getIdCompeticion());
					pagoDto = pagCrud.pagarConTarjeta(pagoDto);
					JOptionPane.showMessageDialog(mainW, "Se ha realizado un pago.\n" + pagoDto.toString());
					((CardLayout) mainW.getPanel_pago().getLayout()).show(mainW.getPanel_pago(), "escogerPago"); // restauramos
																													// el
																													// panel.
					mainW.vaciarCamposPago();
					((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg1"); // Volvemos al
																											// panel
																											// principal.

				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		mainW.getBtTransferencia_1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.getLblError().setVisible(false);
				try {
					CompeticionDto dto = pagCrud.pagarConTransferencia(sesion.getIdAtleta(), sesion.getIdCompeticion());
					JOptionPane.showMessageDialog(mainW,
							"Ingrese en la cuenta " + dto.cuentaBancaria + "\nel importe de " + dto.cuota + " euros");
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, "Aún no se ha inscrito");
				}
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
				
				mainW.getLblErrorOrg().setVisible(false);
				mainW.getLblErrorOrg().setText("Error: ");
				
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

		if (idCompeticion.isEmpty() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacío");
		}

		int id = -1;

		try {
			id = Integer.parseUnsignedInt(idCompeticion);
		} catch (NumberFormatException e1) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vacío o menor que 0");
		}

		try {
			List<InscripcionDto> clas = incCrud.obtenerClasificaciones(id, sexo);

			String res = "";

			for (InscripcionDto d : clas) {
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
		if(emailAtleta.isBlank() || emailAtleta.isEmpty() || idCompeticionString.isBlank() || idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Algún campo está vacio");
		}
		else {
			try {
				int idCompeticion = Integer.parseInt(idCompeticionString);
				incCrud.inscribirAtleta(emailAtleta, idCompeticion);
				JOptionPane.showMessageDialog(null, "Atleta Inscrito");
				mainW.getLblError().setVisible(false);
				sesion = new Sesion(emailAtleta, idCompeticion);
				((CardLayout)mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg4");
			} catch (BusinessException e) {
				mainW.getLblError().setText("Error: " + e.getMessage());
				mainW.getLblError().setVisible(true);
			}
			
		}
		
	}
}
