package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuieresRegistrarte extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel_1;
	private JLabel lblWarning;
	private MainWindow mainWindow;

	/**
	 * Create the dialog.
	 */
	public QuieresRegistrarte(MainWindow mainW) {
		setVisible(false);
		setResizable(false);
		setModal(true);
		this.mainWindow = mainW;
		setTitle("Usuario no registrado");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("El email no est\u00E1 registrado.");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setBounds(163, 71, 230, 30);
			contentPanel.add(lblNewLabel);
		}
		contentPanel.add(getLblNewLabel_1());
		contentPanel.add(getLblWarning());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("S\u00ED");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "registro");
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("No");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("\u00BFQuieres registarte para continuar?");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabel_1.setBounds(163, 112, 212, 30);
		}
		return lblNewLabel_1;
	}

	private JLabel getLblWarning() {
		if (lblWarning == null) {
			lblWarning = new JLabel("");
			lblWarning.setIcon(new ImageIcon(QuieresRegistrarte.class.getResource("/img/warningIcon.png")));
			lblWarning.setBounds(10, 34, 143, 150);
		}
		return lblWarning;
	}
}
