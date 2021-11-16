package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.ScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel_soy;
	private JButton btnVolverBienvenida;
	private JPanel panel_card;
	private JPanel panel_atleta;
	private JPanel panel_organizador;
	private JButton btnListarCompeticionesAbiertas;
	private JPanel panel_default;
	private JLabel lblBienvenida;
	private JLabel lblSeleccion;
	private JButton btnAtletaBienvenida;
	private JButton btnOrganizadorBienvenida;
	private JLabel lblEmailAtleta;
	private JTextField txtFEmail;
	private JButton btnInscribirse;
	private JLabel lblError;
	private JButton btnIniciarSesion;
	private JLabel lblNewLabel;
	private JComboBox<String> cbCategoria;
	private JButton btnGenerarClasificacion;
	private JLabel lblErrorOrg;
	private JPanel panel_pago;
	private JButton btnObtenerAtletas;
	private JPanel pnPagoTarjeta;
	private JPanel pnEscogerPago;
	private JLabel lbEligeTipoPago;
	private JButton btTarjeta;
	private JButton btTransferencia;
	private JTextField txtNum;
	private JTextField txtCVC;
	private JLabel lblNumero;
	private JLabel lblFechaDeCaducidad;
	private JLabel lblCvc;
	private JButton btnPagarTarjeta2;
	private JButton btnListarInscripciones;
	private JPanel panel_sesion;
	private JLabel lblRegistro;
	private JTextField textFieldIniciarSesion;
	private JLabel lblSesTitulo;
	private Button btnSesion;
	private Button btnCancelar;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTable tablaClasificacion;
	private JTable tableCompeticion;
	private JButton btnNuevaCompeticion;

	private JButton btnImportarDatos;
	private JTextField txtArchivoTiempos;

	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;
	private JTextArea ErrorTextAreaPago;
	private JTextArea ErrorTextAreaSesion;

	private JButton btnCargarCategorias;


	@SuppressWarnings("unused")
	private CustomDialogRegistro registroDialog;
	private JPanel panel_registrarse;
	private JLabel lblNombre;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JLabel lblApellidos;
	private JLabel lblDni;
	private JTextField textFieldDNI;
	private JLabel lblNacimiento;
	private JCalendar calendarNacimiento;
	private JLabel lblEmail;
	private JTextField textFieldCorreo;
	private JButton btnRegistrarse;
	private JTextArea textAreaRegistro;
	private JLabel lblSexo;
	private JComboBox<String> comboSexo;
	private JButton btCargarPagos;

	private JButton btMostrarCompeticiones;

	private JButton btnAsignacionDorsales;
	private JPanel panel_asignarDorsales;
	private JScrollPane scrollPaneDorsales;
	private JTable tableAsignar;
	private JButton btnAsignar;
	private JButton btNuevaCompeticion;
	private JButton btnMostrarTodasComp;



	/**
	 * Create the frame.
	 */
	public MainWindow() {
		this.registroDialog = new CustomDialogRegistro(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_soy(), BorderLayout.NORTH);
		contentPane.add(getPanel_card(), BorderLayout.CENTER);
	}

	public JPanel getPanel_soy() {
		if (panel_soy == null) {
			panel_soy = new JPanel();
			panel_soy.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_soy.add(getBtnVolverBienvenida());
		}
		return panel_soy;
	}

	public JButton getBtnVolverBienvenida() {
		if (btnVolverBienvenida == null) {
			btnVolverBienvenida = new JButton("Volver a pagina de bienvenida");

			btnVolverBienvenida.setEnabled(false);
		}
		return btnVolverBienvenida;
	}

	public JPanel getPanel_card() {
		if (panel_card == null) {
			panel_card = new JPanel();
			panel_card.setLayout(new CardLayout(0, 0));
			panel_card.add(getPanel_default(), "Pg1");
			panel_card.add(getPanel_atleta(), "Pg2");
			panel_card.add(getPanel_organizador(), "Pg3");
			panel_card.add(getPanel_pago(), "Pg4");
			panel_card.add(getPanel_sesion(), "Pg5");
			panel_card.add(getPanel_registrarse(), "registro");
			panel_card.add(getPanel_asignarDorsales(), "dorsales");

		}
		return panel_card;
	}

	public JPanel getPanel_atleta() {
		if (panel_atleta == null) {
			panel_atleta = new JPanel();
			panel_atleta.setLayout(null);
			panel_atleta.add(getScrollPane_1());
			panel_atleta.add(getBtnListarCompeticionesAbiertas());
			panel_atleta.add(getLblEmailAtleta());
			panel_atleta.add(getTxtFEmail());
			panel_atleta.add(getBtnInscribirse());
			panel_atleta.add(getLblError());
			panel_atleta.add(getBtnIniciarSesion());
			panel_atleta.add(getBtnListarInscripciones());
		}
		return panel_atleta;
	}

	public JPanel getPanel_organizador() {
		if (panel_organizador == null) {
			panel_organizador = new JPanel();
			panel_organizador.setLayout(null);
			panel_organizador.add(getLblNewLabel());
			panel_organizador.add(getCbCategoria());
			panel_organizador.add(getBtnGenerarClasificacion());
			panel_organizador.add(getLblErrorOrg());
			panel_organizador.add(getScrollPane());
			panel_organizador.add(getBtnObtenerAtletas());
			panel_organizador.add(getBtnImportarDatos());
			panel_organizador.add(getTxtArchivoTiempos());

			panel_organizador.add(getBtnCargarCategorias());

			panel_organizador.add(getBtCargarPagos());

			panel_organizador.add(getBtMostrarCompeticiones());

			panel_organizador.add(getBtnAsignacionDorsales());
			panel_organizador.add(getBtNuevaCompeticion());
			panel_organizador.add(getBtnMostrarTodasComp());


		}
		return panel_organizador;
	}

	public JButton getBtnListarCompeticionesAbiertas() {
		if (btnListarCompeticionesAbiertas == null) {
			btnListarCompeticionesAbiertas = new JButton("Listar Competiciones");

			btnListarCompeticionesAbiertas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnListarCompeticionesAbiertas.setBounds(10, 10, 205, 34);
		}
		return btnListarCompeticionesAbiertas;
	}

	public JPanel getPanel_default() {
		if (panel_default == null) {
			panel_default = new JPanel();
			panel_default.setLayout(null);
			panel_default.add(getLblBienvenida());
			panel_default.add(getLblSeleccion());
			panel_default.add(getBtnAtletaBienvenida());
			panel_default.add(getBtnOrganizadorBienvenida());
		}
		return panel_default;
	}

	public JLabel getLblBienvenida() {
		if (lblBienvenida == null) {
			lblBienvenida = new JLabel("Bienvenido a su gestor de carreras");
			lblBienvenida.setFont(new Font("Dialog", Font.PLAIN, 40));
			lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
			lblBienvenida.setBounds(108, 163, 682, 101);
		}
		return lblBienvenida;
	}

	public JLabel getLblSeleccion() {
		if (lblSeleccion == null) {
			lblSeleccion = new JLabel("Por favor, seleccione su rol:");
			lblSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
			lblSeleccion.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblSeleccion.setBounds(108, 263, 682, 47);
		}
		return lblSeleccion;
	}

	public JButton getBtnAtletaBienvenida() {
		if (btnAtletaBienvenida == null) {
			btnAtletaBienvenida = new JButton("Atleta");
			btnAtletaBienvenida.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					btnVolverBienvenida.setEnabled(true);
					((CardLayout) getPanel_card().getLayout()).show(getPanel_card(), "Pg2");

				}
			});

			btnAtletaBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnAtletaBienvenida.setBounds(219, 354, 183, 47);
		}
		return btnAtletaBienvenida;
	}

	public JButton getBtnOrganizadorBienvenida() {
		if (btnOrganizadorBienvenida == null) {
			btnOrganizadorBienvenida = new JButton("Organizador");
			btnOrganizadorBienvenida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnVolverBienvenida.setEnabled(true);
					((CardLayout) getPanel_card().getLayout()).show(getPanel_card(), "Pg3");

				}
			});
			btnOrganizadorBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnOrganizadorBienvenida.setBounds(433, 354, 183, 47);
		}
		return btnOrganizadorBienvenida;
	}

	public JLabel getLblEmailAtleta() {
		if (lblEmailAtleta == null) {
			lblEmailAtleta = new JLabel("Introduzca el email con el que se registro:");
			lblEmailAtleta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEmailAtleta.setBounds(10, 418, 307, 34);
		}
		return lblEmailAtleta;
	}

	public JTextField getTxtFEmail() {
		if (txtFEmail == null) {
			txtFEmail = new JTextField();
			txtFEmail.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!checkFormatoEmail(getTxtFEmail().getText())) {
						getTxtFEmail().setText("");
						setErrorFormatoEmail();
					}
				}
			});
			txtFEmail.setFont(new Font("Dialog", Font.PLAIN, 15));
			txtFEmail.setHorizontalAlignment(SwingConstants.CENTER);
			txtFEmail.setBounds(327, 421, 240, 29);
			txtFEmail.setColumns(10);
		}
		return txtFEmail;
	}

	public JButton getBtnInscribirse() {
		if (btnInscribirse == null) {
			btnInscribirse = new JButton("Inscribirse");
			btnInscribirse.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnInscribirse.setBounds(614, 418, 193, 34);
		}
		return btnInscribirse;
	}

	public JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel("Error:");
			lblError.setVisible(false);
			lblError.setForeground(Color.RED);
			lblError.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblError.setBounds(10, 462, 558, 34);
		}
		return lblError;
	}

	public void setErrorFormatoEmail() {
		lblError.setText("Error: El formato del email es incorrecto.");
		lblError.setForeground(Color.RED);
		lblError.setVisible(true);
	}

	public void setErrorAlListarCompeticiones() {
		lblError.setText("Error: Error al listar competiciones.");
		lblError.setForeground(Color.RED);
		lblError.setVisible(true);
	}

	public void setErrorYaEstabaInscrito() {
		lblError.setText("Error: Ya estabas inscrito en dicha competicion.");
		lblError.setForeground(Color.RED);
		lblError.setVisible(true);
	}

	public void setErrorAtletaNoRegistrado() {
		lblError.setText("Error: Atleta no registrado.");
		lblError.setForeground(Color.RED);
		lblError.setVisible(true);
	}

	public void setConfirmadaInscripcion() {
		lblError.setText("Error: Atleta inscrito correctamente.");
		lblError.setVisible(true);
		lblError.setForeground(Color.GREEN);
	}

	public JButton getBtnIniciarSesion() {
		if (btnIniciarSesion == null) {
			btnIniciarSesion = new JButton("Iniciar sesi\u00F3n");
			btnIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnIniciarSesion.setBounds(716, 10, 142, 34);

		}
		return btnIniciarSesion;
	}

	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Categor\u00EDa :");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel.setBounds(10, 359, 140, 35);
		}
		return lblNewLabel;
	}

	public JComboBox<String> getCbCategoria() {
		if (cbCategoria == null) {
			cbCategoria = new JComboBox<String>();
			cbCategoria.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbCategoria.setBounds(147, 364, 157, 29);

		}
		return cbCategoria;
	}

	public JButton getBtnGenerarClasificacion() {
		if (btnGenerarClasificacion == null) {
			btnGenerarClasificacion = new JButton("Generar clasificacion");

			btnGenerarClasificacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnGenerarClasificacion.setBounds(10, 404, 294, 35);
			btnGenerarClasificacion.setEnabled(false);
		}
		return btnGenerarClasificacion;
	}

	public CustomDialogRegistro getRegistroDialog() {
		return registroDialog;
	}

	public JLabel getLblErrorOrg() {
		if (lblErrorOrg == null) {
			lblErrorOrg = new JLabel("Error:");
			lblErrorOrg.setVisible(false);
			lblErrorOrg.setForeground(Color.RED);
			lblErrorOrg.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblErrorOrg.setBounds(10, 449, 858, 35);
		}
		return lblErrorOrg;
	}

	public JPanel getPanel_pago() {
		if (panel_pago == null) {
			panel_pago = new JPanel();
			panel_pago.setLayout(new CardLayout(0, 0));
			panel_pago.add(getPnEscogerPago(), "escogerPago");
			panel_pago.add(getPnPagoTarjeta(), "tarjeta");
		}
		return panel_pago;
	}

	public JButton getBtnObtenerAtletas() {
		if (btnObtenerAtletas == null) {
			btnObtenerAtletas = new JButton("Obtener Atletas");
			btnObtenerAtletas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnObtenerAtletas.setBounds(525, 404, 324, 35);
			btnObtenerAtletas.setEnabled(false);
		}
		return btnObtenerAtletas;
	}

	private JPanel getPnPagoTarjeta() {
		if (pnPagoTarjeta == null) {
			pnPagoTarjeta = new JPanel();
			pnPagoTarjeta.setLayout(null);
			pnPagoTarjeta.add(getTxtNum());
			pnPagoTarjeta.add(getTxtCVC());
			pnPagoTarjeta.add(getLblNumero());
			pnPagoTarjeta.add(getLblFechaDeCaducidad());
			pnPagoTarjeta.add(getLblCvc());
			pnPagoTarjeta.add(getBtnPagarTarjeta2());
			pnPagoTarjeta.add(getMonthChooser());
			pnPagoTarjeta.add(getYearChooser());
			pnPagoTarjeta.add(getErrorTextAreaPago());
		}
		return pnPagoTarjeta;
	}

	public JPanel getPnEscogerPago() {
		if (pnEscogerPago == null) {
			pnEscogerPago = new JPanel();
			pnEscogerPago.setLayout(null);
			pnEscogerPago.add(getLbEligeTipoPago());
			pnEscogerPago.add(getBtTarjeta_1());
			pnEscogerPago.add(getBtTransferencia_1());
		}
		return pnEscogerPago;
	}

	private JLabel getLbEligeTipoPago() {
		if (lbEligeTipoPago == null) {
			lbEligeTipoPago = new JLabel("Elija tipo de pago");
			lbEligeTipoPago.setBounds(317, 205, 175, 25);
			lbEligeTipoPago.setHorizontalAlignment(SwingConstants.CENTER);
			lbEligeTipoPago.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		return lbEligeTipoPago;
	}

	public JButton getBtTarjeta_1() {
		if (btTarjeta == null) {
			btTarjeta = new JButton("Tarjeta");
			btTarjeta.setBounds(217, 304, 121, 38);
			btTarjeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btTarjeta;
	}

	public JButton getBtTransferencia_1() {
		if (btTransferencia == null) {
			btTransferencia = new JButton("Transferencia");
			btTransferencia.setBounds(445, 304, 140, 38);
			btTransferencia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return btTransferencia;
	}

	public JTextField getTxtNum() {
		if (txtNum == null) {
			txtNum = new JTextField();
			txtNum.addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {

					if (!compruebaNumeroTarjeta(getTxtNum().getText())) {
						mostrarErrorPagoTarjeta(
								"El formato del nï¿½mero de tarjeta es incorrecto. \n Deben ser 16 dï¿½gitos.");

					}
				}

			});

			txtNum.setBounds(417, 141, 384, 53);
			txtNum.setColumns(10);
		}
		return txtNum;
	}

	public JTextField getTxtCVC() {
		if (txtCVC == null) {
			txtCVC = new JTextField();

			txtCVC.addFocusListener(new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {

					if (!compruebaCVC(getTxtCVC().getText())) {

						mostrarErrorPagoTarjeta(
								"El cvc debe ser un nï¿½mero de tres dï¿½gitos.\n Se encuentra en la cara trasera de tu tarjeta.");
						getTxtCVC().setText("");
					}
				}

			});
			txtCVC.setColumns(10);
			txtCVC.setBounds(417, 372, 384, 53);

		}
		return txtCVC;
	}

	private JLabel getLblNumero() {
		if (lblNumero == null) {
			lblNumero = new JLabel("N\u00FAmero de tarjeta:");
			lblNumero.setLabelFor(getTxtNum());
			lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 24));
			lblNumero.setBounds(45, 145, 291, 49);
		}
		return lblNumero;
	}

	private JLabel getLblFechaDeCaducidad() {
		if (lblFechaDeCaducidad == null) {
			lblFechaDeCaducidad = new JLabel("Fecha de caducidad:");
			lblFechaDeCaducidad.setFont(new Font("Tahoma", Font.PLAIN, 24));
			lblFechaDeCaducidad.setBounds(45, 263, 291, 49);
		}
		return lblFechaDeCaducidad;
	}

	private JLabel getLblCvc() {
		if (lblCvc == null) {
			lblCvc = new JLabel("cvc:");
			lblCvc.setLabelFor(getTxtCVC());
			lblCvc.setFont(new Font("Tahoma", Font.PLAIN, 24));
			lblCvc.setBounds(45, 372, 291, 49);

		}
		return lblCvc;
	}

	public JButton getBtnPagarTarjeta2() {
		if (btnPagarTarjeta2 == null) {
			btnPagarTarjeta2 = new JButton("Pagar");
			btnPagarTarjeta2.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnPagarTarjeta2.setBounds(697, 476, 126, 53);
		}
		return btnPagarTarjeta2;
	}

	public JButton getBtnListarInscripciones() {
		if (btnListarInscripciones == null) {
			btnListarInscripciones = new JButton("Mis inscripciones");
			btnListarInscripciones.setEnabled(false);
			btnListarInscripciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnListarInscripciones.setBounds(225, 10, 166, 34);
		}
		return btnListarInscripciones;
	}

	public void vaciarCamposPago() {
		getTxtNum().setText("");
		getTxtCVC().setText("");

	}

	public void vaciarCampoIniciarSesion() {
		getTextFieldIniciarSesion().setText("");
	}

	private JPanel getPanel_sesion() {
		if (panel_sesion == null) {
			panel_sesion = new JPanel();
			panel_sesion.setLayout(null);
			panel_sesion.add(getLblRegistro());
			panel_sesion.add(getTextFieldIniciarSesion());
			panel_sesion.add(getLblSesTitulo());
			panel_sesion.add(getBtnSesion());
			panel_sesion.add(getBtnCancelar());
			panel_sesion.add(getErrorTextAreaSesion());
		}
		return panel_sesion;
	}

	private JLabel getLblRegistro() {
		if (lblRegistro == null) {
			lblRegistro = new JLabel("Introduce tu email:");
			lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 24));
			lblRegistro.setBounds(77, 199, 234, 112);
		}
		return lblRegistro;
	}

	public JTextField getTextFieldIniciarSesion() {
		if (textFieldIniciarSesion == null) {
			textFieldIniciarSesion = new JTextField();
			textFieldIniciarSesion.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!checkFormatoEmail(getTextFieldIniciarSesion().getText())) {
						mostrarErrorInicioSesion("El formato del correo es incorrecto.");
						getTextFieldIniciarSesion().setText("");// vacï¿½o el campo para evitar que proceda con la
																// inscripciï¿½n
					}
				}
			});
			textFieldIniciarSesion.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {

					vaciarErrorInicioSesion();
					// para que el mensaje desaparezca cuando el usuario vuelve a intentarlo
				}
			});
			textFieldIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textFieldIniciarSesion.setBounds(321, 222, 440, 57);
			textFieldIniciarSesion.setColumns(10);
		}
		return textFieldIniciarSesion;
	}

	public void vaciarErrorInicioSesion() {
		getErrorTextAreaSesion().setText("");
		getErrorTextAreaSesion().setVisible(false);
		getErrorTextAreaSesion().setEnabled(false);
	}

	public void mostrarErrorInicioSesion(String string) {
		getErrorTextAreaSesion().setText(string);
		getErrorTextAreaSesion().setVisible(true);
		getErrorTextAreaSesion().setEnabled(true);
	}

	public boolean checkFormatoEmail(String text) {

		return text.matches("[a-zA-Z0-9]{0,100}@[a-zA-Z0-9]{0,40}+.(es|com|org)");
	}

	private JLabel getLblSesTitulo() {
		if (lblSesTitulo == null) {
			lblSesTitulo = new JLabel("Iniciar sesi\u00F3n");
			lblSesTitulo.setFont(new Font("Tahoma", Font.PLAIN, 40));
			lblSesTitulo.setBounds(283, 31, 256, 57);
		}
		return lblSesTitulo;
	}

	public Button getBtnSesion() {
		if (btnSesion == null) {
			btnSesion = new Button("Iniciar");
			btnSesion.setFont(new Font("Dialog", Font.PLAIN, 21));
			btnSesion.setForeground(new Color(0, 0, 0));
			btnSesion.setBounds(584, 485, 140, 45);
		}
		return btnSesion;
	}

	private Button getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new Button("Cancelar");

			btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 21));
			btnCancelar.setForeground(new Color(0, 0, 0));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					vaciarCampoIniciarSesion();
				}
			});
			btnCancelar.setBounds(737, 480, 111, 50);
		}
		return btnCancelar;
	}

	public JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTablaClasificacion());

			scrollPane.setBounds(10, 10, 858, 294);

		}
		return scrollPane;
	}

	public JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane(getTableCompeticion());
			scrollPane_1.setBounds(10, 54, 848, 307);
		}
		return scrollPane_1;
	}

	public JTable getTablaClasificacion() {
		if (tablaClasificacion == null) {
			tablaClasificacion = new JTable();
			
			tablaClasificacion.setBounds(10, 10, 858, 294);
		}
		return tablaClasificacion;
	}

	public JTable getTableCompeticion() {
		if (tableCompeticion == null) {
			tableCompeticion = new JTable();
			tableCompeticion.setBounds(10, 54, 848, 307);
		}
		return tableCompeticion;
	}

	public JButton getBtnImportarDatos() {
		if (btnImportarDatos == null) {
			btnImportarDatos = new JButton("Importar Datos");
			btnImportarDatos.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnImportarDatos.setBounds(525, 359, 157, 35);
		}
		return btnImportarDatos;
	}

	public JTextField getTxtArchivoTiempos() {
		if (txtArchivoTiempos == null) {
			txtArchivoTiempos = new JTextField();
			txtArchivoTiempos.setBounds(692, 361, 157, 35);
			txtArchivoTiempos.setColumns(10);
		}
		return txtArchivoTiempos;
	}

	public JMonthChooser getMonthChooser() {
		if (monthChooser == null) {
			monthChooser = new JMonthChooser();
			monthChooser.getComboBox().setFont(new Font("Tahoma", Font.PLAIN, 21));
			monthChooser.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 18));
			monthChooser.setBounds(417, 271, 180, 41);

		}
		return monthChooser;
	}

	public JYearChooser getYearChooser() {
		if (yearChooser == null) {
			yearChooser = new JYearChooser();
			yearChooser.getSpinner().setFont(new Font("Tahoma", Font.PLAIN, 42));
			yearChooser.setBounds(635, 271, 134, 41);
			yearChooser.setStartYear(2021);
			yearChooser.setEndYear(2029);
		}
		return yearChooser;
	}

	private JTextArea getErrorTextAreaPago() {
		if (ErrorTextAreaPago == null) {
			ErrorTextAreaPago = new JTextArea();
			ErrorTextAreaPago.setFont(new Font("Tahoma", Font.PLAIN, 19));
			ErrorTextAreaPago.setBackground(new Color(240, 240, 240));
			ErrorTextAreaPago.setForeground(Color.RED);
			ErrorTextAreaPago.setVisible(false);
			ErrorTextAreaPago.setEnabled(false);
			ErrorTextAreaPago.setEditable(false);
			ErrorTextAreaPago.setBounds(29, 476, 553, 70);
		}
		return ErrorTextAreaPago;
	}

	private void mostrarErrorPagoTarjeta(String error) {
		ErrorTextAreaPago.setText(error);
		ErrorTextAreaPago.setVisible(true);
		ErrorTextAreaPago.setEnabled(true);
	}
	private boolean compruebaIDCompeticion(String text) {
		return text.matches("[0-9]+");
	}
	private boolean compruebaCVC(String text) {

		return text.matches("[0-9]{3}");
	}

	private boolean compruebaNumeroTarjeta(String text) {
		return text.matches("[0-9]{16}");
	}

	public JTextArea getErrorTextAreaSesion() {
		if (ErrorTextAreaSesion == null) {
			ErrorTextAreaSesion = new JTextArea();
			ErrorTextAreaSesion.setFont(new Font("Tahoma", Font.BOLD, 20));
			ErrorTextAreaSesion.setForeground(Color.RED);
			ErrorTextAreaSesion.setVisible(false);
			ErrorTextAreaSesion.setEnabled(false);
			ErrorTextAreaSesion.setBounds(41, 417, 472, 87);
			ErrorTextAreaSesion.setBackground(new Color(240, 240, 240));
		}
		return ErrorTextAreaSesion;

	}

	public JButton getBtnCargarCategorias() {
		if (btnCargarCategorias == null) {
			btnCargarCategorias = new JButton("Cargar categorias");
			btnCargarCategorias.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCargarCategorias.setBounds(314, 364, 157, 29);
			btnCargarCategorias.setEnabled(false);
		}
		return btnCargarCategorias;
	}

	private JPanel getPanel_registrarse() {
		if (panel_registrarse == null) {
			panel_registrarse = new JPanel();
			panel_registrarse.setLayout(null);
			panel_registrarse.add(getLblNombre());
			panel_registrarse.add(getTxtRegNombre());
			panel_registrarse.add(getTxtRegApellido());
			panel_registrarse.add(getLblApellidos());
			panel_registrarse.add(getLblDni());
			panel_registrarse.add(getTextFieldDNI());
			panel_registrarse.add(getLblNacimiento());
			panel_registrarse.add(getCalendarNacimiento());
			panel_registrarse.add(getLblEmail());
			panel_registrarse.add(getTextFieldCorreo());
			panel_registrarse.add(getBtnRegistrarse());
			panel_registrarse.add(getTextAreaRegistro());
			panel_registrarse.add(getLblSexo());
			panel_registrarse.add(getComboSexo());

		}
		return panel_registrarse;
	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setLabelFor(getTxtRegNombre());
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblNombre.setBounds(66, 10, 81, 37);
		}
		return lblNombre;
	}

	public JTextField getTxtRegNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
			textFieldNombre.setBounds(261, 11, 366, 37);
			textFieldNombre.setColumns(10);
			textFieldNombre.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!comprobarSoloTexto(getTxtRegApellido().getText())) {
						mostrarErrorRegistro(
								"El nombre solo puede tener letras. Si tu nombre es compuesto emplea el sï¿½mbolo -.");
						getTxtRegNombre().setText("");
					}
				}

			});
		}
		return textFieldNombre;
	}

	public JTextField getTxtRegApellido() {
		if (textFieldApellidos == null) {
			textFieldApellidos = new JTextField();
			textFieldApellidos.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!comprobarSoloTexto(getTxtRegApellido().getText())) {
						mostrarErrorRegistro(
								"El apellido solo puede tener letras. Si tu apellido es compuesto emplea el sï¿½mbolo -.");
						getTxtRegApellido().setText("");
					}
				}

			});
			textFieldApellidos.setBounds(261, 75, 366, 37);
			textFieldApellidos.setColumns(10);
		}
		return textFieldApellidos;
	}

	protected boolean comprobarSoloTexto(String text) {
		// Comprueba que solo sea un nombre/apellido o un apellido/nombre compuesto
		return text.matches("[a-zA-Z]{2,50}|([a-zA-Z]{2,25} -[a-zA-Z]{2,25})");
		// Evita que se introduzcan muchos caracteres
	}

	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos:");
			lblApellidos.setLabelFor(getTxtRegApellido());
			lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblApellidos.setBounds(66, 76, 105, 27);
		}
		return lblApellidos;
	}

	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
			lblDni.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblDni.setBounds(66, 135, 81, 31);
		}
		return lblDni;
	}

	public JTextField getTextFieldDNI() {
		if (textFieldDNI == null) {
			textFieldDNI = new JTextField();
			textFieldDNI.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!checkFormatoDni(getTextFieldDNI().getText())) {
						mostrarErrorRegistro("El dni debe contener seis dígitos y una letra.");
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
					borrarErrorRegistro();
					// para que al intentar arreglar el mail, se borre el mensaje de error y no se
					// confunda el usuario
				}

			});
			textFieldDNI.setColumns(10);
			textFieldDNI.setBounds(261, 136, 366, 37);
		}
		return textFieldDNI;
	}

	private boolean checkFormatoDni(String text) {

		return text.matches("\\d{6}\\D");
	}

	private JLabel getLblNacimiento() {
		if (lblNacimiento == null) {
			lblNacimiento = new JLabel("Fecha de nacimiento:");
			lblNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNacimiento.setBounds(66, 309, 168, 37);
		}
		return lblNacimiento;
	}

	public JCalendar getCalendarNacimiento() {
		if (calendarNacimiento == null) {
			calendarNacimiento = new JCalendar();
			calendarNacimiento.setBounds(261, 310, 205, 153);

		}
		return calendarNacimiento;
	}

	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Correo:");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblEmail.setBounds(66, 195, 96, 27);
		}
		return lblEmail;
	}

	public JTextField getTextFieldCorreo() {
		if (textFieldCorreo == null) {
			textFieldCorreo = new JTextField();
			textFieldCorreo.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					if (!checkFormatoEmail(getTextFieldCorreo().getText())) {
						mostrarErrorRegistro("El correo no tiene el formato adecuado.");
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
					borrarErrorRegistro();
					// para que al intentar arreglar el mail, se borre el mensaje de error y no se
					// confunda el usuario
				}
			});
			textFieldCorreo.setBounds(261, 194, 366, 37);
			textFieldCorreo.setColumns(10);
		}
		return textFieldCorreo;
	}

	public void mostrarErrorRegistro(String string) {
		getTextAreaRegistro().setText(string);
		getTextAreaRegistro().setVisible(true);
		getTextAreaRegistro().setEnabled(true);
	}

	protected void borrarErrorRegistro() {
		getTextAreaRegistro().setText("");
		getTextAreaRegistro().setVisible(false);
		getTextAreaRegistro().setEnabled(false);
	}

	public JButton getBtnRegistrarse() {
		if (btnRegistrarse == null) {
			btnRegistrarse = new JButton("Registro");
			btnRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnRegistrarse.setBounds(719, 502, 125, 44);
		}
		return btnRegistrarse;
	}

	private JTextArea getTextAreaRegistro() {
		if (textAreaRegistro == null) {
			textAreaRegistro = new JTextArea();
			textAreaRegistro.setVisible(false);
			textAreaRegistro.setForeground(Color.RED);
			textAreaRegistro.setBounds(52, 474, 590, 72);
			textAreaRegistro.setBackground(new Color(240, 240, 240));
		}
		return textAreaRegistro;
	}

	private JLabel getLblSexo() {
		if (lblSexo == null) {
			lblSexo = new JLabel("Sexo:");
			lblSexo.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblSexo.setBounds(66, 258, 110, 27);
		}
		return lblSexo;
	}

	public JComboBox<String> getComboSexo() {
		if (comboSexo == null) {
			comboSexo = new JComboBox<String>();
			comboSexo.setBounds(261, 262, 205, 27);
			comboSexo.addItem("Femenino");
			comboSexo.addItem("Masculino");
		}
		return comboSexo;
	}

	

	public void vaciarCamposRegistro() {
		getTxtRegApellido().setText("");
		getTxtRegNombre().setText("");
		getTextFieldCorreo().setText("");
		getTextFieldDNI().setText("");


	}

	public JButton getBtCargarPagos() {
		if (btCargarPagos == null) {
			btCargarPagos = new JButton("Cargar pagos");
			btCargarPagos.setMnemonic('C');
			btCargarPagos.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btCargarPagos.setBounds(662, 495, 140, 35);
		}
		return btCargarPagos;
	}


	public JButton getBtMostrarCompeticiones() {
		if (btMostrarCompeticiones == null) {
			btMostrarCompeticiones = new JButton("Mostrar competiciones");
			btMostrarCompeticiones.setMnemonic('M');
			btMostrarCompeticiones.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btMostrarCompeticiones.setBounds(414, 495, 190, 35);
		}
		return btMostrarCompeticiones;
	}

	public JButton getBtnAsignacionDorsales() {
		if (btnAsignacionDorsales == null) {
			btnAsignacionDorsales = new JButton("Asignar dorsales");
			btnAsignacionDorsales.setEnabled(false);
			btnAsignacionDorsales.setToolTipText("Pulsa aqu\u00ED para generar los dorsales de la competicion seleccionada");
			btnAsignacionDorsales.setFont(new Font("Tahoma", Font.PLAIN, 19));
			btnAsignacionDorsales.setBounds(460, 314, 292, 40);
		}
		return btnAsignacionDorsales;
	}
	private JPanel getPanel_asignarDorsales() {
		if (panel_asignarDorsales == null) {
			panel_asignarDorsales = new JPanel();
			panel_asignarDorsales.setLayout(null);
			panel_asignarDorsales.add(getTableAsignar());
			panel_asignarDorsales.add(getScrollPaneDorsales());
			panel_asignarDorsales.add(getBtnAsignar());
		}
		return panel_asignarDorsales;
	}
	public void setErrorOrgPlazosSinTerminar() {
		getLblErrorOrg().setText("Error: Aún no han finalizado los plazos de inscripción.");
		getLblErrorOrg().setForeground(Color.RED);
		getLblErrorOrg().setVisible(true);
	}
	public void setErrorOrgID() {
		getLblErrorOrg().setText("Error: El ID de competición solo puede tener dígitos.");
		getLblErrorOrg().setForeground(Color.RED);
		getLblErrorOrg().setVisible(true);
	}
	private JScrollPane getScrollPaneDorsales() { 
		if (scrollPaneDorsales == null) {
			scrollPaneDorsales = new JScrollPane(getTableAsignar());
			scrollPaneDorsales.setBounds(173, 108, 514, 201);
		}
		return scrollPaneDorsales;
	}
	public JTable getTableAsignar() {
		if (tableAsignar == null) {
			tableAsignar = new JTable();
			tableAsignar.setBounds(681, 108, -510, 201);
			
		}
		return tableAsignar;
	}
	public JButton getBtnAsignar() {
		if (btnAsignar == null) {
			btnAsignar = new JButton("Asignar");
			btnAsignar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAsignar.setBounds(373, 415, 89, 23);
		}
		return btnAsignar;
	}
	

	public JButton getBtNuevaCompeticion() {
		if (btNuevaCompeticion == null) {
			btNuevaCompeticion = new JButton("Nueva Competici\u00F3n");
			btNuevaCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btNuevaCompeticion.setBounds(147, 495, 190, 35);
		}
		return btNuevaCompeticion;
	}
	public JButton getBtnMostrarTodasComp() {
		if (btnMostrarTodasComp == null) {
			btnMostrarTodasComp = new JButton("Mostrar todas las competiciones");
			
			btnMostrarTodasComp.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnMostrarTodasComp.setBounds(10, 314, 294, 35);
		}
		return btnMostrarTodasComp;
	}
}
