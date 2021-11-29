package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.swing.JLabel;

import gui.JustificanteCancelacion;
import gui.MainWindow;
import uo.ips.application.business.BusinessException;
import uo.ips.application.business.BusinessFactory;
import uo.ips.application.business.Inscripcion.Estado;
import uo.ips.application.business.Inscripcion.InscripcionCrudService;
import uo.ips.application.business.Inscripcion.InscripcionDto;
import uo.ips.application.business.atleta.AtletaCrudService;
import uo.ips.application.business.atleta.AtletaDto;
import uo.ips.application.business.competicion.CompeticionCrudService;
import uo.ips.application.business.competicion.CompeticionDto;
import uo.ips.application.business.plazo.PlazoCrudService;

public class CancelacionController {
	private InscripcionCrudService inscCrud = BusinessFactory
			.forInscripcionCrudService();
	private AtletaCrudService atletaCrud = BusinessFactory
			.forAtletaCrudService();
	private CompeticionCrudService compCrud = BusinessFactory
			.forCompeticionCrudService();
	private MainWindow mainW;
	protected int idCompeticionSeleccionada;
	private ComparacionController cc;
	private JustificanteCancelacion justificante;
	private PlazoCrudService plazoCrud = BusinessFactory.forPlazoCrudService();
	private static final String PAGO_SIN_ABONAR = "Usted no había abonado el importe de la cuota. ";
	private static final String PAGO_ABONADO = "Usted pagó %f euros";
	private static final String INSCRIPCION_DE_CLUB = "Usted se inscribió através del club '%s'";
	private static final String ADEVOLVER = "Se le devolverá un %d de dicho importe.";
	private static final String NOMBRE_COMPETICION = "Se ha cancelado su inscripción en '%s'";
	private static final String FECHA = "a dia %d  de %s  de %d";
	private static final String NOMBRE_APELLIDOS = "%s %s";
	private static final String SACAR_LISTA_ESPERA = "Se le ha eliminado de la lista de espera.";

	public CancelacionController(MainWindow mainW,
			ComparacionController sesion) {
		this.cc = sesion;
		this.mainW = mainW;
		this.justificante = mainW.getJustificanteCancelacionDialog();
		this.initActions();
	}

	private void initActions() {
		this.mainW.getBtnCancelarInscripcion()
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						idCompeticionSeleccionada = Integer.parseInt(mainW
								.getTableInscripciones()
								.getValueAt(mainW.getTableInscripciones()
										.getSelectedRow(), 0)
								.toString());
						cancelarInscripcion();
						mostrarJustificanteDeCancelacion();

					}
				});

	}

	protected void mostrarJustificanteDeCancelacion() {
		try {

			AtletaDto atleta = atletaCrud
					.encontrarPorId(cc.sesion.getIdAtleta());
			InscripcionDto inscripcion = inscCrud.encontarInscripcion(
					cc.sesion.getIdAtleta(), idCompeticionSeleccionada);
			double cuota = plazoCrud.obtenerCuotaDeInscripcion(
					idCompeticionSeleccionada, inscripcion.fechaInscripcion);
			rellenarDatosAtleta(atleta);
			rellenarDatosClub(inscripcion);// solo se rellenan si hay club
			Stream<CompeticionDto> c = compCrud.listarTodasCompeticiones()
					.stream().filter((
							competicion) -> competicion.idCompeticion == idCompeticionSeleccionada);
			Optional<CompeticionDto> optCompDto = c.findFirst();
			if (optCompDto.isPresent()) {
				CompeticionDto dto = optCompDto.get();
				rellenarTexto(justificante.getLblInfoCompeticion(),
						String.format(NOMBRE_COMPETICION, dto.nombre));
				LocalDate hoy = LocalDate.now();
				rellenarTexto(justificante.getLblFecha(),
						String.format(FECHA, hoy.getDayOfMonth(),
								hoy.getMonth().getDisplayName(TextStyle.FULL,
										new Locale("es", "ES")),
								hoy.getYear()));
				if (inscripcion.estado.toString().equals(Estado.LISTA_ESPERA)) {
					rellenarTexto(justificante.getLblCuotaDeInscripcion(),
							SACAR_LISTA_ESPERA);
				} else {
					if (inscripcion.estado.toString()
							.equals(Estado.PENDIENTE_DE_PAGO.toString())
							|| inscripcion.estado.toString()
									.equals(Estado.PRE_INSCRITO.toString()))
						rellenarTexto(justificante.getLblCuotaDeInscripcion(),
								PAGO_SIN_ABONAR);
					else {
						rellenarTexto(justificante.getLblCuotaDeInscripcion(),
								String.format(PAGO_ABONADO, cuota));
						rellenarTexto(justificante.getLblADevolver(),
								String.format(ADEVOLVER, dto.aDevolver));
					}

				}
			}
		} catch (BusinessException e) {
			mainW.getErrorTextAreaSesion().setText(e.getMessage());
		}
		mainW.getJustificanteCancelacionDialog().setVisible(true);
	}

	public void rellenarDatosClub(InscripcionDto inscripcion)
			throws BusinessException {

		if (inscripcion.club != null) {
			rellenarTexto(justificante.getLblADevolver(),
					String.format(INSCRIPCION_DE_CLUB, inscripcion.club));

		}

	}

	public void rellenarDatosAtleta(AtletaDto atleta) throws BusinessException {

		rellenarTexto(
				mainW.getJustificanteCancelacionDialog()
						.getLblRellenarNombreYApellidos(),
				String.format(NOMBRE_APELLIDOS, atleta.nombre,
						atleta.apellido));

	}

	private void rellenarTexto(JLabel label, String texto) {
		label.setText(texto);
		label.setVisible(true);
	}

	protected void cancelarInscripcion() {
		try {
			inscCrud.cancelarInscricpion(cc.sesion.getIdAtleta(),
					idCompeticionSeleccionada);

		} catch (NumberFormatException e1) {
			mainW.getErrorTextAreaSesion().setText(e1.getMessage());
		} catch (BusinessException e1) {
			mainW.getErrorTextAreaSesion().setText(e1.getMessage());
		}
	}

}
