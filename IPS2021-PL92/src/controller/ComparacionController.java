package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Sesion;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.categoria.CategoriaCrudService;
import uo.ips.application.business.competicion.CompeticionCrudService;

public class ComparacionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory
			.forInscripcionCrudService();
	Sesion sesion;// protected para poder acceder a el desde cancelación
					// controller

	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();
	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();
	protected String nombreComp;
	protected String idCompeticionSeleccionada;
	private int dorsalAtletaSeleccionado;
	private CategoriaCrudService categoriaCrud = BusinessFactory.forCategoria();

	public ComparacionController(MainWindow main) {
		this.mainW = main;
		iniciarSesion(sesion);
		this.initActions();
	}

	private void iniciarSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	private void initActions() {
		mainW.getBtnCambiarUsuario().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.resetearCamposYVistasPerfilAtleta();
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg5");

			}

		});
		mainW.getBtnVolverPerfil().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				volverAMiPerfil();

			}
		});
		mainW.getBtnVolverPerfil2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((DefaultTableModel) mainW.getTableComparativa().getModel())
						.setRowCount(0);
				((CardLayout) mainW.getPanel_perfilAtleta().getLayout())
						.show(mainW.getPanel_perfilAtleta(), "otrosAtletas");

			}
		});

		/*
		 * Bot�n para cambiar de usuario una vez ha iniciado sesi�n
		 */
		mainW.getBtnCambiarUsuario().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "pg5");

			}
		});

		/*
		 * Bot�n para que el atleta acceda a sus inscripciones.
		 */
		mainW.getBtnIniciarSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg5");

			}
		});
		/**
		 * Implementa la funcionalidad de inicio de sesi�n necesaria para listar
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
								"No se ha encontrado un usuario asociado al correo electr�nico.\n Int�ntalo de nuevo.");
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
		/**
		 * Muestra los atletas de la competici�n de la inscripci�n seleccionada
		 */
		mainW.getBtnMostrarAtletas().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				obtenerAtletas(idCompeticionSeleccionada);
				((CardLayout) mainW.getPanel_perfilAtleta().getLayout())
						.show(mainW.getPanel_perfilAtleta(), "otrosAtletas");

			}
		});

		/*
		 * Boton que despliega las inscripciones que corresponden al atleta.
		 */
		mainW.getBtnMisInscripciones().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarInscripciones();
				mainW.getBtnMisInscripciones().setVisible(false);
			}
		});

		/**
		 * Bot�n para que el atleta se compare con el atleta que ha seleccionado
		 */
		mainW.getBtnCompararse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mostrarComparativa(Integer.valueOf(idCompeticionSeleccionada));
				((CardLayout) mainW.getPanel_perfilAtleta().getLayout())
						.show(mainW.getPanel_perfilAtleta(), "comparacion");
			}
		});

	}

	private void mostrarInscripciones() {

		try {

			// Esta lista mejor de inscripciones

			List<AtletaInscritoDto> inscripciones = incCrud
					.listarInscripcionesAtletaConDto(sesion.getIdAtleta());

			// Esta es la lista del nombre de las columnas
			String[] columnNames = { "ID", "Competici�n", "Estado Inscripcion",
					"Ultimo Cambio" };

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
				valuesToTable[count][col++] = String.valueOf(dto.idCompeticion);

				valuesToTable[count][col++] = dto.nombreCompeticion;
				valuesToTable[count][col++] = dto.estado.toString();
				valuesToTable[count][col++] = dto.fechaUltimoCambio.toString();
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

			mainW.getTableInscripciones().setModel(model);
			// una vez que el atleta hace click en el bot�n de volver, se hace
			// invisible las tablas para que no quede feo al borrarles el
			// modelo. Por eso hago set visible cada vez que creo un modelo
			// nuevo.

			mainW.getTableInscripciones().setVisible(true);
			mainW.getTableInscripciones().getSelectionModel()
					.addListSelectionListener(

							new ListSelectionListener() {

								@Override
								public void valueChanged(

										ListSelectionEvent event) {
									if (mainW.getTableInscripciones()
											.getSelectedRow() >= 0) {
										nombreComp = mainW
												.getTableInscripciones()
												.getValueAt(mainW
														.getTableInscripciones()
														.getSelectedRow(), 1)
												.toString();
										idCompeticionSeleccionada = mainW
												.getTableInscripciones()
												.getValueAt(mainW
														.getTableInscripciones()
														.getSelectedRow(), 0)
												.toString();
										rellenarLasLabelDeNombreDeCompeticion();
										// Solo se permite mostrar los atletas
										// de
										// una competici�n terminada.
										evitarMostrarAtletasDeUnaCompeticionNoTerminada();
										habilitarBotonCancelacion();
									}
								}

							});

		} catch (BusinessException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	/**
	 * Este método habilita el botón de cancelar inscripción, si y solo si el
	 * estado de la misma es inscrito o pre_inscrito(pendiente de pago) Esto
	 * evita el intento de cancelación de inscripciones anuladas, canceladas o
	 * de competiciones terminadas.
	 */
	protected void habilitarBotonCancelacion() {
		String statusInscripcion = mainW.getTableInscripciones()
				.getValueAt(mainW.getTableInscripciones().getSelectedRow(), 2)
				.toString();
		if (statusInscripcion.equals("PRE_INSCRITO")
				|| statusInscripcion.equals("INSCRITO")
				|| statusInscripcion.equals("PENDIENTE_DE_PAGO")) {
			this.mainW.getBtnCancelarInscripcion().setEnabled(true);
		}

	}

	private void rellenarLasLabelDeNombreDeCompeticion() {
		mainW.getLblCompeticionSeleccionada().setText(nombreComp);
		mainW.getLblNombreCompeticion().setText(nombreComp);
		mainW.getLblNombreCompeticion2().setText(nombreComp);
	}

	private void evitarMostrarAtletasDeUnaCompeticionNoTerminada() {
		if (mainW.getTableInscripciones()
				.getValueAt(mainW.getTableInscripciones().getSelectedRow(), 2)
				.equals("TERMINADA")) {
			mainW.getBtnMostrarAtletas().setEnabled(true);
		} else {
			mainW.getBtnMostrarAtletas().setEnabled(false);
		}
	}

	private void obtenerAtletas(String idCompeticion) {

		int id = -1;
		try {
			id = Integer.parseUnsignedInt(idCompeticion);

			List<AtletaInscritoDto> res = incCrud
					.obtenerAtletasParaCompeticion(id);

			String[] columnNames = { "Dorsal", "Nombre", "Apellidos", "sexo",
					"Categor�a" };

			String[][] valuesToTable = new String[res
					.size()][columnNames.length];

			int count = 0;

			for (AtletaInscritoDto atleta : res) {
				// Necesito encontrar el nombre y apellidos del atleta-> uso un
				// atletaDto normal
				AtletaDto dtoAtleta = atlCrud.encontrarPorId(atleta.idAtleta);

				int col = 0;
				valuesToTable[count][col++] = String.valueOf(atleta.dorsal);
				valuesToTable[count][col++] = dtoAtleta.nombre;
				valuesToTable[count][col++] = dtoAtleta.apellido;
				valuesToTable[count][col++] = dtoAtleta.sexo;
				valuesToTable[count][col++] = atleta.categoria;

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
			// una vez que el atleta hace click en el bot�n de volver, se hace
			// invisible las tablas para que no quede feo al borrarles el
			// modelo. Por eso hago set visible cada vez que creo un modelo
			// nuevo.
			mainW.getTableAtletasDeCompSeleccionada().setVisible(true);

			mainW.getTableAtletasDeCompSeleccionada().getSelectionModel()
					.addListSelectionListener(

							new ListSelectionListener() {

								@Override
								public void valueChanged(
										ListSelectionEvent event) {
									if (mainW
											.getTableAtletasDeCompSeleccionada()
											.getSelectedRow() >= 0) {
										guardarDorsalSeleccionado();
										evitarCompararseConUnoMismo();
										rellenarLabelAtletaSeleccionado();
									}
								}

							});
			mainW.getLblErrorPerfil().setVisible(false);
			mainW.getLblErrorPerfil().setText("Error: ");

		} catch (BusinessException e) {
			mainW.getLblErrorPerfil().setVisible(true);
			mainW.getLblErrorPerfil().setText("Error: " + e.getMessage());
		}

	}

	private void guardarDorsalSeleccionado() {
		this.dorsalAtletaSeleccionado = Integer
				.valueOf(mainW.getTableAtletasDeCompSeleccionada()
						.getValueAt(mainW.getTableAtletasDeCompSeleccionada()
								.getSelectedRow(), 0)
						.toString());
	}

	private void rellenarLabelAtletaSeleccionado() {
		String nombre, apellido;
		nombre = mainW.getTableAtletasDeCompSeleccionada().getValueAt(
				mainW.getTableAtletasDeCompSeleccionada().getSelectedRow(), 1)
				.toString();
		apellido = mainW.getTableAtletasDeCompSeleccionada().getValueAt(
				mainW.getTableAtletasDeCompSeleccionada().getSelectedRow(), 2)
				.toString();
		mainW.getLblAtletaSeleccionado().setText(nombre + " " + apellido);
	}

	private void evitarCompararseConUnoMismo() {
		if (Integer.valueOf(sesion.getDorsalAtleta(
				Integer.valueOf(idCompeticionSeleccionada))) == (Integer
						.valueOf(dorsalAtletaSeleccionado))) {
			mainW.getBtnCompararse().setEnabled(false);
		} else {
			// necesario si se selecciona primero a uno mismo y luego da a otro.
			// de no hacerlo se queda bloqueado el boton
			mainW.getBtnCompararse().setEnabled(true);
		}

	}

	private void mostrarComparativa(int id) {

		AtletaInscritoDto current, seleccionado;
		try {

			current = incCrud
					.obtenerAtletaParaComparar(sesion.getDorsalAtleta(id), id);
			AtletaDto currentDto = atlCrud.encontrarPorId(sesion.getIdAtleta());

			seleccionado = incCrud
					.obtenerAtletaParaComparar(dorsalAtletaSeleccionado, id);
			AtletaDto selectDto = atlCrud.encontrarPorId(seleccionado.idAtleta);
			String[] columnNames = { "Nombre", "Apellidos", "Sexo", "Club",
					"Categor�a", "Posici�n final", "Tiempo", "Ritmo(min/km)" };

			String[][] valuesToTable = new String[2][columnNames.length];

			int col = 0;
			valuesToTable[0][col++] = currentDto.nombre;
			valuesToTable[0][col++] = currentDto.apellido;
			valuesToTable[0][col++] = currentDto.sexo;
			if (current.club == null) {
				valuesToTable[0][col++] = "X";
			} else {
				valuesToTable[0][col++] = current.club;
			}

			valuesToTable[0][col++] = categoriaCrud.encontrarCategoriaPorId(
					current.idCategoria).nombreCategoria;
			valuesToTable[0][col++] = String.valueOf(current.posicionFinal);
			valuesToTable[0][col++] = current.tiempoQueTarda.toString();
			valuesToTable[0][col++] = String.valueOf(current.ritmoPorKm);
			col = 0;
			valuesToTable[1][col++] = selectDto.nombre;
			valuesToTable[1][col++] = selectDto.apellido;
			valuesToTable[1][col++] = selectDto.sexo;
			if (seleccionado.club == null) {
				valuesToTable[1][col++] = "X";
			} else {
				valuesToTable[1][col++] = seleccionado.club;
			}
			valuesToTable[1][col++] = categoriaCrud.encontrarCategoriaPorId(
					seleccionado.idCategoria).nombreCategoria;
			valuesToTable[1][col++] = String
					.valueOf(seleccionado.posicionFinal);
			valuesToTable[1][col++] = seleccionado.tiempoQueTarda.toString();
			valuesToTable[1][col++] = String.valueOf(seleccionado.ritmoPorKm);

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

			mainW.getTableComparativa().setModel(model);
			// una vez que el atleta hace click en el bot�n de volver, se hace
			// invisible las tablas para que no quede feo al borrarles el
			// modelo. Por eso hago set visible cada vez que creo un modelo
			// nuevo.
			mainW.getTableComparativa().setVisible(true);
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		}

	}

	void volverAMiPerfil() {
		mainW.resetearCamposYVistasPerfilAtleta();
		((CardLayout) mainW.getPanel_perfilAtleta().getLayout())
				.show(mainW.getPanel_perfilAtleta(), "perfil");

	}

}