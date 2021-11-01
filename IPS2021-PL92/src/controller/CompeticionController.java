package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		
		
		
		
		
	}
	
	
	private void initBotonListarComp() {
		
		List<CompeticionDto> competiciones = new ArrayList<CompeticionDto>();
		String allComp = "";
		
		String[] columnNames = { "ID", "Nombre", "Fecha Competicion", "Organizador", "Tipo" , "KM" , "Plazas disponibles" ,
				"Inicio inscripcion","Fin inscripcion", "Cuota" };
		
		String[][] valuesToTable = new String[competiciones.size()][columnNames.length];
		
		TableModel model = new DefaultTableModel(valuesToTable,columnNames);
		
		try {
			competiciones = competicionModel.ListarCompeticionesInscripcionesAbiertas();
			
		} catch (BusinessException e1) {
			mainW.getLblError().setText("Problemas al listar las carreras");
		}
		
		int counter = 0;
		int col = 0;
		for(CompeticionDto c : competiciones) {
			
			valuesToTable[counter++][col++] = ""+c.idCompeticion;
			valuesToTable[counter++][col++] = ""+c.nombre;
			valuesToTable[counter++][col++] = ""+c.fechaCompeticion;
			valuesToTable[counter++][col++] = ""+c.organizador;
			valuesToTable[counter++][col++] = ""+c.tipoCompeticion;
			valuesToTable[counter++][col++] = ""+c.distanciaKm;
			valuesToTable[counter++][col++] = ""+c.plazasDisponibles;
			valuesToTable[counter++][col++] = ""+c.plazoInicioInscripcion;
			valuesToTable[counter++][col++] = ""+c.plazoFinInscripcion;
			valuesToTable[counter++][col++] = ""+c.cuota;
			
		}
		
		mainW.getTableCompeticion().setModel(model);
		
	
	}
	
	

	

}
