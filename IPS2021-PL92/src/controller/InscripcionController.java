package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import gui.MainWindow;
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

	private void initActions() {

		mainW.getBtnInscribirse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inscribirse(mainW.getTxtFEmail().getText(), mainW.getTxtFIDCompeticion().getText());
			}
		});

		mainW.getBtnGenerarClasificacion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				obtenerClasificacion(mainW.getTxtIDCompOrg().getText(),
						mainW.getCbCategoria().getSelectedItem().toString());
			}
		});

		mainW.getBtnObtenerAtletas().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				obtenerAtletas(mainW.getTxtIDCompOrg().getText());

			}

		});
		/*
		 * Metodo para cambiar al panel de pago con tarjeta.
		 */
		mainW.getBtTarjeta().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
			
				((CardLayout)mainW.getPnEscogerPago().getLayout()).show(mainW.getPnEscogerPago(), "pnTarj");
			}
		
		});
		/*
		 * M�todo para EFECTUAR el pago tras meter datos de tarjeta bancaria.
		 */
		mainW.getBtnPagarTarjeta2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PagoDto pagoDto =new PagoDto(LocalDate.now(), sesion.getIdAtleta(), sesion.getIdCompeticion());					
					pagoDto=pagCrud.pagarConTarjeta(pagoDto);
					JOptionPane.showMessageDialog(mainW,"Se ha realizado un pago.\n"+
							pagoDto.toString());
					((CardLayout)mainW.getPnEscogerPago().getLayout()).show(mainW.getPnEscogerPago(), "PnEscogerPag"); // restauramos el panel.
					((CardLayout)mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg1"); // Volvemos al panel principal.
					
				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
				}
			}
		});

		mainW.getBtTransferencia().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CompeticionDto dto = pagCrud.pagarConTransferencia(sesion.getIdAtleta(), sesion.getIdCompeticion());
					JOptionPane.showMessageDialog(mainW,
							"Ingrese en la cuenta " + dto.cuentaBancaria + "\nel importe de " + dto.cuota + " euros");
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, "A�n no se ha inscrito");
				}
			}
		});

	}

	private void obtenerAtletas(String idCompeticion) {
		if(idCompeticion.isEmpty() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vac�o");
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
				mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vac�o o menor que 0");
			}
		}

	}

	private void obtenerClasificacion(String idCompeticion, String sexo) {

		if (idCompeticion.isEmpty() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vac�o");
		}

		int id = -1;

		try {
			id = Integer.parseUnsignedInt(idCompeticion);
		} catch (NumberFormatException e1) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vac�o o menor que 0");
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
		if(emailAtleta.isEmpty() || emailAtleta.isEmpty() || idCompeticionString.isEmpty() || idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Alg�n campo est� vacio");
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
