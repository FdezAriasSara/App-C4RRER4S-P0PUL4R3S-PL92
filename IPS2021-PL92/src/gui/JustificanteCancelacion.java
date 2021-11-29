package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JustificanteCancelacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblCancelado;
	private MainWindow mainWindow;
	private JLabel lblRellenarConNombreYApellidos;
	private JLabel lblCuotaDeInscripcion;
	private JLabel lblFecha;
	private JLabel lblADevolver;
	private JLabel lblInfoCompeticion;

	/**
	 * Create the dialog.
	 */
	public JustificanteCancelacion(MainWindow main) {
		setVisible(false);
		setResizable(false);
		setModal(true);
		this.mainWindow = main;
		setTitle("IPS2021-PL92:Justificante cancelacion");
		setBounds(100, 100, 461, 358);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblCancelado());
		contentPanel.add(getLblRellenarNombreYApellidos());
		contentPanel.add(getLblFecha());
		contentPanel.add(getLblADevolver());
		contentPanel.add(getLblInfoCompeticion());
		contentPanel.add(getLblCuotaDeInscripcion());
		getLblInfoCompeticion();
		{
			JLabel lbNombreAtleta = new JLabel("Inscripción a nombre de :");
			lbNombreAtleta.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lbNombreAtleta.setBounds(28, 114, 147, 27);
			contentPanel.add(lbNombreAtleta);
		}
		getLblRellenarNombreYApellidos();
		getLblADevolver();
		getLblCuotaDeInscripcion();
		getLblFecha();
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public JLabel getLblInfoCompeticion() {
		if (lblInfoCompeticion == null) {
			this.lblInfoCompeticion = new JLabel(
					"Se ha cancelado su inscripción en \"nombre de competicion\"");
			lblInfoCompeticion.setFont(new Font("Tahoma", Font.ITALIC, 12));
			lblInfoCompeticion.setHorizontalAlignment(SwingConstants.CENTER);
			lblInfoCompeticion.setBounds(10, 57, 414, 34);

		}
		return lblInfoCompeticion;
	}

	public JLabel getLblADevolver() {
		if (lblADevolver == null) {
			this.lblADevolver = new JLabel(
					"Se le devolverá un % de dicho importe.");
			lblADevolver.setVisible(false);
			lblADevolver.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblADevolver.setBounds(28, 184, 350, 27);

		}
		return lblADevolver;
	}

	public JLabel getLblFecha() {
		if (lblFecha == null) {
			this.lblFecha = new JLabel("a dia %dia  de %mes  de %año");
			lblFecha.setFont(new Font("Tahoma", Font.ITALIC, 13));
			lblFecha.setBounds(215, 240, 201, 14);

		}
		return lblFecha;
	}

	public JLabel getLblCuotaDeInscripcion() {
		if (lblCuotaDeInscripcion == null) {
			this.lblCuotaDeInscripcion = new JLabel("Usted pagó %d euros.");
			lblCuotaDeInscripcion.setVisible(false);
			lblCuotaDeInscripcion.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblCuotaDeInscripcion.setBounds(28, 159, 181, 14);

		}
		return lblCuotaDeInscripcion;
	}

	public JLabel getLblRellenarNombreYApellidos() {
		if (lblRellenarConNombreYApellidos == null) {
			this.lblRellenarConNombreYApellidos = new JLabel("");
			lblRellenarConNombreYApellidos.setBounds(177, 114, 234, 27);

		}
		return lblRellenarConNombreYApellidos;
	}

	private JLabel getLblCancelado() {
		if (lblCancelado == null) {
			lblCancelado = new JLabel(
					"Su inscripción ha sido cancelada con éxito.");
			lblCancelado.setHorizontalAlignment(SwingConstants.CENTER);
			lblCancelado.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblCancelado.setBounds(10, 22, 414, 34);
		}
		return lblCancelado;
	}
}
