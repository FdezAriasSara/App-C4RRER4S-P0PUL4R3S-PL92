package uo.ips.application.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionDto;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class inscribirUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inscribirUsuario frame = new inscribirUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public inscribirUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panelEmail = new JPanel();
		contentPane.add(panelEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelEmail.add(lblEmail);
		
		textField = new JTextField();
		panelEmail.add(textField);
		textField.setColumns(10);
		
		JPanel panelCompeticion = new JPanel();
		contentPane.add(panelCompeticion);
		
		JLabel lblCompeticion = new JLabel("Competicion");
		lblCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelCompeticion.add(lblCompeticion);
		
		JComboBox<CompeticionDto> comboBoxCompeticiones = new JComboBox();
		panelCompeticion.add(comboBoxCompeticiones);
		
		JPanel panelInscribirse = new JPanel();
		contentPane.add(panelInscribirse);
		
		JButton btnInscribirse = new JButton("Inscribirse");
		btnInscribirse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelInscribirse.add(btnInscribirse);
		
		List<CompeticionDto> competiciones;
		try {
			competiciones = BusinessFactory.forCompeticionCrudService().ListarCompeticionesInscripcionesAbiertas();
			
			
			for (CompeticionDto competicionDto : competiciones) {
				comboBoxCompeticiones.addItem(competicionDto);			
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
