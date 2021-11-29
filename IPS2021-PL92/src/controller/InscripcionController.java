package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Sesion;
import uo.ips.application.business.Inscripcion.AtletaInscritoDto;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.Inscripcion.crud.ActualizarEstadoInscripcion;
import uo.ips.application.business.arco.ArcoDto;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.competicion.CompeticionCategoriaDto;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;
import uo.ips.application.business.plazo.PlazoCrudService;
import uo.ips.application.business.registro.RegistroCrudService;

public class InscripcionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory
			.forInscripcionCrudService();
	private Sesion sesion;
	private PagoCrudService pagCrud = BusinessFactory.forPagoCrudService();

	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();

	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();
	private RegistroCrudService regCrud = BusinessFactory
			.forRegistroCrudService();

	private PlazoCrudService plazoCrud = BusinessFactory.forPlazoCrudService();
	// reservados

	private List<CompeticionCategoriaDto> currentCategoriasInComboBox;
	private int currentIdCompeticon = -1;

	public InscripcionController(MainWindow main) {
		this.mainW = main;
		iniciarSesion(sesion);
		this.initActions();

	}

	private void iniciarSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	private void initActions() {

		mainW.getBtnVolverBienvenida().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				iniciarSesion(null);

				mainW.getBtnMisInscripciones().setEnabled(false);// una vez haya
																	// iniciado
																	// sesinn ,
																	// el atleta
																	// puede
																	// acceder a
																	// sus
																	// inscripciones.

				mainW.vaciarCampoIniciarSesion();

				((CardLayout) mainW.getPanel_card().getLayout())
						.show(mainW.getPanel_card(), "Pg1");
				mainW.getTableCompeticion().removeAll();
				mainW.getTablaClasificacion().removeAll();
				mainW.resetearCamposYVistasPerfilAtleta();
				mainW.getBtnVolverBienvenida().setEnabled(false);

			}
		});

		mainW.getBtnInscribirse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mainW.getLblError().setVisible(false);

				inscribirse(mainW.getTxtFEmail().getText(),
						mainW.getTableCompeticion().getModel()
								.getValueAt(mainW.getTableCompeticion()
										.getSelectedRow(), 0)
								.toString(),
						Integer.parseInt(
								mainW.getTableCompeticion().getModel()
										.getValueAt(mainW.getTableCompeticion()
												.getSelectedRow(), 6)
										.toString()));

			}
		});

		mainW.getBtnObtenerAtletas().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.getLblError().setVisible(false);
				obtenerAtletas(mainW.getTablaClasificacion().getModel()
						.getValueAt(
								mainW.getTablaClasificacion().getSelectedRow(),
								0)
						.toString());

			}

		});

		/*
		 * Metodo para cambiar al panel de pago con tarjeta.
		 */
		mainW.getBtTarjeta_1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				((CardLayout) mainW.getPanel_pago().getLayout())
						.show(mainW.getPanel_pago(), "tarjeta");
			}

		});
		/*
		 * Mntodo para EFECTUAR el pago tras meter datos de tarjeta bancaria.
		 */
		mainW.getBtnPagarTarjeta2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idAtleta = sesion.getIdAtleta();
				int idCompeticion = Integer.parseInt(mainW.getTableCompeticion()
						.getModel()

						.getValueAt(
								mainW.getTableCompeticion().getSelectedRow(), 0)
						.toString());
				String nombreCompeticion = mainW.getTableCompeticion()
						.getModel()

						.getValueAt(
								mainW.getTableCompeticion().getSelectedRow(), 1)
						.toString();
				try {

					PagoDto pagoDto = new PagoDto(LocalDate.now(),
							sesion.getIdAtleta(), sesion.getIdCompeticion());

					// comprobar que no haya campos vacnos
					if (mainW.getTxtCVC().getText().isBlank()
							|| mainW.getTxtNum().getText().isBlank())
						JOptionPane.showMessageDialog(null,
								"Debes rellenar todos los campos");

					else {
						// Sacar datos de la tarjeta-inicio
						int cvc = Integer.valueOf(mainW.getTxtCVC().getText());
						String numeroTarjeta = mainW.getTxtNum().getText();
						LocalDate fechaCaducidad = LocalDate.of(
								mainW.getYearChooser().getYear(),
								mainW.getMonthChooser().getMonth() + 1, 1);

						TarjetaDto tarjeta = new TarjetaDto(numeroTarjeta,
								fechaCaducidad, cvc, idAtleta);
						// Sacar datos de la tarjeta-fin

						PagoDto justificante = pagCrud.pagarConTarjeta(idAtleta,
								idCompeticion, tarjeta);
						JOptionPane.showMessageDialog(mainW,
								"Se ha realizado el pago de su inscripción.\n"
										+ justificante.toString() + "para '"
										+ nombreCompeticion + "'");
						((CardLayout) mainW.getPanel_pago().getLayout())
								.show(mainW.getPanel_pago(), "escogerPago");
						// restauramos el panel

						mainW.vaciarCamposPago();
						((CardLayout) mainW.getPanel_card().getLayout())
								.show(mainW.getPanel_card(), "Pg2");
						// Volvemos al panel principal.
					}

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
					CompeticionDto dto = pagCrud.pagarConTransferencia(
							sesion.getIdAtleta(), sesion.getIdCompeticion());
					JOptionPane.showMessageDialog(mainW,
							"Ingrese en la cuenta " + dto.cuentaBancaria
									+ "\nel importe de " + dto.cuota
									+ " euros");
				} catch (BusinessException e1) {

					JOptionPane.showMessageDialog(null,
							"Ann no se ha inscrito");

				}
			}
		});
		/**
		 * Efectua el registro del atleta
		 */
		mainW.getBtnRegistrarse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					AtletaDto dto = new AtletaDto();
					// si el atleta es mayor de edad
					LocalDate fechaNacimiento = mainW.getCalendarNacimiento()
							.getDate().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
					if (!fechaNacimiento
							.isBefore(LocalDate.parse("2003-12-31")))
						mainW.mostrarErrorRegistro(
								"Debes ser mayor de edad para registrarte.");
					else {
						// recoger datos registro en un dto
						dto.nombre = mainW.getTxtRegNombre().getText();
						dto.apellido = mainW.getTxtRegApellido().getText();
						dto.dni = mainW.getTextFieldDNI().getText();
						dto.fechaNacimiento = Date.valueOf(fechaNacimiento);
						dto.sexo = mainW.getComboSexo().getSelectedItem()
								.toString();
						dto.email = mainW.getTextFieldCorreo().getText();
						// anadir a la base de datos.
						atlCrud.anadirAtleta(dto);

						// una vez que el atleta se registra se le inscribe en
						// la competicinn que habna

						// seleccionado.
						inscribirse(dto.email,
								mainW.getTableCompeticion().getModel()

										.getValueAt(mainW.getTableCompeticion()
												.getSelectedRow(), 0)
										.toString(),
								Integer.parseInt(mainW.getTableCompeticion()
										.getModel()
										.getValueAt(mainW.getTableCompeticion()
												.getSelectedRow(), 6)
										.toString()));

						mainW.vaciarCamposRegistro();

					}

				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		/*
		 * Asignacinn de dorsales
		 */
		mainW.getBtnAsignacionDorsales()
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ZoneId defaultZoneId = ZoneId.systemDefault();
						int idCompeticion = Integer.parseInt(mainW
								.getTablaClasificacion().getModel()
								.getValueAt(mainW.getTablaClasificacion()
										.getSelectedRow(), 0)
								.toString());

						try {
							// si la competicinn no tiene los plazos
							// terminados-> mostrar error y no ensenar
							// panel dorsales
							Date ultimoPlazo = plazoCrud
									.getUltimoPlazoByCompeticionId(
											idCompeticion);
							if (ultimoPlazo
									.after(Date.valueOf(LocalDate.now()))) {
								mainW.setErrorOrgPlazosSinTerminar();
							} else {
								int dorsalesReservados = compCrud
										.dorsalesReservados(idCompeticion);
								if (dorsalesReservados == 0) {
									// tenga o no dorsales reservados,se harn
									// asignacinn de los dorsales no
									// reservados
									// esto lo hago aquiporque sino, aunque se
									// aborte la asignacion manual de
									// dorsales la automatica seguna
									incCrud.asignarDorsalesNoReservados(
											Integer.parseInt(mainW
													.getTablaClasificacion()
													.getModel()
													.getValueAt(mainW
															.getTablaClasificacion()
															.getSelectedRow(),
															0)
													.toString()));
									JOptionPane.showMessageDialog(null,
											"Se han asignado los dorsales de la competicinn con id "
													+ idCompeticion);
									((CardLayout) mainW.getPanel_card()
											.getLayout()).show(
													mainW.getPanel_card(),
													"Pg3");
								} else {

									// si la competicinn teen dorsales
									// reservados-> mostrar panel para
									// reservarlos
									inicializarTablaAsignacionReservas(
											dorsalesReservados);
									((CardLayout) mainW.getPanel_card()
											.getLayout()).show(
													mainW.getPanel_card(),
													"dorsales");

								}
							}
						} catch (BusinessException e1) {
							JOptionPane.showMessageDialog(null,
									e1.getMessage());
						}

					}
				});

		mainW.getBtnAsignar().addActionListener(new ActionListener() {
			boolean parar = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int idCompeticion = Integer.parseInt(mainW
							.getTableCompeticion().getModel().getValueAt(mainW
									.getTableCompeticion().getSelectedRow(), 0)
							.toString());
					int dorsales = compCrud.dorsalesReservados(idCompeticion);

					List<String> emails = new ArrayList<>();
					for (int fila = 0; fila < dorsales - 1; fila++) {
						// comprobaciones sobre los emails introducidos
						String email = mainW.getTableAsignar()
								.getValueAt(fila, 1).toString().trim();

						if (email.isBlank() || email.isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"No puedes dejar dorsales sin reservar.");
							parar = true;
							break;
						}
						if (!mainW.checkFormatoEmail(email)) {
							JOptionPane.showMessageDialog(null,

									"El formato del email:" + email
											+ " es incorrecto. Revnselo porfavor.");

							parar = true;
							break;
						}
						if (atlCrud.encontrarPorEmail(email) == null) {
							JOptionPane.showMessageDialog(null,
									"No se encuentra el atleta con email: "
											+ email);
							parar = true;
							break;
						}
						emails.add(email);
					}
					if (!parar) {
						int dorsal = 1;
						for (String mail : emails) {
							incCrud.asignarDorsalReservado(mail, dorsal,
									idCompeticion);
							dorsal++;
						}

						incCrud.asignarDorsalesNoReservados(Integer
								.parseInt(mainW.getTableCompeticion().getModel()
										.getValueAt(mainW.getTableCompeticion()
												.getSelectedRow(), 0)
										.toString()));
						JOptionPane.showMessageDialog(null,

								"Se han asignado los dorsales de la competicinn con id "
										+ idCompeticion);
						((CardLayout) mainW.getPanel_card().getLayout())
								.show(mainW.getPanel_card(), "Pg3");

					}

				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
//
		mainW.getBtnImportarDatos().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = mainW.getTxtArchivoTiempos().getText();

				try {
					JFileChooser fileChooser = new JFileChooser();
					int seleccion = fileChooser
							.showOpenDialog(mainW.getPnEscogerPago());

					if (seleccion == JFileChooser.APPROVE_OPTION) {
						File fichero = fileChooser.getSelectedFile();
						List<InscripcionDto> insc = ParseadorTiempos
								.parse(fichero.getAbsolutePath());
						int notUpdated = incCrud.registrarTiempos(insc);
						System.out.println("Dorsales erroneos: " + notUpdated);
						System.out.println("Dorsales repetidos: "
								+ ParseadorTiempos.getRepeatedInLastExe());
						System.out.println("Formato erroneo en linea: "
								+ ParseadorTiempos.getWrongParseInLastExe());
						JOptionPane.showMessageDialog(null,
								"Datos importados correctamente\n Dorsales erroneos: "
										+ notUpdated + "\n Dorsales repetidos: "
										+ ParseadorTiempos
												.getRepeatedInLastExe());

					}

				} catch (IOException e1) {
					mainW.getLblError().setVisible(true);
					mainW.getLblError().setEnabled(true);
					mainW.getLblError()
							.setText("Error al encontrar archivo de datos");

					mainW.getLblError().setEnabled(false);
				} catch (BusinessException e1) {
					mainW.getLblError().setVisible(true);
					mainW.getLblError().setEnabled(true);
					mainW.getLblError().setText(
							"Error al exportar datos a la base de datos");

					mainW.getLblError().setEnabled(false);
				}

			}
		});

		mainW.getTablaClasificacion().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				if (mainW.getTableCompeticion().getSelectedRow() >= 0) {
					mainW.getLblErrorOrg().setVisible(true);

					mainW.getLblErrorOrg().setText("Error: ID vacno");

					mainW.getBtnCargarCategorias().setEnabled(false);
					mainW.getBtnGenerarClasificacion().setEnabled(false);
					mainW.getBtnObtenerAtletas().setEnabled(false);
					return;
				}

				int id = -1;

				try {
					id = Integer.parseUnsignedInt(mainW.getTablaClasificacion()
							.getModel().getValueAt(mainW.getTablaClasificacion()
									.getSelectedRow(), 0)
							.toString());
					currentIdCompeticon = id;
					mainW.getBtnCargarCategorias().setEnabled(true);
					mainW.getBtnGenerarClasificacion().setEnabled(true);
					mainW.getBtnObtenerAtletas().setEnabled(true);
				} catch (ArrayIndexOutOfBoundsException e1) {
					mainW.getLblErrorOrg().setVisible(true);

					mainW.getLblErrorOrg().setText(
							"Error: ID de competicion no numerico, vacno o menor que 0");

					// mainW.getTxtIDCompOrg().setText("");
					mainW.getBtnCargarCategorias().setEnabled(false);
					mainW.getBtnGenerarClasificacion().setEnabled(false);
					mainW.getBtnObtenerAtletas().setEnabled(false);
					return;
				}

			}
		});
		mainW.getBtnCargarCategorias().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.getCbCategoria().removeAllItems();
				anadirCategoriasAComboBox(currentIdCompeticon);
			}

		});

		mainW.getTablaClasificacion().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

				mainW.getBtnGenerarClasificacion().setEnabled(false);
			}
		});

		mainW.getBtnGenerarClasificacion()
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (currentCategoriasInComboBox.isEmpty()) {
							mainW.getLblErrorOrg().setVisible(true);
							mainW.getLblErrorOrg().setText(
									"Error:Por favor, pulsa en cargar categorias antes de generar la clasificacion");
							return;
						}

						if (currentIdCompeticon < 0) {
							mainW.getLblErrorOrg().setVisible(true);
							mainW.getLblErrorOrg().setText(
									"Error: indique el numero de competicion");
							return;
						}

						mainW.getLblError().setVisible(false);
						int catSelectedCB = mainW.getCbCategoria()
								.getSelectedIndex() - 1; // Resto 1 porque
															// primera categorna
															// es ABSOLUTA
															// (generar todas
															// las clasi)

						int idCategoriaSelected;

						if (catSelectedCB < 0) {
							idCategoriaSelected = -1;
						} else {
							idCategoriaSelected = currentCategoriasInComboBox
									.get(catSelectedCB).idCategoria;
						}

						obtenerClasificacion(currentIdCompeticon,
								idCategoriaSelected);
					}
				});

		mainW.getBtCargarPagos().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = mainW.getTablaClasificacion().getSelectedRow();
				if (fila > -1) {
					DefaultTableModel modelo = (DefaultTableModel) mainW
							.getTablaClasificacion().getModel();
					ActualizarEstadoInscripcion aEi = new ActualizarEstadoInscripcion(
							Integer.parseInt(
									(String) modelo.getValueAt(fila, 0)));
					try {
						aEi.execute();
						mainW.getLblErrorOrg().setVisible(false);
						mainW.getLblErrorOrg().setEnabled(false);
						JOptionPane.showMessageDialog(mainW,
								"Pagos cargados correctamente e inscripciones actualizadas");
					} catch (BusinessException e1) {
						mainW.getLblErrorOrg().setText(e1.getMessage());
						mainW.getLblErrorOrg().setVisible(true);
						mainW.getLblErrorOrg().setEnabled(true);
					}
				} else {

					mainW.getLblErrorOrg().setText(
							"Error: selecciona competicinn para cargar pagos");

					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setEnabled(true);
				}
			}
		});
	}

	public void initFiltro(List<ArcoDto> arcos) {
		mainW.getPnFiltro().removeAll();
		int count = 1;
		for (ArcoDto arco : arcos) {
			String name = arco.name != null ? arco.name
					: "Punto de control " + count;
			count++;
			JCheckBox ch = new JCheckBox(arco.name);
			mainW.getPnFiltro().add(ch);
			mainW.getPnFiltro().repaint();
		}
	}

	protected void inicializarTablaAsignacionReservas(int dorsales) {
		String[] columnNames = { "Dorsal", "Email Atleta" };

		String[][] valuesToTable = new String[dorsales][2];

		for (int i = 0; i < dorsales; i++) {

			valuesToTable[i][0] = String.valueOf(i + 1);
			valuesToTable[i][1] = "";
		}

		TableModel model = new DefaultTableModel(valuesToTable, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				switch (column) {
				case 0:
					return false;
				case 1:
					return true;
				default:
					return false;
				}
			}
		};

		mainW.getTableAsignar().setModel(model);

	}

	private void anadirCategoriasAComboBox(int idCompeticion) {

		try {
			this.currentCategoriasInComboBox = compCrud
					.listarCompeticionesConSusCategorias(idCompeticion);

			mainW.getCbCategoria().addItem("ABSOLUTA");
			for (CompeticionCategoriaDto dto : currentCategoriasInComboBox) {
				mainW.getCbCategoria().addItem(dto.nombreCategoria

				);
			}

		} catch (BusinessException e) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			return;
		}

	}

	private void obtenerClasificacion(int idCompeticion, int idCategoria) {

		if (currentIdCompeticon < 0) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg()
					.setText("Error: indique el numero de competicion");
			return;
		}
		initFiltro(BusinessFactory.forArco().getArcos(idCompeticion));

		List<InscripcionDto> clas = new ArrayList<InscripcionDto>();

		try {

			if (idCategoria < 0) {// generar TODAS las clasificaciones

				for (CompeticionCategoriaDto compCat : currentCategoriasInComboBox) {

					clas.addAll(incCrud.obtenerClasificaciones(idCompeticion,
							compCat.idCategoria));

				}

			} else { // Generar solo categoria seleccionada
				clas = incCrud.obtenerClasificaciones(idCompeticion,
						idCategoria);
			}

			String[] columnNames = { "Nombre", "Apellidos", "Posicion",
					"Tiempo", "Categoria", "Club" };

			String[][] valuesToTable = new String[clas
					.size()][columnNames.length];

			int count = 0;
			AtletaDto atleta;
			for (InscripcionDto dto : clas) {
				atleta = atlCrud.encontrarPorId(dto.idAtleta);
				int col = 0;
				valuesToTable[count][col++] = atleta.nombre;
				valuesToTable[count][col++] = atleta.apellido;

				if (dto.posicionFinal <= 0) {
					valuesToTable[count][col++] = "-";
				} else {
					valuesToTable[count][col++] = "" + dto.posicionFinal;
				}

				if (dto.tiempoQueTardaEnSegundos <= 0) {
					valuesToTable[count][col++] = "--:--:--";
				} else {
					valuesToTable[count][col++] = "" + dto.tiempoQueTarda;
				}

				valuesToTable[count][col++] = dto.nombreCategoria;
				valuesToTable[count][col++] = dto.club;

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
			mainW.getTablaClasificacion().setModel(model);

		} catch (BusinessException e) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
		}
	}

	private void obtenerAtletas(String idCompeticion) {
		if (idCompeticion.isBlank() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacno");
		} else {
			int id = -1;
			try {
				id = Integer.parseUnsignedInt(idCompeticion);

				List<AtletaInscritoDto> res = incCrud
						.obtenerAtletasParaCompeticion(id);

				String[] columnNames = { "Nombre", "Apellidos", "Categorna",
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

				mainW.getTablaClasificacion().setModel(model);

				mainW.getLblErrorOrg().setVisible(false);
				mainW.getLblErrorOrg().setText("Error: ");

			} catch (BusinessException e) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			} catch (NumberFormatException e1) {
				mainW.getLblErrorOrg().setVisible(true);

				mainW.getLblErrorOrg().setText(
						"Error: ID de competicion no numerico, vacno o menor que 0");

			}
		}

	}

	void inscribirse(String emailAtleta, String idCompeticionString,
			int plazas) {

		if (emailAtleta.isBlank() || emailAtleta.isEmpty()
				|| idCompeticionString.isBlank()

				|| idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Algún campo está vacio");
		} else {
			int idCompeticion = Integer.parseInt(idCompeticionString);

			mostrarParaQueCompeticionEstasInscribiendote();

			try {
				if (comprobarSiEstaRegistrado(emailAtleta)) {

					if (plazas > 0) {
						incCrud.inscribirAtleta(emailAtleta, idCompeticion);
						pasarAPagos(emailAtleta, idCompeticion);
						JOptionPane.showMessageDialog(null, "Atleta Inscrito");
					} else {
						if (incCrud.tieneListaEspera(idCompeticion)) {
							int confirmado = JOptionPane.showConfirmDialog(
									mainW,
									"La competición está llena, ¿Quieres apuntarte en la lista de espera?");

							if (JOptionPane.OK_OPTION == confirmado) {
								int posListaEspera = incCrud
										.inscribirAtletaListaEspera(emailAtleta,
												idCompeticion);
								JOptionPane.showMessageDialog(mainW,
										"Atleta inscrito en lista de espera en posicion "
												+ posListaEspera
												+ " a fecha de: "
												+ LocalDate.now().format(
														DateTimeFormatter
																.ofLocalizedDate(
																		FormatStyle.SHORT)));
								mainW.getLblError().setVisible(false);
								pasarAPagos(emailAtleta, idCompeticion);
							}
						} else {
							JOptionPane.showMessageDialog(mainW,
									"La competición está llena y no permite lista de espera",
									null, JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {// si el atleta no está registrado.
					mainW.getTextFieldCorreo()
							.setText(mainW.getTxtFEmail().getText());
					// si el atleta decide registrarse, se habrá introducido ya
					// el correo para su
					mainW.getRegistroDialog().setVisible(true);
					incCrud.inscribirAtleta(emailAtleta, idCompeticion);

					JOptionPane.showMessageDialog(null, "Atleta Inscrito");
					mainW.getLblError().setVisible(false);
					sesion = new Sesion(emailAtleta, idCompeticion);

					((CardLayout) mainW.getPanel_card().getLayout())
							.show(mainW.getPanel_card(), "Pg4");
				}

			} catch (BusinessException e) {
				mainW.getLblError().setText("Error: " + e.getMessage());
				mainW.getLblError().setVisible(true);
			}

		}

	}

	private void mostrarParaQueCompeticionEstasInscribiendote() {
		String nombreCompeticion = mainW.getTableCompeticion().getModel()

				.getValueAt(mainW.getTableCompeticion().getSelectedRow(), 1)
				.toString();
		mainW.getLblPagoInscripcion().setText(
				String.format("Inscripción en '%s'", nombreCompeticion));
	}

	private boolean comprobarSiEstaRegistrado(String emailAtleta) {
		if (!regCrud.ComprobarDatosInscripcion(emailAtleta)) {
			mainW.setErrorAtletaNoRegistrado();

			return false;
		}
		return true;

	}

	private void pasarAPagos(String emailAtleta, int idCompeticion) {
		mainW.getLblError().setVisible(false);
		sesion = new Sesion(emailAtleta, idCompeticion);
		((CardLayout) mainW.getPanel_card().getLayout())
				.show(mainW.getPanel_card(), "Pg4");
	}

}