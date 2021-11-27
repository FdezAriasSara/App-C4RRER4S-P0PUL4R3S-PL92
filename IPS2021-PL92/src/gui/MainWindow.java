package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class MainWindow extends JFrame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JDateChooser fechaFinal;
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

	private JButton btnAsignacionDorsales;
	private JPanel panel_asignarDorsales;
	private JScrollPane scrollPaneDorsales;
	private JButton btnAsignar;
	private JButton btNuevaCompeticion;
	private JButton btnMostrarTodasComp;
	private JButton btnInscribirClubArch;
	private JButton btnInscribirClubFormulario;
	private JPanel panel;
	private JPanel panel_formularioClub;
	private JLabel lblNombreClub;
	private JTextField txtFNombreClub;
	private JTextField txtFNombreAtl;
	private JLabel lblNombreAtl;
	private JLabel lblApellido;
	private JLabel lblEmailFormulario;
	private JTextField txtFApellido;
	private JComboBox<String> cBoxSexo;
	private JLabel lblSexoFormulario;
	private JLabel lblFechaDeNacimiento;
	private JTextField textField;
	private JTextField txtFEmailFormulario;
	private JLabel lblDniForm;
	private JTextField txtFDniForm;
	private JButton btnInscribirFormulario;
	private JTable tableFormulario;
	private JButton btnTerminar;
	private JLabel lblErrorForm;
	private JLabel lblCompeticionActualForm;
	private JTextField txtFCompeticionForm;
	private JScrollPane scrollPaneFormulario;
	private JButton btnBorrarAtletaDeFormulario;
	private JTextField txtPlazasDispnibles;
	private JLabel lblPlazasDisponiblesForm;

	private JButton btInformacionContable;
	private JPanel panel_contabilidad;
	private JLabel lbNombeCompeticion;
	private JLabel lbNombeNombreCompeticion;
	private JLabel lbFecha;
	private JScrollPane scrollPane_2;
	private JTable tablaContabilidad;

	private JPanel panel_perfilAtleta;
	private JPanel panel_perfil;
	private JPanel panel_comparar;
	private JPanel panel_Comparativa;
	private JScrollPane scrollPane_inscripciones;
	private JTable tableAsignar;
	private JButton btnMisInscripciones;
	private JButton btnCambiarUsuario;
	private JLabel lblCompeticionSeleccionada;
	private JLabel lblMostrarAtletas;
	private JButton btnMostrarAtletas;
	private JLabel lblCancelarInscripcion;
	private JButton btnCancelarInscripcion;
	private JLabel lblErrorPerfil;
	private JScrollPane scrollPane_AtletasCompSeleccionada;
	private JTable tableAtletasDeCompSeleccionada;
	private JScrollPane scrollPane_comparativa;
	private JTable tableComparativa;
	private JTable tableInscripciones;
	private JButton btnVolverPerfil;
	private JLabel lblNombreCompeticion;
	private JLabel lblNombreCompeticion2;
	private JButton btnVolverPerfil2;
	private JLabel lblCompara;
	private JLabel lblAtletaSeleccionado;
	private JButton btnCompararse;

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
		
		 
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
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
			panel_card.add(getPanel_formularioClub(), "formulario");

			panel_card.add(getPanel_contabilidad(), "contabilidad");

			panel_card.add(getPanel_perfilAtleta(), "perfil");

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
			panel_atleta.add(getBtnInscribirClubArch());
			panel_atleta.add(getBtnInscribirClubFormulario());
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

			panel_organizador.add(getBtnAsignacionDorsales());
			panel_organizador.add(getBtNuevaCompeticion());
			panel_organizador.add(getBtnMostrarTodasComp());
			panel_organizador.add(getPanel());

			panel_organizador.add(getBtInformacionContable());

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

				@Override
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
				@Override
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
				getTextFieldIniciarSesion().setText("");// vacï¿½o el
																// campo para
																// evitar que
																// proceda con
																// la

																// inscripciï¿½n
					}
				}
			});
			textFieldIniciarSesion.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {

					vaciarErrorInicioSesion();
					// para que el mensaje desaparezca cuando el usuario vuelve
					// a intentarlo
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
				@Override
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

			scrollPane.setBounds(10, 10, 680, 294);

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

//	private boolean compruebaIDCompeticion(String text) {
//		return text.matches("[0-9]+");
//	}

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

								"El nombre solo puede tener letras. Si tu nombre es compuesto emplea el simbolo -.");

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
		// Comprueba que solo sea un nombre/apellido o un apellido/nombre
		// compuesto
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
					// para que al intentar arreglar el mail, se borre el
					// mensaje de error y no se
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
					// para que al intentar arreglar el mail, se borre el
					// mensaje de error y no se
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
// 25-11-21-> lo quit� por los conlfictos-> btnMostrarTodasComp;
//	public JButton getBtMostrarCompeticiones() {
//		if (btMostrarCompeticiones == null) {
//			btMostrarCompeticiones = new JButton("Mostrar competiciones");
//			btMostrarCompeticiones.setMnemonic('M');
//			btMostrarCompeticiones.setFont(new Font("Tahoma", Font.PLAIN, 15));
//			btMostrarCompeticiones.setBounds(414, 495, 190, 35);
//		}
//		return btMostrarCompeticiones;
//	}

	public JButton getBtnAsignacionDorsales() {
		if (btnAsignacionDorsales == null) {
			btnAsignacionDorsales = new JButton("Asignar dorsales");
			btnAsignacionDorsales.setEnabled(false);

			btnAsignacionDorsales
					.setToolTipText("Pulsa aqu\u00ED para generar los dorsales de la competicion seleccionada");

			btnAsignacionDorsales.setFont(new Font("Tahoma", Font.PLAIN, 19));
			btnAsignacionDorsales.setBounds(525, 314, 324, 40);
		}
		return btnAsignacionDorsales;
	}

	private JPanel getPanel_asignarDorsales() {
		if (panel_asignarDorsales == null) {
			panel_asignarDorsales = new JPanel();
			panel_asignarDorsales.setLayout(null);
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

	public JButton getBtnInscribirClubArch() {
		if (btnInscribirClubArch == null) {
			btnInscribirClubArch = new JButton("Inscribir a club con archivo");
			btnInscribirClubArch.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnInscribirClubArch.setBounds(567, 463, 240, 34);
			btnInscribirClubArch.setEnabled(false);
		}
		return btnInscribirClubArch;
	}

	public JButton getBtnInscribirClubFormulario() {
		if (btnInscribirClubFormulario == null) {
			btnInscribirClubFormulario = new JButton("Inscribir a club con formulario");
			
			btnInscribirClubFormulario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnInscribirClubFormulario.setBounds(567, 507, 240, 34);
			btnInscribirClubFormulario.setEnabled(false);
		}
		return btnInscribirClubFormulario;
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(700, 10, 148, 294);
		}
		return panel;
	}
	public JPanel getPanel_formularioClub() {
		if (panel_formularioClub == null) {
			panel_formularioClub = new JPanel();
			panel_formularioClub.setLayout(null);
			panel_formularioClub.add(getLblNombreClub());
			panel_formularioClub.add(getTxtFNombreClub());
			panel_formularioClub.add(getTxtFNombreAtl());
			panel_formularioClub.add(getLblNombreAtl());
			panel_formularioClub.add(getLblApellido());
			panel_formularioClub.add(getLblEmailFormulario());
			panel_formularioClub.add(getTxtFApellido());
			panel_formularioClub.add(getCBoxSexo());
			panel_formularioClub.add(getLblSexoFormulario());
			 
			panel_formularioClub.add(getFechaFinal());
			panel_formularioClub.add(getLblFechaDeNacimiento());
			panel_formularioClub.add(getTxtFEmailFormulario());
			panel_formularioClub.add(getLblDniForm());
			panel_formularioClub.add(getTxtFDniForm());
			panel_formularioClub.add(getBtnInscribirFormulario());
			panel_formularioClub.add(getTableFormulario());
			panel_formularioClub.add(getBtnTerminar());
			panel_formularioClub.add(getLblErrorForm());
			panel_formularioClub.add(getLblCompeticionActualForm());
			panel_formularioClub.add(getTxtFCompeticionForm());
			panel_formularioClub.add(getScrollPaneFormulario());
			panel_formularioClub.add(getBtnBorrarAtletaDeFormulario());
			panel_formularioClub.add(getTxtPlazasDispnibles());
			panel_formularioClub.add(getLblPlazasDisponiblesForm());
		}
		return panel_formularioClub;
	}
	
	public JDateChooser getFechaFinal() {
		if(fechaFinal == null) {
			
			fechaFinal = new JDateChooser();
			fechaFinal.setBounds(597, 83, 161, 24);
			
		}
		return fechaFinal;
	}
	public JLabel getLblNombreClub() {
		if (lblNombreClub == null) {
			lblNombreClub = new JLabel("Nombre del Club:");
			lblNombreClub.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNombreClub.setBounds(10, 10, 131, 24);
		}
		return lblNombreClub;
	}
	public JTextField getTxtFNombreClub() {
		if (txtFNombreClub == null) {
			txtFNombreClub = new JTextField();
			txtFNombreClub.setBounds(151, 10, 211, 24);
			txtFNombreClub.setColumns(10);
		}
		return txtFNombreClub;
	}
	public JTextField getTxtFNombreAtl() {
		if (txtFNombreAtl == null) {
			txtFNombreAtl = new JTextField();
			txtFNombreAtl.setBounds(151, 83, 211, 24);
			txtFNombreAtl.setColumns(10);
		}
		return txtFNombreAtl;
	}
	public JLabel getLblNombreAtl() {
		if (lblNombreAtl == null) {
			lblNombreAtl = new JLabel("Nombre Atleta:");
			lblNombreAtl.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNombreAtl.setBounds(10, 83, 131, 24);
		}
		return lblNombreAtl;
	}
	public JLabel getLblApellido() {
		if (lblApellido == null) {
			lblApellido = new JLabel("Apellido:");
			lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblApellido.setBounds(10, 132, 131, 24);
		}
		return lblApellido;
	}
	public JLabel getLblEmailFormulario() {
		if (lblEmailFormulario == null) {
			lblEmailFormulario = new JLabel("Email:");
			lblEmailFormulario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEmailFormulario.setBounds(10, 183, 131, 24);
		}
		return lblEmailFormulario;
	}
	public JTextField getTxtFApellido() {
		if (txtFApellido == null) {
			txtFApellido = new JTextField();
			txtFApellido.setBounds(151, 132, 211, 24);
			txtFApellido.setColumns(10);
		}
		return txtFApellido;
	}
	public JComboBox<String> getCBoxSexo() {
		if (cBoxSexo == null) {
			cBoxSexo = new JComboBox<String>();
			cBoxSexo.setBounds(151, 233, 131, 24);
			cBoxSexo.addItem("masculino");
			cBoxSexo.addItem("femenino");
		}
		return cBoxSexo;
	}
	public JLabel getLblSexoFormulario() {
		if (lblSexoFormulario == null) {
			lblSexoFormulario = new JLabel("Sexo:");
			lblSexoFormulario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSexoFormulario.setBounds(10, 233, 131, 24);
		}
		return lblSexoFormulario;
	}
	public JLabel getLblFechaDeNacimiento() {
		if (lblFechaDeNacimiento == null) {
			lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento");
			lblFechaDeNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblFechaDeNacimiento.setBounds(436, 83, 151, 24);
		}
		return lblFechaDeNacimiento;
	}
	public JTextField getTxtFEmailFormulario() {
		if (txtFEmailFormulario == null) {
			txtFEmailFormulario = new JTextField();
			txtFEmailFormulario.setBounds(151, 188, 211, 24);
			txtFEmailFormulario.setColumns(10);
		}
		return txtFEmailFormulario;
	}
	public JLabel getLblDniForm() {
		if (lblDniForm == null) {
			lblDniForm = new JLabel("DNI:");
			lblDniForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblDniForm.setBounds(436, 132, 58, 24);
		}
		return lblDniForm;
	}
	public JTextField getTxtFDniForm() {
		if (txtFDniForm == null) {
			txtFDniForm = new JTextField();
			txtFDniForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtFDniForm.setBounds(487, 132, 271, 24);
			txtFDniForm.setColumns(10);
		}
		return txtFDniForm;
	}
	public JButton getBtnInscribirFormulario() {
		if (btnInscribirFormulario == null) {
			btnInscribirFormulario = new JButton("Añadir");
			btnInscribirFormulario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnInscribirFormulario.setBounds(436, 233, 131, 24);
		}
		return btnInscribirFormulario;
	}
	public JTable getTableFormulario() {
		if (tableFormulario == null) {
			tableFormulario = new JTable();
			tableFormulario.setBounds(10, 341, 858, 219);
		}
		return tableFormulario;
	}
	public JButton getBtnTerminar() {
		if (btnTerminar == null) {
			btnTerminar = new JButton("Terminar");
			btnTerminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnTerminar.setBounds(617, 231, 131, 24);
		}
		return btnTerminar;
	}
	public JLabel getLblErrorForm() {
		if (lblErrorForm == null) {
			lblErrorForm = new JLabel("Error:");
			lblErrorForm.setForeground(Color.RED);
			lblErrorForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblErrorForm.setBounds(22, 297, 701, 24);
		}
		return lblErrorForm;
	}
	public JLabel getLblCompeticionActualForm() {
		if (lblCompeticionActualForm == null) {
			lblCompeticionActualForm = new JLabel("Se est\u00E1 inscribiendo en competici\u00F3n:");
			lblCompeticionActualForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCompeticionActualForm.setBounds(391, 10, 250, 24);
		}
		return lblCompeticionActualForm;
	}
	public JTextField getTxtFCompeticionForm() {
		if (txtFCompeticionForm == null) {
			txtFCompeticionForm = new JTextField();
			txtFCompeticionForm.setHorizontalAlignment(SwingConstants.CENTER);
			txtFCompeticionForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtFCompeticionForm.setEditable(false);
			txtFCompeticionForm.setBounds(665, 10, 93, 24);
			txtFCompeticionForm.setColumns(10);
		}
		return txtFCompeticionForm;
	}
	private JScrollPane getScrollPaneFormulario() {
		if (scrollPaneFormulario == null) {
			scrollPaneFormulario = new JScrollPane(getTableFormulario());
			scrollPaneFormulario.setBounds(22, 341, 831, 200);
		}
		return scrollPaneFormulario;
	}
	public JButton getBtnBorrarAtletaDeFormulario() {
		if (btnBorrarAtletaDeFormulario == null) {
			btnBorrarAtletaDeFormulario = new JButton("Borrar Atleta");
			btnBorrarAtletaDeFormulario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnBorrarAtletaDeFormulario.setBounds(734, 297, 119, 24);
		}
		return btnBorrarAtletaDeFormulario;
	}
	public JTextField getTxtPlazasDispnibles() {
		if (txtPlazasDispnibles == null) {
			txtPlazasDispnibles = new JTextField();
			txtPlazasDispnibles.setHorizontalAlignment(SwingConstants.CENTER);
			txtPlazasDispnibles.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPlazasDispnibles.setEditable(false);
			txtPlazasDispnibles.setBounds(665, 45, 93, 24);
			txtPlazasDispnibles.setColumns(10);
		}
		return txtPlazasDispnibles;
	}
	private JLabel getLblPlazasDisponiblesForm() {
		if (lblPlazasDisponiblesForm == null) {
			lblPlazasDisponiblesForm = new JLabel("Plazas disponibles:");
			lblPlazasDisponiblesForm.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblPlazasDisponiblesForm.setBounds(391, 45, 239, 24);
		}
		return lblPlazasDisponiblesForm;
		
	}


	public JButton getBtInformacionContable() {
		if (btInformacionContable == null) {
			btInformacionContable = new JButton("Ver estado contable");
			btInformacionContable.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btInformacionContable.setBounds(460, 495, 170, 35);
		}
		return btInformacionContable;
	}

	private JPanel getPanel_contabilidad() {
		if (panel_contabilidad == null) {
			panel_contabilidad = new JPanel();
			panel_contabilidad.setLayout(null);
			panel_contabilidad.add(getLbNombeCompeticion());
			panel_contabilidad.add(getLbNombeNombreCompeticion());
			panel_contabilidad.add(getLbFecha());
			panel_contabilidad.add(getScrollPane_2());
		}
		return panel_contabilidad;
	}

	private JLabel getLbNombeCompeticion() {
		if (lbNombeCompeticion == null) {
			lbNombeCompeticion = new JLabel("Balance carrera: ");
			lbNombeCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbNombeCompeticion.setBounds(32, 11, 173, 35);
		}
		return lbNombeCompeticion;
	}

	public JLabel getLbNombeNombreCompeticion() {
		if (lbNombeNombreCompeticion == null) {
			lbNombeNombreCompeticion = new JLabel("");
			lbNombeNombreCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lbNombeNombreCompeticion.setBounds(232, 11, 584, 35);
		}
		return lbNombeNombreCompeticion;
	}

	public JLabel getLbFecha() {
		if (lbFecha == null) {
			lbFecha = new JLabel("");
			lbFecha.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lbFecha.setBounds(32, 73, 173, 28);
		}
		return lbFecha;
	}

	private JScrollPane getScrollPane_2() {
		if (scrollPane_2 == null) {
			scrollPane_2 = new JScrollPane((Component) null);
			scrollPane_2.setBounds(10, 118, 846, 411);
			scrollPane_2.setViewportView(getTablaContabilidad());
		}
		return scrollPane_2;
	}

	public JTable getTablaContabilidad() {
		if (tablaContabilidad == null) {
			tablaContabilidad = new JTable();
		}
		return tablaContabilidad;
	}

	public JPanel getPanel_perfilAtleta() {
		if (panel_perfilAtleta == null) {
			panel_perfilAtleta = new JPanel();
			panel_perfilAtleta.setLayout(new CardLayout(0, 0));
			panel_perfilAtleta.add(getPanel_perfil(), "perfil");
			panel_perfilAtleta.add(getPanel_comparar(), "otrosAtletas");
			panel_perfilAtleta.add(getPanel_Comparativa(), "comparacion");
		}
		return panel_perfilAtleta;
	}

	private JPanel getPanel_perfil() {
		if (panel_perfil == null) {
			panel_perfil = new JPanel();
			panel_perfil.setLayout(null);
			panel_perfil.add(getScrollPane_inscripciones());

			panel_perfil.add(getBtnMisInscripciones());
			panel_perfil.add(getBtnCambiarUsuario());
			panel_perfil.add(getLblCompeticionSeleccionada());
			panel_perfil.add(getLblMostrarAtletas());
			panel_perfil.add(getBtnMostrarAtletas());
			panel_perfil.add(getLblCancelarInscripcion());
			panel_perfil.add(getBtnCancelarInscripcion());
			panel_perfil.add(getLblErrorPerfil());
		}
		return panel_perfil;
	}

	private JPanel getPanel_comparar() {
		if (panel_comparar == null) {
			panel_comparar = new JPanel();
			panel_comparar.setLayout(null);

			panel_comparar.add(getScrollPane_AtletasCompSeleccionada());
			panel_comparar.add(getBtnVolverPerfil());
			panel_comparar.add(getLblNombreCompeticion());
			panel_comparar.add(getLblCompara());
			panel_comparar.add(getLblAtletaSeleccionado());
			panel_comparar.add(getBtnCompararse());
		}
		return panel_comparar;
	}

	private JPanel getPanel_Comparativa() {
		if (panel_Comparativa == null) {
			panel_Comparativa = new JPanel();
			panel_Comparativa.setLayout(null);
			panel_Comparativa.add(getScrollPane_comparativa());
			panel_Comparativa.add(getLblNombreCompeticion2());
			panel_Comparativa.add(getBtnVolverPerfil2());

		}
		return panel_Comparativa;
	}

	private JScrollPane getScrollPane_inscripciones() {
		if (scrollPane_inscripciones == null) {
			scrollPane_inscripciones = new JScrollPane(getTableInscripciones());
			scrollPane_inscripciones.setBounds(10, 83, 846, 280);
		}
		return scrollPane_inscripciones;
	}

	public JTable getTableAsignar() {
		if (tableAsignar == null) {
			tableAsignar = new JTable();
			tableAsignar.setBounds(681, 108, -510, 201);

		}
		return tableAsignar;
	}

	public JButton getBtnMisInscripciones() {
		if (btnMisInscripciones == null) {
			btnMisInscripciones = new JButton("Mostrar mis inscricpiones");
			btnMisInscripciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnMisInscripciones.setBounds(10, 32, 196, 37);
		}
		return btnMisInscripciones;
	}

	public JButton getBtnCambiarUsuario() {
		if (btnCambiarUsuario == null) {
			btnCambiarUsuario = new JButton("Cambiar usuario");
			btnCambiarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnCambiarUsuario.setBounds(693, 32, 163, 37);
		}
		return btnCambiarUsuario;
	}

	public JLabel getLblCompeticionSeleccionada() {
		if (lblCompeticionSeleccionada == null) {
			lblCompeticionSeleccionada = new JLabel("");
			lblCompeticionSeleccionada.setForeground(new Color(51, 153, 255));
			lblCompeticionSeleccionada.setFont(new Font("Tahoma", Font.ITALIC, 23));
			lblCompeticionSeleccionada.setBounds(10, 374, 433, 37);
		}
		return lblCompeticionSeleccionada;
	}

	private JLabel getLblMostrarAtletas() {
		if (lblMostrarAtletas == null) {
			lblMostrarAtletas = new JLabel("Mostrar atletas de la competici\u00F3n seleccionada:");
			lblMostrarAtletas.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblMostrarAtletas.setBounds(10, 414, 383, 51);
		}
		return lblMostrarAtletas;
	}

	public JButton getBtnMostrarAtletas() {
		if (btnMostrarAtletas == null) {
			btnMostrarAtletas = new JButton("Mostrar");
			btnMostrarAtletas.setEnabled(false);
			btnMostrarAtletas.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnMostrarAtletas.setBounds(424, 422, 117, 29);
		}
		return btnMostrarAtletas;
	}

	private JLabel getLblCancelarInscripcion() {
		if (lblCancelarInscripcion == null) {
			lblCancelarInscripcion = new JLabel("Cancelar inscripci\u00F3n de la competici\u00F3n seleccionada:");
			lblCancelarInscripcion.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblCancelarInscripcion.setBounds(10, 476, 394, 40);
		}
		return lblCancelarInscripcion;
	}

	public JButton getBtnCancelarInscripcion() {
		if (btnCancelarInscripcion == null) {
			btnCancelarInscripcion = new JButton("Cancelar");
			btnCancelarInscripcion.setEnabled(false);
			btnCancelarInscripcion.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnCancelarInscripcion.setBounds(424, 482, 117, 29);
		}
		return btnCancelarInscripcion;
	}

	public JLabel getLblErrorPerfil() {
		if (lblErrorPerfil == null) {
			lblErrorPerfil = new JLabel("Error:");
			lblErrorPerfil.setForeground(new Color(255, 0, 0));
			lblErrorPerfil.setBackground(new Color(204, 0, 0));
			lblErrorPerfil.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblErrorPerfil.setBounds(10, 527, 612, 19);
		}
		return lblErrorPerfil;
	}

	private JScrollPane getScrollPane_AtletasCompSeleccionada() {
		if (scrollPane_AtletasCompSeleccionada == null) {
			scrollPane_AtletasCompSeleccionada = new JScrollPane(getTableAtletasDeCompSeleccionada());
			scrollPane_AtletasCompSeleccionada.setBounds(10, 62, 846, 280);
		}
		return scrollPane_AtletasCompSeleccionada;
	}

	public JTable getTableAtletasDeCompSeleccionada() {
		if (tableAtletasDeCompSeleccionada == null) {
			tableAtletasDeCompSeleccionada = new JTable();
			tableAtletasDeCompSeleccionada.setBounds(10, 62, 846, 280);
		}
		return tableAtletasDeCompSeleccionada;
	}

	private JScrollPane getScrollPane_comparativa() {
		if (scrollPane_comparativa == null) {
			scrollPane_comparativa = new JScrollPane(getTableComparativa());
			scrollPane_comparativa.setBounds(10, 68, 846, 280);
		}
		return scrollPane_comparativa;
	}

	public JTable getTableComparativa() {
		if (tableComparativa == null) {
			tableComparativa = new JTable();
			tableComparativa.setBounds(10, 82, 846, 280);
		}
		return tableComparativa;
	}

	public JTable getTableInscripciones() {
		if (tableInscripciones == null) {
			tableInscripciones = new JTable();
			tableInscripciones.setBounds(0, 0, 1, 1);
		}
		return tableInscripciones;
	}

	public JButton getBtnVolverPerfil() {
		if (btnVolverPerfil == null) {
			btnVolverPerfil = new JButton("Volver");
			btnVolverPerfil.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnVolverPerfil.setBounds(731, 490, 96, 26);
		}
		return btnVolverPerfil;
	}

	public JLabel getLblNombreCompeticion() {
		if (lblNombreCompeticion == null) {
			lblNombreCompeticion = new JLabel("NOMBRE COMPETICION");
			lblNombreCompeticion.setForeground(new Color(51, 153, 255));
			lblNombreCompeticion.setFont(new Font("Tahoma", Font.ITALIC, 26));
			lblNombreCompeticion.setBounds(10, 11, 493, 45);
		}
		return lblNombreCompeticion;
	}

	public JLabel getLblNombreCompeticion2() {
		if (lblNombreCompeticion2 == null) {
			lblNombreCompeticion2 = new JLabel("NOMBRE COMPETICI\u00D3N");
			lblNombreCompeticion2.setForeground(new Color(51, 153, 255));
			lblNombreCompeticion2.setFont(new Font("Tahoma", Font.ITALIC, 27));
			lblNombreCompeticion2.setBounds(10, 23, 400, 36);
		}
		return lblNombreCompeticion2;
	}

	public JButton getBtnVolverPerfil2() {
		if (btnVolverPerfil2 == null) {
			btnVolverPerfil2 = new JButton("Volver ");
			btnVolverPerfil2.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVolverPerfil2.setBounds(697, 484, 141, 36);
		}
		return btnVolverPerfil2;
	}

	private JLabel getLblCompara() {
		if (lblCompara == null) {
			lblCompara = new JLabel("Compararse con");
			lblCompara.setFont(new Font("Dialog", Font.PLAIN, 30));
			lblCompara.setBounds(10, 371, 231, 45);
		}
		return lblCompara;
	}

	public JLabel getLblAtletaSeleccionado() {
		if (lblAtletaSeleccionado == null) {
			lblAtletaSeleccionado = new JLabel("nombre atleta");
			lblAtletaSeleccionado.setForeground(new Color(102, 153, 255));
			lblAtletaSeleccionado.setFont(new Font("Dialog", Font.PLAIN, 25));
			lblAtletaSeleccionado.setBounds(239, 371, 264, 45);
		}
		return lblAtletaSeleccionado;
	}

	public JButton getBtnCompararse() {
		if (btnCompararse == null) {
			btnCompararse = new JButton("Comparar");
			btnCompararse.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnCompararse.setBounds(508, 379, 124, 38);
		}
		return btnCompararse;
	}

	public void resetearCamposYVistasPerfilAtleta() {

		getLblCompeticionSeleccionada().setText("");
		getLblAtletaSeleccionado().setText("");
		getLblNombreCompeticion().setText("");
		getLblNombreCompeticion2().setText("");
		getBtnMisInscripciones().setVisible(true);

		((DefaultTableModel) getTableInscripciones().getModel()).setRowCount(0);
		((DefaultTableModel) getTableAtletasDeCompSeleccionada().getModel()).setRowCount(0);
		((DefaultTableModel) getTableComparativa().getModel()).setRowCount(0);
		getTableInscripciones().setVisible(false);
		getTableAtletasDeCompSeleccionada().setVisible(false);
		getTableComparativa().setVisible(false);
		// para que la próxima vez que se abra un perfil se vea la visión de
		// perfil
		((CardLayout) getPanel_perfilAtleta().getLayout()).show(getPanel_perfilAtleta(), "perfil");


	}
}
