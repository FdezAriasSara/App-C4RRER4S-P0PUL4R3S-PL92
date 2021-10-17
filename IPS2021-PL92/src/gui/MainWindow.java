package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

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
	private JTextPane txtPCompeticiones;
	private JButton btnListarCompeticionesAbiertas;
	private JTextField txtFIDCompeticion;
	private JLabel lblInscribirse;
	private JPanel panel_default;
	private JLabel lblBienvenida;
	private JLabel lblSeleccion;
	private JButton btnAtletaBienvenida;
	private JButton btnOrganizadorBienvenida;
	private JLabel lblEmailAtleta;
	private JTextField txtFEmail;
	private JButton btnInscribirse;
	private JLabel lblError;
	private JButton btnRegistroAtleta;
	private JLabel lblIDCompeticionOrg;
	private JTextField txtIDCompOrg;
	private JLabel lblNewLabel;
	private JComboBox<String> cbCategoria;
	private JButton btnGenerarClasificacion;
	private JLabel lblErrorOrg;
	private JTextPane txtPClasificacion;
	private JPanel panel_pago;
	private JButton btTarjeta;
	private JButton btTransferencia;
	private JButton btnObtenerAtletas;

	
	/**
	 * Create the frame.
	 */
	public MainWindow() {
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
			btnVolverBienvenida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					((CardLayout)getPanel_card().getLayout()).show(getPanel_card(), "Pg1");
					getTxtPCompeticiones().setText("");
					getTxtPClasificacion().setText("");
					btnVolverBienvenida.setEnabled(false);
				}
			});
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
			
		}
		return panel_card;
	}
	public JPanel getPanel_atleta() {
		if (panel_atleta == null) {
			panel_atleta = new JPanel();
			panel_atleta.setLayout(null);
			panel_atleta.add(getTxtPCompeticiones());
			panel_atleta.add(getBtnListarCompeticionesAbiertas());
			panel_atleta.add(getTxtFIDCompeticion());
			panel_atleta.add(getLblInscribirse());
			panel_atleta.add(getLblEmailAtleta());
			panel_atleta.add(getTxtFEmail());
			panel_atleta.add(getBtnInscribirse());
			panel_atleta.add(getLblError());
			panel_atleta.add(getBtnRegistroAtleta());
		}
		return panel_atleta;
	}
	public JPanel getPanel_organizador() {
		if (panel_organizador == null) {
			panel_organizador = new JPanel();
			panel_organizador.setLayout(null);
			panel_organizador.add(getLblIDCompeticionOrg());
			panel_organizador.add(getTxtIDCompOrg());
			panel_organizador.add(getLblNewLabel());
			panel_organizador.add(getCbCategoria());
			panel_organizador.add(getBtnGenerarClasificacion());
			panel_organizador.add(getLblErrorOrg());
			panel_organizador.add(getTxtPClasificacion());
			panel_organizador.add(getBtnObtenerAtletas());
		}
		return panel_organizador;
	}
	public JTextPane getTxtPCompeticiones() {
		if (txtPCompeticiones == null) {
			txtPCompeticiones = new JTextPane();
			txtPCompeticiones.setEditable(false);
			txtPCompeticiones.setBounds(10, 54, 848, 307);
		}
		return txtPCompeticiones;
	}
	public JButton getBtnListarCompeticionesAbiertas() {
		if (btnListarCompeticionesAbiertas == null) {
			btnListarCompeticionesAbiertas = new JButton("Listar Competiciones");
			
			
			btnListarCompeticionesAbiertas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnListarCompeticionesAbiertas.setBounds(10, 10, 205, 34);
		}
		return btnListarCompeticionesAbiertas;
	}
	public JTextField getTxtFIDCompeticion() {
		if (txtFIDCompeticion == null) {
			txtFIDCompeticion = new JTextField();
			txtFIDCompeticion.setHorizontalAlignment(SwingConstants.CENTER);
			txtFIDCompeticion.setFont(new Font("Dialog", Font.PLAIN, 15));
			txtFIDCompeticion.setBounds(327, 377, 51, 29);
			txtFIDCompeticion.setColumns(10);
		}
		return txtFIDCompeticion;
	}
	public JLabel getLblInscribirse() {
		if (lblInscribirse == null) {
			lblInscribirse = new JLabel("ID Competicion en la que te quieres inscribir:");
			lblInscribirse.setFont(new Font("Dialog", Font.PLAIN, 15));
			lblInscribirse.setBounds(10, 374, 307, 34);
		}
		return lblInscribirse;
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
					((CardLayout)getPanel_card().getLayout()).show(getPanel_card(), "Pg2");
					
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
					((CardLayout)getPanel_card().getLayout()).show(getPanel_card(), "Pg3");
					
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
			lblError = new JLabel("Error: Ya estabas inscrito en dicha competicion");
			lblError.setVisible(false);
			lblError.setForeground(Color.RED);
			lblError.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblError.setBounds(10, 462, 558, 34);
		}
		return lblError;
	}
	public JButton getBtnRegistroAtleta() {
		if (btnRegistroAtleta == null) {
			btnRegistroAtleta = new JButton("Registrarse");
			btnRegistroAtleta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnRegistroAtleta.setBounds(653, 10, 205, 34);
		}
		return btnRegistroAtleta;
	}
	public JLabel getLblIDCompeticionOrg() {
		if (lblIDCompeticionOrg == null) {
			lblIDCompeticionOrg = new JLabel("Introduzca el ID de la competicion:");
			lblIDCompeticionOrg.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblIDCompeticionOrg.setBounds(10, 314, 246, 35);
		}
		return lblIDCompeticionOrg;
	}
	public JTextField getTxtIDCompOrg() {
		if (txtIDCompOrg == null) {
			txtIDCompOrg = new JTextField();
			txtIDCompOrg.setHorizontalAlignment(SwingConstants.CENTER);
			txtIDCompOrg.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIDCompOrg.setBounds(254, 314, 50, 29);
			txtIDCompOrg.setColumns(10);
		}
		return txtIDCompOrg;
	}
	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Categor\u00EDa (sexo):");
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
			
			cbCategoria.addItem("masculino");
			cbCategoria.addItem("femenino");
		}
		return cbCategoria;
	}
	public JButton getBtnGenerarClasificacion() {
		if (btnGenerarClasificacion == null) {
			btnGenerarClasificacion = new JButton("Generar clasificacion");
			
			btnGenerarClasificacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnGenerarClasificacion.setBounds(10, 404, 294, 35);
		}
		return btnGenerarClasificacion;
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
	public JTextPane getTxtPClasificacion() {
		if (txtPClasificacion == null) {
			txtPClasificacion = new JTextPane();
			txtPClasificacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPClasificacion.setEditable(false);
			txtPClasificacion.setBounds(10, 10, 858, 294);
		}
		return txtPClasificacion;
	}

	private JPanel getPanel_pago() {
		if (panel_pago == null) {
			panel_pago = new JPanel();
			panel_pago.setLayout(null);
			
			JLabel lbEligeTipoPago = new JLabel("Elija tipo de pago");
			lbEligeTipoPago.setFont(new Font("Tahoma", Font.BOLD, 20));
			lbEligeTipoPago.setHorizontalAlignment(SwingConstants.CENTER);
			lbEligeTipoPago.setBounds(253, 11, 336, 56);
			panel_pago.add(lbEligeTipoPago);
			panel_pago.add(getBtTarjeta());
			panel_pago.add(getBtTransferencia());
		}
		return panel_pago;
	}
	public JButton getBtTarjeta() {
		if (btTarjeta == null) {
			btTarjeta = new JButton("Tarjeta");
			btTarjeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btTarjeta.setBounds(191, 427, 134, 37);
		}
		return btTarjeta;
	}
	public JButton getBtTransferencia() {
		if (btTransferencia == null) {
			btTransferencia = new JButton("Transferencia");
			btTransferencia.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btTransferencia.setBounds(476, 427, 134, 37);
		}
		return btTransferencia;
	}

	public JButton getBtnObtenerAtletas() {
		if (btnObtenerAtletas == null) {
			btnObtenerAtletas = new JButton("Obtener Atletas");
			btnObtenerAtletas.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnObtenerAtletas.setBounds(458, 404, 294, 35);
		}
		return btnObtenerAtletas;
	}
}
