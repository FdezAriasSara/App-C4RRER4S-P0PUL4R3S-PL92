package controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gui.CrearCarrera;
import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.pago.ContabilidadDto;
import uo.ips.application.business.pago.crud.Contabilidad;

public class CompeticionController {

	private MainWindow mainW;

	private CompeticionCrudService competicionModel = BusinessFactory.forCompeticionCrudService();

	public CompeticionController(MainWindow main) {
		this.mainW = main;

		this.initActions();
	}

	private void initActions() {

		mainW.getBtnListarCompeticionesAbiertas().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initBotonListarComp();
				mainW.getBtnInscribirClubArch().setEnabled(true);
			}

		});

		mainW.getBtNuevaCompeticion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CrearCarrera frame = new CrearCarrera();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});

		mainW.getBtnMostrarTodasComp().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();

				try {
					competiciones = competicionModel.listarTodasCompeticiones();

				} catch (BusinessException e1) {
					mainW.getLblError().setText("Problemas al listar las carreras");
				}

				String[] columnNames = { "ID", "Nombre", "Fecha Competicion", "Organizador", "Tipo", "KM",
						"Plazas disponibles" };

				String[][] valuesToTable = new String[competiciones.size()][columnNames.length];

				int counter = 0;

				for (CompeticionDto c : competiciones) {
					int col = 0;
					valuesToTable[counter][col++] = "" + c.idCompeticion;
					valuesToTable[counter][col++] = "" + c.nombre;
					valuesToTable[counter][col++] = "" + c.fechaCompeticion;
					valuesToTable[counter][col++] = "" + c.organizador;
					valuesToTable[counter][col++] = "" + c.tipoCompeticion;
					valuesToTable[counter][col++] = "" + c.distanciaKm;
					valuesToTable[counter][col++] = "" + c.plazasDisponibles;
					counter++;
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

			}

		});

		mainW.getBtInformacionContable().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = mainW.getTablaClasificacion().getSelectedRow();
				int id = -1;
				String nombreCompeticion = "";

				if (fila > -1) {
					DefaultTableModel modelo = (DefaultTableModel) mainW.getTablaClasificacion().getModel();
					id = Integer.parseInt((String) modelo.getValueAt(fila, 0));
					nombreCompeticion = (String) modelo.getValueAt(fila, 1);
					mainW.getLbNombeNombreCompeticion().setText(nombreCompeticion);
					mainW.getLbFecha()
							.setText(LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

					initTablaContabilidad(id);

					((CardLayout) mainW.getPanel_card().getLayout()).show(mainW.getPanel_card(), "contabilidad");
				} else {
					JOptionPane.showMessageDialog(mainW, "Selecciona competición para ver estado contable", null,
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
	}

	private void initBotonListarComp() {

		List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();

		try {
			competiciones = competicionModel.ListarCompeticionesInscripcionesAbiertas();

		} catch (BusinessException e1) {
			mainW.getLblError().setText("Problemas al listar las carreras");
		}

		String[] columnNames = { "ID", "Nombre", "Fecha Competicion", "Organizador", "Tipo", "KM", "Plazas disponibles",
				"Inicio inscripcion", "Fin inscripcion", "Cuota" };

		String[][] valuesToTable = new String[competiciones.size()][columnNames.length];

		int counter = 0;

		for (CompeticionDto c : competiciones) {
			int col = 0;
			valuesToTable[counter][col++] = "" + c.idCompeticion;
			valuesToTable[counter][col++] = "" + c.nombre;
			valuesToTable[counter][col++] = "" + c.fechaCompeticion;
			valuesToTable[counter][col++] = "" + c.organizador;
			valuesToTable[counter][col++] = "" + c.tipoCompeticion;
			valuesToTable[counter][col++] = "" + c.distanciaKm;
			valuesToTable[counter][col++] = "" + c.plazasDisponibles;
			valuesToTable[counter][col++] = "" + c.plazoInicioInscripcion;
			valuesToTable[counter][col++] = "" + c.plazoFinInscripcion;
			valuesToTable[counter][col++] = "" + c.cuota;
			counter++;
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

	}

	private void initTablaContabilidad(int idCompeticion) {
		List<ContabilidadDto> contabilidades = new Contabilidad(idCompeticion).execute();

		String[] columnNames = { "Concepto", "Cantidad" };

		String[][] valuesToTable = new String[6][columnNames.length];

		valuesToTable[0][0] = "Atletas inscritos";
		valuesToTable[0][1] = contabilidades.get(0).cantidad + "";

		valuesToTable[1][0] = "Devoluciones";
		valuesToTable[1][1] = contabilidades.get(1).cantidad + "";

		valuesToTable[2][0] = "Ingresos";
		valuesToTable[2][1] = contabilidades.get(0).importe + "";

		valuesToTable[3][0] = "Importe total devoluciones";
		valuesToTable[3][1] = contabilidades.get(1).importe + "";

		double total = contabilidades.get(0).importe - contabilidades.get(1).importe;

		valuesToTable[5][0] = "Total";
		valuesToTable[5][1] = total + "";

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

		mainW.getTablaContabilidad().setModel(model);
	}

	private Color getColor(double total) {
		if (total > 0)
			return Color.GREEN;
		else
			return Color.RED;
	}
}
