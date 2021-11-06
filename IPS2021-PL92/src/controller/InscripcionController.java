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
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.competicion.CompeticionCategoriaDto;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;
import uo.ips.application.business.registro.RegistroCrudService;

public class InscripcionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory.forInscripcionCrudService();
	private Sesion sesion;
	private PagoCrudService pagCrud = BusinessFactory.forPagoCrudService();

	private CompeticionCrudService compCrud = BusinessFactory.forCompeticionCrudService();

	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();
	private RegistroCrudService regCrud=BusinessFactory.forRegistroCrudService();

	

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
				mainW.getBtnListarInscripciones().setEnabled(false);// una vez haya iniciado sesi�n , el atleta puede
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
				inscribirse(mainW.getTxtFEmail().getText(), mainW.getTxtFIDCompeticion().getText());
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
		 * Bot�n para que el atleta acceda a sus inscripciones.
		 */
		mainW.getBtnIniciarSesion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg5");

			}
		});
		/**
		 * Implementa la funcionalidad de inicio de sesi�n necesaria para listar las
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
								"No se ha encontrado un usuario asociado al correo electr�nico.\n Int�ntalo de nuevo.");
						mainW.vaciarCampoIniciarSesion();
					} else {
						mainW.getBtnListarInscripciones().setEnabled(true);// una vez haya iniciado sesi�n , el atleta
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
					String[] columnNames = { "Competici�n", "Estado Inscripcion", "Ultimo Cambio" };

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
		 * M�todo para EFECTUAR el pago tras meter datos de tarjeta bancaria.
		 */
		mainW.getBtnPagarTarjeta2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PagoDto pagoDto = new PagoDto(LocalDate.now(), sesion.getIdAtleta(), sesion.getIdCompeticion());
					// comprobar que no haya campos vac�os
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
					JOptionPane.showMessageDialog(null, "A�n no se ha inscrito");
				}
			}
		});

		mainW.getBtnRegistrarse().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			try {
		
				AtletaDto dto= new AtletaDto();
				//si el atleta es mayor de edad
				LocalDate fechaNacimiento=mainW.getCalendarNacimiento().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if(!fechaNacimiento.isBefore(LocalDate.parse("2003-12-31")))
					mainW.mostrarErrorRegistro("Debes ser mayor de edad para registrarte.");
				else {
					//recoger datos registro en un dto
					dto.nombre=mainW.getTxtRegNombre().getText();
					dto.apellido=mainW.getTxtRegApellido().getText();
					dto.dni=mainW.getTextFieldDNI().getText();
					dto.fechaNacimiento=Date.valueOf(fechaNacimiento);
					dto.sexo=mainW.getComboSexo().getSelectedItem().toString();
					dto.email=mainW.getTextFieldCorreo().getText();
					//a�adir a la base de datos.
					atlCrud.anadirAtleta(dto);
					
					//una vez que el atleta se registra se le inscribe en la competici�n que hab�a seleccionado.
					inscribirse(dto.email, mainW.getTxtFIDCompeticion().getText());
					mainW.vaciarCamposRegistro();
				
				}
				
				
				
							
				} catch (BusinessException ex) {
					JOptionPane.showMessageDialog(null,ex.getMessage());
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
		
		
		
		mainW.getTxtIDCompOrg().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				

				if (mainW.getTxtIDCompOrg().getText().isEmpty() || mainW.getTxtIDCompOrg().getText().isEmpty()) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error: ID vac�o");
					mainW.getTxtIDCompOrg().setText("");
					mainW.getBtnCargarCategorias().setEnabled(false);
					mainW.getBtnGenerarClasificacion().setEnabled(false);
					mainW.getBtnObtenerAtletas().setEnabled(false);
					return;
				}
				
				int id = -1;

				try {
					id = Integer.parseUnsignedInt(mainW.getTxtIDCompOrg().getText());
					currentIdCompeticon = id;
					mainW.getBtnCargarCategorias().setEnabled(true);
					mainW.getBtnGenerarClasificacion().setEnabled(true);
					mainW.getBtnObtenerAtletas().setEnabled(true);
				} catch (NumberFormatException e1) {
					mainW.getLblErrorOrg().setVisible(true);
					mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vac�o o menor que 0");
					mainW.getTxtIDCompOrg().setText("");
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
				a�adirCategoriasAComboBox(currentIdCompeticon);
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
				int catSelectedCB = mainW.getCbCategoria().getSelectedIndex() - 1; //Resto 1 porque primera categor�a es ABSOLUTA (generar todas las clasi)
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


	}
	
	
	

	
	private void a�adirCategoriasAComboBox(int idCompeticion) {
		
		
		
		try {
			this.currentCategoriasInComboBox = compCrud.listarCompeticionesConSusCategorias(idCompeticion);
			
			mainW.getCbCategoria().addItem("ABSOLUTA");
			for(CompeticionCategoriaDto dto :  currentCategoriasInComboBox) {
				mainW.getCbCategoria().addItem(dto.nombreCategor�a);
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
			mainW.getLblErrorOrg().setText("Error: ID vac�o");
		} else {
			int id = -1;
			try {
				id = Integer.parseUnsignedInt(idCompeticion);

				List<AtletaInscritoDto> res = incCrud.obtenerAtletasParaCompeticion(id);

				String[] columnNames = { "Nombre", "Apellidos", "Categor�a", "Fecha de Inscripcion", "Estado" };

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
				mainW.getLblErrorOrg().setText("Error: ID de competicion no numerico, vac�o o menor que 0");
			}
		}

	}

	

	private void inscribirse(String emailAtleta, String idCompeticionString) {
		if (emailAtleta.isBlank() || emailAtleta.isEmpty() || idCompeticionString.isBlank()
				|| idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Alg�n campo est� vacio");
		} else {
			try {
				if(comprobarSiEstaRegistrado(emailAtleta)) {
					int idCompeticion = Integer.parseInt(idCompeticionString);
					incCrud.inscribirAtleta(emailAtleta, idCompeticion);
					JOptionPane.showMessageDialog(null, "Atleta Inscrito");
					mainW.getLblError().setVisible(false);
					sesion = new Sesion(emailAtleta, idCompeticion);
					((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg4");
					mainW.vaciarCamposInscripcion();
				}else {//si el atleta no est� registrado.
					mainW.getTextFieldCorreo().setText(mainW.getTxtFEmail().getText());
					//si el atleta decide registrarse, se habr� introducido ya el correo para su comodidad
					mainW.getRegistroDialog().setVisible(true);
				}
			
			} catch (BusinessException e) {
				mainW.getLblError().setText("Error: " + e.getMessage());
				mainW.getLblError().setVisible(true);
			}

		}

	}

	private boolean comprobarSiEstaRegistrado(String emailAtleta) {
		if(!regCrud.ComprobarDatosInscripcion(emailAtleta)) {
			mainW.setErrorAtletaNoRegistrado();
		
			return false;
		}
		return true;
		
	}
}
