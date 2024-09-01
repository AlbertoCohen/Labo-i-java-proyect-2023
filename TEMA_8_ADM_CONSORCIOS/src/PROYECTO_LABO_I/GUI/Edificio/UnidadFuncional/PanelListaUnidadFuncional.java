package PROYECTO_LABO_I.GUI.Edificio.UnidadFuncional;

import PROYECTO_LABO_I.Entidades.Entrada;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;
import PROYECTO_LABO_I.GUI.PanelManager;
import PROYECTO_LABO_I.Service.ServiceException;
import PROYECTO_LABO_I.Service.TransaccionService;
import PROYECTO_LABO_I.Service.UnidadFuncionalService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class PanelListaUnidadFuncional extends JPanel {
    private PanelManager panelManager;
    private JTable table;
    private UnidadFuncionalService unidadFuncionalService;
    private TransaccionService transaccionService;

    public PanelListaUnidadFuncional(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.unidadFuncionalService = new UnidadFuncionalService();
        this.transaccionService = new TransaccionService();

        setLayout(new BorderLayout());

        table = new JTable();
        actualizarTabla();

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> panelManager.mostrarPanelFormularioUnidadFuncional(null));

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarUnidadFuncionalSeleccionada());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarUnidadFuncionalSeleccionada());

        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(e -> pagarUnidadFuncionalSeleccionada());

        JButton btnvolverAlMenu = new JButton("Volver al menu");
        btnvolverAlMenu.addActionListener(e -> panelManager.mostrarPanelPrincipal());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnPagar);
        panelBotones.add(btnvolverAlMenu);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        try {
            List<UnidadFuncional> unidades = unidadFuncionalService.recuperarTodas();
            String[] columnNames = { "Número Unidad", "Nombre", "Teléfono", "Información Adicional", "Pago Mensual" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (UnidadFuncional uf : unidades) {
                model.addRow(new Object[] {
                        uf.getNumeroUnidadFuncional(),
                        uf.getNombreOcupante(),
                        uf.getTelefono(),
                        uf.getInfoAdicional(),
                        uf.getPagoMensual()
                });
            }
            table.setModel(model);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las unidades funcionales", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarUnidadFuncionalSeleccionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int numeroUnidad = (int) table.getValueAt(selectedRow, 0);
            try {
                UnidadFuncional unidadFuncional = unidadFuncionalService.recuperarUF(numeroUnidad);
                panelManager.mostrarPanelFormularioUnidadFuncional(unidadFuncional);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al recuperar la unidad funcional", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUnidadFuncionalSeleccionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int numeroUnidad = (int) table.getValueAt(selectedRow, 0);
            try {
                unidadFuncionalService.eliminar(numeroUnidad);
                actualizarTabla();
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la unidad funcional", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void pagarUnidadFuncionalSeleccionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int numeroUnidad = (int) table.getValueAt(selectedRow, 0);
            try {
                UnidadFuncional unidadFuncional = unidadFuncionalService.recuperarUF(numeroUnidad);
                Entrada nuevaEntrada = new Entrada();

                nuevaEntrada.setCodTransaccion(0);
                nuevaEntrada.setUnidadFuncional(unidadFuncional);
                nuevaEntrada.setTipo("Entrada");
                nuevaEntrada
                        .setDescripcion("Pagada por la unidad numero: " + unidadFuncional.getNumeroUnidadFuncional());
                nuevaEntrada.setMonto(unidadFuncional.getPagoMensual());
                nuevaEntrada.setFecha(LocalDateTime.now());
                transaccionService.guardar(nuevaEntrada);

                JOptionPane.showMessageDialog(this, "Pago registrado correctamente.", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al registrar el pago: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
