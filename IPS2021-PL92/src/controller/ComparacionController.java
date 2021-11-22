package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Sesion;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.competicion.CompeticionCategoriaDto;
import uo.ips.application.business.competicion.CompeticionCrudService;

public class ComparacionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory
			.forInscripcionCrudService();
	private Sesion sesion;

	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();

	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();// para saber si hay dorsales
											// reservados

	private List<CompeticionCategoriaDto> currentCategoriasInComboBox;
	private int currentIdCompeticon = -1;

	public ComparacionController(MainWindow main) {
		this.mainW = main;
		iniciarSesion(sesion);
		this.initActions();
	}

	private void iniciarSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	private void initActions() {

		/*
		 * Botón para cambiar de usuario una vez ha iniciado sesión
		 */
		mainW.getBtnCambiarUsuario().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg5");

			}
		});
		/*
		 * Botón para cambiar de usuario una vez ha iniciado sesión
		 */
		mainW.getBtnCambiarUsuario().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg5");

			}
		});
		/*
		 * Botón para que el atleta acceda a sus inscripciones.
		 */
		mainW.getBtnIniciarSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg5");

			}
		});
		/**
		 * Implementa la funcionalidad de inicio de sesión necesaria para listar
		 * las inscripciones.
		 */
		mainW.getBtnSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String email = mainW.getTextFieldIniciarSesion().getText();
				if (email.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Debes rellenar tu email para iniciar sesion.");
				} else {
					iniciarSesion(new Sesion(email));
					if (sesion.getIdAtleta() == Sesion.NO_INICIADO) {
						mainW.mostrarErrorInicioSesion(
								"No se ha encontrado un usuario asociado al correo electrónico.\n Inténtalo de nuevo.");
						mainW.vaciarCampoIniciarSesion();
					} else {

						// perfil del atleta
						((CardLayout) mainW.getPanel_card().getLayout())
								.show(mainW.getPanel_card(), "perfil");

						mainW.vaciarCampoIniciarSesion();
					}

				}

			}
		});

		/*
		 * Boton que despliega las inscripciones que corresponden al atleta.
		 */
		mainW.getBtnMisInscripciones().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					// Esta lista mejor de inscripciones

					List<AtletaInscritoDto> inscripciones = incCrud
							.listarInscripcionesAtletaConDto(
									sesion.getIdAtleta());

					// Esta es la lista del nombre de las columnas
					String[] columnNames = { "Competición",
							"Estado Inscripcion", "Ultimo Cambio" };

					// Esta es la array que contiene los elementos a
					// listar, el primer [es el numero
					// de fila]
					// el segundo [el numero de la columna de acuerdo a
					// los datos de arriba]
					String[][] valuesToTable = new String[inscripciones
							.size()][columnNames.length];

					int count = 0;
					for (AtletaInscritoDto dto : inscripciones) {
						int col = 0;
						valuesToTable[count][col++] = dto.nombreCompeticion;
						valuesToTable[count][col++] = dto.estado.toString();
						valuesToTable[count][col++] = dto.fechaUltimoCambio
								.toString();
						count++;
					}

					TableModel model = new DefaultTableModel(valuesToTable,
							columnNames) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};

					mainW.getTableCompeticion().setModel(model);

				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});

	}

	private void obtenerAtletas(String idCompeticion) {
		if (idCompeticion.isBlank() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacío");
		} else {
			int id = -1;
			try {
				id = Integer.parseUnsignedInt(idCompeticion);

				List<AtletaInscritoDto> res = incCrud
						.obtenerAtletasParaCompeticion(id);

				String[] columnNames = { "Nombre", "Apellidos", "Categoría",
						"Fecha de Inscripcion", "Estado" };

				String[][] valuesToTable = new String[res
						.size()][columnNames.length];

				int count = 0;
				for (AtletaInscritoDto dto : res) {
					int col = 0;
					valuesToTable[count][col++] = dto.nombre;
					valuesToTable[count][col++] = dto.apellido;
					valuesToTable[count][col++] = dto.categoria;
					valuesToTable[count][col++] = dto.fechaInscripcion
							.toString();
					valuesToTable[count][col++] = dto.estado.toString();
					count++;
				}

				TableModel model = new DefaultTableModel(valuesToTable,
						columnNames) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};

				mainW.getTableAtletasDeCompSeleccionada().setModel(model);

				mainW.getLblErrorOrg().setVisible(false);
				mainW.getLblErrorOrg().setText("Error: ");

			} catch (BusinessException e) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			} catch (NumberFormatException e1) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText(
						"Error: ID de competicion no numerico, vacío o menor que 0");
			}
		}

	}
}