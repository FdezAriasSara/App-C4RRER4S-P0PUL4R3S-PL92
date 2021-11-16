package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


import java.sql.Date;


import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;

import java.time.ZoneId;

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
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.Inscripcion.crud.ActualizarEstadoInscripcion;
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
	private InscripcionCrudService incCrud = BusinessFactory.forInscripcionCrudService();
	private Sesion sesion;
	private PagoCrudService pagCrud = BusinessFactory.forPagoCrudService();


	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();
	private RegistroCrudService regCrud = BusinessFactory.forRegistroCrudService();

	private PlazoCrudService plazoCrud = BusinessFactory.forPlazoCrudService();
	private CompeticionCrudService compCrud = BusinessFactory.forCompeticionCrudService();// para saber si hay dorsales
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
			public void actionPerformed(ActionEvent e) {

				iniciarSesion(null);
				mainW.getBtnListarInscripciones().setEnabled(false);// una vez haya iniciado sesión , el atleta puede
																	// acceder a sus inscripciones.
				mainW.vaciarCampoIniciarSesion();

				((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg1");
				mainW.getTableCompeticion().removeAll();
				mainW.getTablaClasificacion().removeAll();
				mainW.getBtnVolverBienvenida().setEnabled(false);
			}
		});

		mainW.getBtnInscribirse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mainW.getLblError().setVisible(false);
				inscribirse(mainW.getTxtFEmail().getText(), mainW.getTableCompeticion().getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString() );
			}
		});

		
		mainW.getBtnObtenerAtletas().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainW.getLblError().setVisible(false);
				obtenerAtletas(mainW.getTablaClasificacion().getModel().getValueAt(mainW.getTablaClasificacion().getSelectedRow(), 0).toString());

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
					if (sesion.getIdAtleta() == Sesion.NO_INICIADO) {
						mainW.mostrarErrorInicioSesion(
								"No se ha encontrado un usuario asociado al correo electrónico.\n Inténtalo de nuevo.");
						mainW.vaciarCampoIniciarSesion();
					} else {
						mainW.getBtnListarInscripciones().setEnabled(true);// una vez haya iniciado sesión , el atleta
																			// puede
						// acceder a sus inscripciones.
						((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg2");

						mainW.vaciarCampoIniciarSesion();
					}

				}

			}
		});

		/*
		 * Boton que despliega las inscripciones que corresponden al atleta.
		 */
		mainW.getBtnListarInscripciones().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					// Esta lista mejor de inscripciones

					List<AtletaInscritoDto> inscripciones = incCrud
							.listarInscripcionesAtletaConDto(sesion.getIdAtleta());

					// Esta es la lista del nombre de las columnas
					String[] columnNames = { "Competición", "Estado Inscripcion", "Ultimo Cambio" };

					// Esta es la array que contiene los elementos a listar, el primer [es el numero
					// de fila]
					// el segundo [el numero de la columna de acuerdo a los datos de arriba]
					String[][] valuesToTable = new String[inscripciones.size()][columnNames.length];

					int count = 0;
					for (AtletaInscritoDto dto : inscripciones) {
						int col = 0;
						valuesToTable[count][col++] = dto.nombreCompeticion;
						valuesToTable[count][col++] = dto.estado.toString();
						valuesToTable[count][col++] = dto.fechaUltimoCambio.toString();
						count++;
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

					mainW.getTableCompeticion().setModel(model);

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
					// comprobar que no haya campos vacíos
					if (mainW.getTxtCVC().getText().isBlank() || mainW.getTxtNum().getText().isBlank())
						JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos");
					else {
						// Sacar datos de la tarjeta-inicio
						int cvc = Integer.valueOf(mainW.getTxtCVC().getText());
						String numeroTarjeta = mainW.getTxtNum().getText();
						LocalDate fechaCaducidad = LocalDate.of(mainW.getYearChooser().getYear(),
								mainW.getMonthChooser().getMonth(), 1);

						TarjetaDto tarjeta = new TarjetaDto(numeroTarjeta, fechaCaducidad, cvc, sesion.getIdAtleta());
						// Sacar datos de la tarjeta-fin

						pagoDto = pagCrud.pagarConTarjeta(pagoDto, tarjeta);
						JOptionPane.showMessageDialog(mainW, "Se ha realizado un pago.\n" + pagoDto.toString());
						((CardLayout) mainW.getPanel_pago().getLayout()).show(mainW.getPanel_pago(), "escogerPago");
						// restauramos el panel

						mainW.vaciarCamposPago();
						((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg2");
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
					CompeticionDto dto = pagCrud.pagarConTransferencia(sesion.getIdAtleta(), sesion.getIdCompeticion());
					JOptionPane.showMessageDialog(mainW,
							"Ingrese en la cuenta " + dto.cuentaBancaria + "\nel importe de " + dto.cuota + " euros");
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, "Aún no se ha inscrito");
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
					LocalDate fechaNacimiento = mainW.getCalendarNacimiento().getDate().toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDate();
					if (!fechaNacimiento.isBefore(LocalDate.parse("2003-12-31")))
						mainW.mostrarErrorRegistro("Debes ser mayor de edad para registrarte.");
					else {
						// recoger datos registro en un dto
						dto.nombre = mainW.getTxtRegNombre().getText();
						dto.apellido = mainW.getTxtRegApellido().getText();
						dto.dni = mainW.getTextFieldDNI().getText();
						dto.fechaNacimiento = Date.valueOf(fechaNacimiento);
						dto.sexo = mainW.getComboSexo().getSelectedItem().toString();
						dto.email = mainW.getTextFieldCorreo().getText();
						// añadir a la base de datos.
						atlCrud.anadirAtleta(dto);

						// una vez que el atleta se registra se le inscribe en la competición que había
						// seleccionado.
						inscribirse(dto.email, mainW.getTableCompeticion().getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString());
						mainW.vaciarCamposRegistro();

					}

				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		/*
		 * Asignación de dorsales
		 */
		mainW.getBtnAsignacionDorsales().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ZoneId defaultZoneId = ZoneId.systemDefault();
				int idCompeticion = Integer.parseInt( mainW.getTablaClasificacion().getModel().getValueAt(mainW.getTablaClasificacion().getSelectedRow(), 0).toString());

				try {
					// si la competición no tiene los plazos terminados-> mostrar error y no enseñar
					// panel dorsales
					Date ultimoPlazo = plazoCrud.getUltimoPlazoByCompeticionId(idCompeticion);
					if (ultimoPlazo.after(Date.valueOf(LocalDate.now()))) {
						mainW.setErrorOrgPlazosSinTerminar();
					} else {
						int dorsalesReservados = compCrud.dorsalesReservados(idCompeticion);
						if (dorsalesReservados == 0) {
							// tenga o no dorsales reservados,se hará asignación de los dorsales no
							// reservados
							// esto lo hago aquiporque sino, aunque se aborte la asignacion manual de
							// dorsales la automatica seguía
							incCrud.asignarDorsalesNoReservados(Integer.parseInt( mainW.getTablaClasificacion().getModel().getValueAt(mainW.getTablaClasificacion().getSelectedRow(), 0).toString()));
							JOptionPane.showMessageDialog(null,
									"Se han asignado los dorsales de la competición con id " + idCompeticion);
							((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg3");
						} else {

							// si la competición teen dorsales reservados-> mostrar panel para reservarlos
							inicializarTablaAsignacionReservas(dorsalesReservados);
							((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "dorsales");
						}
					}

				}

				catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});

		mainW.getBtnAsignar().addActionListener(new ActionListener() {
			boolean parar = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int idCompeticion = Integer.parseInt( mainW.getTableCompeticion().getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString());
					int dorsales = compCrud.dorsalesReservados(idCompeticion);

					List<String> emails = new ArrayList<>();
					for (int fila = 0; fila < dorsales - 1; fila++) {
						// comprobaciones sobre los emails introducidos
						String email = mainW.getTableAsignar().getValueAt(fila, 1).toString().trim();

						if (email.isBlank() || email.isEmpty()) {
							JOptionPane.showMessageDialog(null, "No puedes dejar dorsales sin reservar.");
							parar = true;
							break;
						}
						if (!mainW.checkFormatoEmail(email)) {
							JOptionPane.showMessageDialog(null,
									"El formato del email:" + email + " es incorrecto. Revíselo porfavor.");
							parar = true;
							break;
						}
						if (atlCrud.encontrarPorEmail(email) == null) {
							JOptionPane.showMessageDialog(null, "No se encuentra el atleta con email: " + email);
							parar = true;
							break;
						}
						emails.add(email);
					}
					if (!parar) {
						int dorsal = 1;
						for (String mail : emails) {
							incCrud.asignarDorsalReservado(mail, dorsal, idCompeticion);
							dorsal++;
						}

						incCrud.asignarDorsalesNoReservados(Integer.parseInt( mainW.getTableCompeticion().getModel().getValueAt(mainW.getTableCompeticion().getSelectedRow(), 0).toString()));
						JOptionPane.showMessageDialog(null,
								"Se han asignado los dorsales de la competición con id " + idCompeticion);
						((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg3");

					}

				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});


		mainW.getBtnImportarDatos().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = mainW.getTxtArchivoTiempos().getText();

				try {
					List<InscripcionDto> insc = ParseadorTiempos.parse(filename);
					int notUpdated = incCrud.registrarTiempos(insc);

					System.out.println("Dorsales erroneos: " + notUpdated);
					System.out.println("Dorsales repetidos: " + ParseadorTiempos.getRepeatedInLastExe());
					System.out.println("Formato erroneo en linea: " + ParseadorTiempos.getWrongParseInLastExe());
					JOptionPane.showMessageDialog(null, "Datos importados correctamente");

				} catch (IOException e1) {
					mainW.getLblError().setVisible(true);
					mainW.getLblError().setEnabled(true);
					mainW.getLblError().setText("Error al encontrar archivo de datos");

					mainW.getLblError().setEnabled(false);
				} catch (BusinessException e1) {
					mainW.getLblError().setVisible(true);
					mainW.getLblError().setEnabled(true);
					mainW.getLblError().setText("Error al exportar datos a la base de datos");

					mainW.getLblError().setEnabled(false);
				}

			}
		});
		
		
		
		mainW.getTablaClasificacion().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				
				if (mainW.getTableCompeticion().getSelectedRow() >= 0) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error: ID vacío");
					
					mainW.getBtnCargarCategorias().setEnabled(false);
					mainW.getBtnGenerarClasificacion().setEnabled(false);
					mainW.getBtnObtenerAtletas().setEnabled(false);
					return;
				}
				
				int id = -1;

				try {
					id = Integer.parseUnsignedInt(mainW.getTablaClasificacion().getModel().getValueAt(mainW.getTablaClasificacion().getSelectedRow(), 0).toString());
					currentIdCompeticon = id;
					mainW.getBtnCargarCategorias().setEnabled(true);
					mainW.getBtnGenerarClasificacion().setEnabled(true);
					mainW.getBtnObtenerAtletas().setEnabled(true);
				} catch (ArrayIndexOutOfBoundsException e1) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vacío o menor que 0");
					
					mainW.getBtnCargarCategorias().setEnabled(false);
					mainW.getBtnGenerarClasificacion().setEnabled(false);
					mainW.getBtnObtenerAtletas().setEnabled(false);
					return;
				}
				
			}
		});
		
		
		mainW.getBtnCargarCategorias().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainW.getCbCategoria().removeAllItems();
				añadirCategoriasAComboBox(currentIdCompeticon);
			}
			
		});
		
		
		mainW.getTablaClasificacion().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				mainW.getBtnGenerarClasificacion().setEnabled(false);
			}
		});
		
		
		mainW.getBtnGenerarClasificacion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentCategoriasInComboBox.isEmpty()) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error:Por favor, pulsa en cargar categorias antes de generar la clasificacion");
					return;
				}
				
				if(currentIdCompeticon < 0) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error: indique el numero de competicion");
					return;
				}
				
				mainW.getLblError().setVisible(false);
				int catSelectedCB = mainW.getCbCategoria().getSelectedIndex() - 1; //Resto 1 porque primera categoría es ABSOLUTA (generar todas las clasi)
				int idCategoriaSelected;
				
				if(catSelectedCB < 0) {
					idCategoriaSelected = -1;
				}else {
					idCategoriaSelected = currentCategoriasInComboBox.get(catSelectedCB).idCategoria;
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
					DefaultTableModel modelo = (DefaultTableModel) mainW.getTablaClasificacion().getModel();
					ActualizarEstadoInscripcion aEi = new ActualizarEstadoInscripcion(
							Integer.parseInt((String) modelo.getValueAt(fila, 0)));
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
					mainW.getLblErrorOrg().setText("Error: selecciona competición para cargar pagos");
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setEnabled(true);
				}
			}
		});
		
		
		
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
	
	
	

	
	
	

	
	private void añadirCategoriasAComboBox(int idCompeticion) {
		
		
		
		try {
			this.currentCategoriasInComboBox = compCrud.listarCompeticionesConSusCategorias(idCompeticion);
			
			mainW.getCbCategoria().addItem("ABSOLUTA");
			for(CompeticionCategoriaDto dto :  currentCategoriasInComboBox) {
				mainW.getCbCategoria().addItem(dto.nombreCategoría);
			}
			
			
		} catch (BusinessException e) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			return;
		}
		
		
	}
	
	
	private void obtenerClasificacion(int idCompeticion, int idCategoria) {

		
		if(currentIdCompeticon < 0) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: indique el numero de competicion");
			return;
		}
		
		List<InscripcionDto> clas = new ArrayList<InscripcionDto>();
			
		try {
			
			if( idCategoria < 0) {//generar TODAS las clasificaciones
				
				for(CompeticionCategoriaDto compCat : currentCategoriasInComboBox) {
					
					clas.addAll(incCrud.obtenerClasificaciones(idCompeticion, compCat.idCategoria));
					
				}
				
			}else { //Generar solo categoria seleccionada
				clas = incCrud.obtenerClasificaciones(idCompeticion, idCategoria);
			}
			
		
			String[] columnNames = { "Nombre", "Apellidos", "Posicion", "Tiempo","Categoria" };

			String[][] valuesToTable = new String[clas.size()][columnNames.length];

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

				count++;
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
			mainW.getTablaClasificacion().setModel(model);

		} catch (BusinessException e) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
		}
	}
	
	

	private void obtenerAtletas(String idCompeticion) {
		if (idCompeticion.isBlank() || idCompeticion.isEmpty()) {
			mainW.getLblErrorOrg().setVisible(true);
			mainW.getLblErrorOrg().setText("Error: ID vacío");
		} else {
			int id = -1;
			try {
				id = Integer.parseUnsignedInt(idCompeticion);

				List<AtletaInscritoDto> res = incCrud.obtenerAtletasParaCompeticion(id);

				String[] columnNames = { "Nombre", "Apellidos", "Categoría", "Fecha de Inscripcion", "Estado" };

				String[][] valuesToTable = new String[res.size()][columnNames.length];

				int count = 0;
				for (AtletaInscritoDto dto : res) {
					int col = 0;
					valuesToTable[count][col++] = dto.nombre;
					valuesToTable[count][col++] = dto.apellido;
					valuesToTable[count][col++] = dto.categoria;
					valuesToTable[count][col++] = dto.fechaInscripcion.toString();
					valuesToTable[count][col++] = dto.estado.toString();
					count++;
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

				mainW.getTablaClasificacion().setModel(model);

				mainW.getLblErrorOrg().setVisible(false);
				mainW.getLblErrorOrg().setText("Error: ");

			} catch (BusinessException e) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: " + e.getMessage());
			} catch (NumberFormatException e1) {
				mainW.getLblErrorOrg().setVisible(true);
				mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vacío o menor que 0");
			}
		}

	}

	

	private void inscribirse(String emailAtleta, String idCompeticionString) {
		if (emailAtleta.isBlank() || emailAtleta.isEmpty() || idCompeticionString.isBlank()
				|| idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Algún campo está vacio");
		} else {
			try {
				if (comprobarSiEstaRegistrado(emailAtleta)) {
					int idCompeticion = Integer.parseInt(idCompeticionString);
					incCrud.inscribirAtleta(emailAtleta, idCompeticion);
					JOptionPane.showMessageDialog(null, "Atleta Inscrito");
					mainW.getLblError().setVisible(false);
					sesion = new Sesion(emailAtleta, idCompeticion);
					((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg4");
					
				} else {// si el atleta no está registrado.
					mainW.getTextFieldCorreo().setText(mainW.getTxtFEmail().getText());
					// si el atleta decide registrarse, se habrá introducido ya el correo para su
					// comodidad
					mainW.getRegistroDialog().setVisible(true);
				}

			} catch (BusinessException e) {
				mainW.getLblError().setText("Error: " + e.getMessage());
				mainW.getLblError().setVisible(true);
			}

		}

	}

	private boolean comprobarSiEstaRegistrado(String emailAtleta) {
		if (!regCrud.ComprobarDatosInscripcion(emailAtleta)) {
			mainW.setErrorAtletaNoRegistrado();

			return false;
		}
		return true;

	}
}
