package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.plazo.Plazo;
import uo.ips.application.business.plazo.PlazoDto;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.categoria.CategoriaDto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.Choice;
import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class CrearCarrera extends JFrame {

	private JPanel contentPane;
	private JLabel lblNombre;
	private JTextField txtFieldNombre;
	private JLabel lblTipo;
	private JComboBox comboBoxTipo;
	private JLabel lblDistancia;
	private JSpinner spinner;
	private JLabel lblFecha;
	private JScrollPane scrollPaneCategorias;
	private JPanel pnCategorias;
	private JLabel lblNewLabel;
	private JPanel pnFilas;
	private JButton btnCrearCategoria;
	private JButton btnCrearCompeticion;
	private JButton btnCrearPlazo;
	private JCalendar calendar;
	private JLabel lblOrganizador;
	private JTextField txtOrganizador;
	private JLabel lblCuentaBancaria;
	private JTextField txtCuentaBancaria;
	private JTabbedPane tabbedPane;
	private JPanel pnPlazos;
	private JScrollPane scrollPanePlazos;
	private JPanel pnFilasPlazos;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearCarrera frame = new CrearCarrera();
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
	public CrearCarrera() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 737);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNombre());
		contentPane.add(getTxtFieldNombre());
		contentPane.add(getLblTipo());
		contentPane.add(getComboBoxTipo());
		contentPane.add(getLblDistancia());
		contentPane.add(getSpinnerDistancia());
		contentPane.add(getLblFecha());
		// contentPane.add(getPnCategorias());
		contentPane.add(getBtnCrearCompeticion());
		contentPane.add(getCalendar());
		contentPane.add(getLblOrganizador());
		contentPane.add(getTxtOrganizador());
		contentPane.add(getLblCuentaBancaria());
		contentPane.add(getTxtCuentaBancaria());
		contentPane.add(getTabbedPane());
		contentPane.add(getLblError());
		// contentPane.add(getScrollPaneCategorias());

	}

	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNombre.setBounds(41, 26, 80, 27);
		}
		return lblNombre;
	}

	private JTextField getTxtFieldNombre() {
		if (txtFieldNombre == null) {
			txtFieldNombre = new JTextField();
			txtFieldNombre.setBounds(154, 31, 275, 19);
			txtFieldNombre.setColumns(10);
		}
		return txtFieldNombre;
	}

	private JLabel getLblTipo() {
		if (lblTipo == null) {
			lblTipo = new JLabel("Tipo");
			lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblTipo.setBounds(41, 63, 80, 27);
		}
		return lblTipo;
	}

	private JComboBox getComboBoxTipo() {
		if (comboBoxTipo == null) {
			comboBoxTipo = new JComboBox();
			comboBoxTipo.setBounds(154, 67, 88, 21);
			comboBoxTipo.addItem("Asfalto");
			comboBoxTipo.addItem("Monta�a");
		}
		return comboBoxTipo;
	}

	private JLabel getLblDistancia() {
		if (lblDistancia == null) {
			lblDistancia = new JLabel("Distancia");
			lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblDistancia.setBounds(41, 103, 80, 27);
		}
		return lblDistancia;
	}

	private JSpinner getSpinnerDistancia() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setBounds(154, 108, 50, 19);
		}
		return spinner;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblFecha.setBounds(556, 13, 80, 27);
		}
		return lblFecha;
	}

	private JScrollPane getScrollPaneCategorias() {
		if (scrollPaneCategorias == null) {
			scrollPaneCategorias = new JScrollPane(getPnFilas());
			scrollPaneCategorias.setBounds(41, 252, 609, 229);
		}
		return scrollPaneCategorias;
	}

	private JPanel getPnCategorias() {
		if (pnCategorias == null) {
			pnCategorias = new JPanel();
			pnCategorias.setBounds(41, 347, 687, 322);
			pnCategorias.setLayout(new BorderLayout(0, 0));
			pnCategorias.add(getLblNewLabel());
			pnCategorias.add(getBtnCrearCategoria(), BorderLayout.SOUTH);
			pnCategorias.add(getScrollPaneCategorias(), BorderLayout.CENTER);

		}
		return pnCategorias;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("New label");
		}
		return lblNewLabel;
	}

	private void addCategoria() {
		JPanel pn = new JPanel();
		JLabel lbNombre = new JLabel("Nombre");
		JTextField nombre = new JTextField(10);
		JLabel lbSexo = new JLabel("Sexo");
		JComboBox sexo = new JComboBox();
		sexo.addItem("Masculino");
		sexo.addItem("Femenino");
		sexo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkCategoriasVality();
			}
		});

		JLabel lbMin = new JLabel("Edad m�nima");
		JSpinner min = new JSpinner(new SpinnerNumberModel(18, 18, 200, 1));
		min.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if ((int) min.getValue() >= (int) ((JSpinner) min.getParent().getComponents()[7]).getValue()) {
					((JSpinner) min.getParent().getComponents()[7]).setValue((int) min.getValue() + 1);
				} else {
					try {
						min.commitEdit();
						checkCategoriasVality();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		JLabel lbMax = new JLabel("Edad m�xima");
		JSpinner max = new JSpinner(new SpinnerNumberModel(19, 19, 200, 1));

		max.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if ((int) max.getValue() <= (int) ((JSpinner) max.getParent().getComponents()[5]).getValue()) {
					max.setValue((int) ((JSpinner) max.getParent().getComponents()[5]).getValue() + 1);
				} else {
					try {
						max.commitEdit();
						checkCategoriasVality();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		pn.add(lbNombre);
		pn.add(nombre);
		pn.add(lbSexo);
		pn.add(sexo);
		pn.add(lbMin);
		pn.add(min);
		pn.add(lbMax);
		pn.add(max);
		pnFilas.add(pn);
		pnFilas.add(Box.createRigidArea(new Dimension(0, 10)));
		pn.setVisible(true);

	}

	private void addPlazo() {
		JPanel pn = new JPanel();

		JLabel lblcuota = new JLabel("Cuota");
		JSpinner cuota = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

		JLabel lblFechaInicio = new JLabel("Fecha incio");
		JDateChooser inicio = new JDateChooser();
		inicio.setPreferredSize(new Dimension(120, 20));
		inicio.setDate(new Date());
		inicio.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				checkPlazosVality();

			}
		});

		JLabel lblFechaFinal = new JLabel("Fecha final");
		JDateChooser fechaFinal = new JDateChooser();
		fechaFinal.setDate(new Date());
		fechaFinal.setPreferredSize(new Dimension(120, 20));
		fechaFinal.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				checkPlazosVality();
			}
		});

		pn.add(lblFechaInicio);
		pn.add(inicio);

		pn.add(lblFechaFinal);
		pn.add(fechaFinal);

		pn.add(lblcuota);
		pn.add(cuota);
		pn.setVisible(true);
		getPnFilasPlazos().add(pn);
		getPnFilasPlazos().add(Box.createRigidArea(new Dimension(0, 10)));

	}

	private JPanel getPnFilas() {
		if (pnFilas == null) {
			pnFilas = new JPanel();
			pnFilas.setLayout(new BoxLayout(pnFilas, BoxLayout.Y_AXIS));

		}

		return pnFilas;
	}

	private JButton getBtnCrearCategoria() {
		if (btnCrearCategoria == null) {
			btnCrearCategoria = new JButton("Nueva Categoria");
		}
		btnCrearCategoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCategoria();
				setVisible(true);
				checkCategoriasVality();

				System.out.println("Clicado");

			}
		});
		return btnCrearCategoria;
	}

	private JButton getBtnCrearPlazo() {
		if (btnCrearPlazo == null) {
			btnCrearPlazo = new JButton("Nuevo Plazo");
		}
		btnCrearPlazo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addPlazo();
				setVisible(true);

				System.out.println("Clicado");

			}
		});
		return btnCrearPlazo;
	}

	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(470, 50, 258, 183);
			calendar.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					checkPlazosVality();
					
				}
			});
		}
		return calendar;
	}

	private JButton getBtnCrearCompeticion() {
		if (btnCrearCompeticion == null) {
			btnCrearCompeticion = new JButton("Crear competici�n");
			btnCrearCompeticion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					crearCompeticion();
				}
			});
			btnCrearCompeticion.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnCrearCompeticion.setBounds(565, 634, 163, 37);

		}
		return btnCrearCompeticion;
	}

	private void crearCompeticion() {
		if (!checkAllFields()) {
			return;
		}
		if (!checkCategoriasVality() || !checkPlazosVality()) {
			return;
		}
		CompeticionDto competicion = new CompeticionDto();
		competicion.idCompeticion = -1;
		competicion.nombre = getTxtFieldNombre().getText();
		competicion.tipoCompeticion = getComboBoxTipo().getSelectedItem().toString();
		competicion.distanciaKm = Integer.parseInt(getSpinnerDistancia().getValue().toString());
		competicion.fechaCompeticion = new java.sql.Date(getCalendar().getDate().getTime());
		competicion.organizador = getTxtOrganizador().getText();
		competicion.plazos = new ArrayList<Plazo>();
		competicion.cuentaBancaria = getTxtCuentaBancaria().getText();

		List<CategoriaDto> categorias = createCategories();
		List<PlazoDto> plazos = createPlazos();

		try {
			BusinessFactory.forCompeticionCrudService().anadirCompeticion(competicion);

			for (CategoriaDto categoriaDto : categorias) {
				BusinessFactory.forCategoria().A�adirCategoria(categoriaDto, competicion.idCompeticion);
			}

			for (PlazoDto plazo : plazos) {
				plazo.idCompeticion = competicion.idCompeticion;
				BusinessFactory.forPlazo().addPlazo(plazo);
			}

			JOptionPane.showMessageDialog(this, "Competici�n creada correctamente");
			dispose();
		} catch (BusinessException e) {
			getLblError().setText(e.getMessage());
		}

	}

	private JLabel getLblOrganizador() {
		if (lblOrganizador == null) {
			lblOrganizador = new JLabel("Organizador");
			lblOrganizador.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblOrganizador.setBounds(41, 145, 80, 27);
		}
		return lblOrganizador;
	}

	private JTextField getTxtOrganizador() {
		if (txtOrganizador == null) {
			txtOrganizador = new JTextField();
			txtOrganizador.setColumns(10);
			txtOrganizador.setBounds(154, 150, 275, 19);
		}
		return txtOrganizador;
	}

	private JLabel getLblCuentaBancaria() {
		if (lblCuentaBancaria == null) {
			lblCuentaBancaria = new JLabel("Cuenta Bancaria");
			lblCuentaBancaria.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblCuentaBancaria.setBounds(41, 182, 103, 27);
		}
		return lblCuentaBancaria;
	}

	private JTextField getTxtCuentaBancaria() {
		if (txtCuentaBancaria == null) {
			txtCuentaBancaria = new JTextField();
			txtCuentaBancaria.setColumns(10);
			txtCuentaBancaria.setBounds(154, 187, 275, 19);
		}
		return txtCuentaBancaria;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(41, 243, 666, 361);

			tabbedPane.add(getPnCategorias());
			tabbedPane.add("Categorias", pnCategorias);

			tabbedPane.addTab("Plazos", null, getPnPlazos(), null);
		}
		return tabbedPane;
	}

	private JPanel getPnPlazos() {
		if (pnPlazos == null) {
			pnPlazos = new JPanel();
			pnPlazos.setLayout(new BorderLayout(0, 0));
			pnPlazos.add(getScrollPanePlazos(), BorderLayout.CENTER);
			pnPlazos.add(getBtnCrearPlazo(), BorderLayout.SOUTH);
		}
		return pnPlazos;
	}

	private JScrollPane getScrollPanePlazos() {
		if (scrollPanePlazos == null) {
			scrollPanePlazos = new JScrollPane(getPnFilasPlazos());
		}
		return scrollPanePlazos;
	}

	private JPanel getPnFilasPlazos() {
		if (pnFilasPlazos == null) {
			pnFilasPlazos = new JPanel();
			pnFilasPlazos.setLayout(new BoxLayout(pnFilasPlazos, BoxLayout.Y_AXIS));
		}
		return pnFilasPlazos;
	}

	private JLabel getLblError() {
		if (lblError == null) {
			lblError = new JLabel("");
			lblError.setForeground(Color.RED);
			lblError.setBounds(55, 634, 429, 19);
		}
		return lblError;
	}

	private boolean checkAllFields() {
		for (Component component : contentPane.getComponents()) {
			if (component instanceof JTextField && ((JTextField) component).getText().isEmpty()) {
				getLblError().setText("Tienes que rellenar todos los campos");
				return false;
			}
		}
		if (getPnFilas().getComponents().length == 0) {
			getLblError().setText("Tienes que a�adir al menos una categoria");
			return false;
		}

		if (getPnFilasPlazos().getComponents().length == 0) {
			getLblError().setText("Tienes que a�adir al menos un plazo");
			return false;
		}
		return true;
	}

	private List<CategoriaDto> createCategories() {
		List<CategoriaDto> lista = new ArrayList<CategoriaDto>();
		for (Component fila : getPnFilas().getComponents()) {
			if (fila instanceof JPanel) {
				CategoriaDto categoria = new CategoriaDto();
				JPanel panel = (JPanel) fila;
				Component[] components = panel.getComponents();
				categoria.nombreCategoria = ((JTextField) components[1]).getText();
				categoria.sexo = (String) ((JComboBox<String>) components[3]).getSelectedItem();
				categoria.edadMin = (int) ((JSpinner) components[5]).getValue();
				categoria.edadMax = (int) ((JSpinner) components[7]).getValue();
				lista.add(categoria);
			}
		}
		return lista;
	}

	private List<PlazoDto> createPlazos() {
		List<PlazoDto> lista = new ArrayList<PlazoDto>();
		for (Component fila : getPnFilasPlazos().getComponents()) {
			if (fila instanceof JPanel) {
				PlazoDto plazo = new PlazoDto();
				JPanel panel = (JPanel) fila;
				Component[] components = panel.getComponents();
				plazo.cuota = Double.parseDouble(((JSpinner) components[5]).getValue().toString());
				Date inicio = ((JDateChooser) components[1]).getDate();
				if (inicio != null) {
					plazo.fechaInicio = inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					;
				}
				Date fin = ((JDateChooser) components[3]).getDate();
				if (fin != null) {
					plazo.fechaFin = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					;
				}
				lista.add(plazo);
			}
		}
		return lista;
	}

	private void logLista(List<CategoriaDto> lista) {
		for (CategoriaDto categoriaDto : lista) {
			System.out.println(
					categoriaDto.nombreCategoria + categoriaDto.sexo + categoriaDto.edadMin + categoriaDto.edadMax);
		}
	}

	private boolean checkCategoriasVality() {
		List<CategoriaDto> ordenadas = new ArrayList<CategoriaDto>(createCategories());
		ordenadas.sort(Comparator.comparing(c -> c.edadMin));
		if(!ordenadas.stream().anyMatch(c -> c.sexo.contentEquals("Masculino") && c.edadMin == 18)) {
			getLblError().setText("Es necesaria al menos una categoria Masculina con m�nimo de edad de 18");
			return false;
		}
		
		if(!ordenadas.stream().anyMatch(c -> c.sexo.contentEquals("Femenino") && c.edadMin == 18)) {
			getLblError().setText("Es necesaria al menos una categoria Femenina con m�nimo de edad de 18");
			return false;
		}
		
		
		
		if (ordenadas.get(0).edadMin != 18) {
			getLblError().setText("Es necesario que una categoria tenda edad minima de 18 a�os");
			return false;
		}
		
		List<CategoriaDto> masculino = ordenadas.stream().filter(c -> c.sexo.contentEquals("Masculino")).collect(Collectors.toList());
		List<CategoriaDto> femenino = ordenadas.stream().filter(c -> c.sexo.contentEquals("Femenino")).collect(Collectors.toList());

		if(!checkSpaces(masculino))
			return false;
		if(!checkSpaces(femenino))
			return false;
		getLblError().setText("");
		return true;

	}
	
	private boolean checkSpaces(List<CategoriaDto> ordenadas) {
		for (int i = 0; i < ordenadas.size() - 1; i++) {
			int result = (ordenadas.get(i).edadMax - ordenadas.get(i + 1).edadMin);
			if (result >= 0) {
				getLblError().setText("Las categorias no se pueden solapar");
				return false;
			} else if (result < -1) {
				getLblError().setText("No puedes dejar huecos entre las categorias");
				return false;
			}
		}
		return true;
	}

	private boolean checkPlazosVality() {
		System.out.println("Checkeando plazos");
		List<PlazoDto> ordenadas = new ArrayList<PlazoDto>(createPlazos());
		
		
		
		ordenadas.sort(Comparator.comparing(c -> c.fechaInicio));
		for (PlazoDto plazoDto : ordenadas) {
			if (plazoDto.fechaFin.isBefore(plazoDto.fechaInicio) || plazoDto.fechaFin.isEqual(plazoDto.fechaInicio)) {
				getLblError().setText("La fecha de fin debe ser posterior a la de inicio");
				return false;
			}
			if (plazoDto.fechaFin
					.isAfter(getCalendar().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
				getLblError().setText("El plazo no puede acabar despues de la competici�n");
				return false;
			}
		}

		for (int i = 0; i < ordenadas.size() - 1; i++) {
			if (ordenadas.get(i).fechaFin.isBefore(ordenadas.get(i).fechaInicio)
					|| ordenadas.get(i).fechaFin.isEqual(ordenadas.get(i).fechaInicio)) {
				getLblError().setText("La fecha de fin debe ser posterior a la de inicio");
				return false;
			}
			long result = ChronoUnit.DAYS.between(ordenadas.get(i).fechaFin, ordenadas.get(i + 1).fechaInicio);
			if (result < 1) {
				getLblError().setText("Los plazos no se pueden solapar");
				return false;
			} else if (result > 1) {
				getLblError().setText("No puedes dejar huecos entre los plazos");
				return false;
			}
		}
		getLblError().setText("");
		return true;

	}

}
