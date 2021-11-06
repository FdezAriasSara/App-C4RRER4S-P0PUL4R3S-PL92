package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
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
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.PagoCrudService;
import uo.ips.application.business.pago.PagoDto;
import uo.ips.application.business.pago.TarjetaDto;

public class InscripcionController {

	private MainWindow mainW;
	private InscripcionCrudService incCrud = BusinessFactory.forInscripcionCrudService();
	private Sesion sesion;
	private PagoCrudService pagCrud = BusinessFactory.forPagoCrudService();
	private AtletaCrudService atlCrud = BusinessFactory.forAtletaCrudService();

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
						((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg1");
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
			String[] columnNames = { "Nombre", "Apellidos", "Posicion", "Tiempo" };

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

	private void inscribirse(String emailAtleta, String idCompeticionString) {
		if (emailAtleta.isBlank() || emailAtleta.isEmpty() || idCompeticionString.isBlank()
				|| idCompeticionString.isEmpty()) {
			mainW.getLblError().setVisible(true);
			mainW.getLblError().setText("Error: Algún campo está vacio");
		} else {
			try {
				int idCompeticion = Integer.parseInt(idCompeticionString);
				incCrud.inscribirAtleta(emailAtleta, idCompeticion);
				JOptionPane.showMessageDialog(null, "Atleta Inscrito");
				mainW.getLblError().setVisible(false);
				sesion = new Sesion(emailAtleta, idCompeticion);
				((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "Pg4");
			} catch (BusinessException e) {
				mainW.getLblError().setText("Error: " + e.getMessage());
				mainW.getLblError().setVisible(true);
			}

		}

	}
}
