package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import gui.CrearCarrera;
import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;

public class CompeticionController {

	private MainWindow mainW;

	private CompeticionCrudService competicionModel = BusinessFactory.forCompeticionCrudService() ;
	
	

	public CompeticionController(MainWindow main) {
		this.mainW = main;

		this.initActions();
	}

	private void initActions() {

		mainW.getBtnListarCompeticionesAbiertas().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initBotonListarComp();
			}

		});

		mainW.getBtMostrarCompeticiones().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initBotonListarComp2();
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
	
	

	private void initBotonListarComp2() {

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

		mainW.getTablaClasificacion().setModel(model);

	}

}
