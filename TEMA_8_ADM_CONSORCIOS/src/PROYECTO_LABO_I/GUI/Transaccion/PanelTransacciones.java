package PROYECTO_LABO_I.GUI.Transaccion;

import PROYECTO_LABO_I.Entidades.Entrada;
import PROYECTO_LABO_I.Entidades.Gasto;
import PROYECTO_LABO_I.Entidades.Transaccion;
import PROYECTO_LABO_I.GUI.PanelManager;
import PROYECTO_LABO_I.Service.TransaccionService;
import PROYECTO_LABO_I.Service.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelTransacciones extends JPanel {
    private final PanelManager panelManager;
    private final TransaccionService transaccionService;
    private final JTable table;

    public PanelTransacciones(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.transaccionService = new TransaccionService();
        setLayout(new BorderLayout());

        table = new JTable();
        actualizarTabla();

        JButton btnNuevaTransaccion = new JButton("Nueva Transacción");
        btnNuevaTransaccion.addActionListener(e -> panelManager.mostrarPanelFormularioTransaccion(new Entrada()));

        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarTransaccionSeleccionada());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarTransaccionSeleccionada());

        JButton btnVolverAlMenu = new JButton("Volver al menú");
        btnVolverAlMenu.addActionListener(e -> panelManager.mostrarPanelPrincipal());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnNuevaTransaccion);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolverAlMenu);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        try {
            List<Transaccion> transacciones = transaccionService.recuperarTodas();

            String[] columnNames = {"Código", "Tipo", "Monto", "Fecha", "Descripción"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (Transaccion t : transacciones) {
                if (t != null) {  // Fixes NullPointerException
                    model.addRow(new Object[]{
                        t.getCodTransaccion(),
                        t instanceof Gasto ? "GASTO" : "ENTRADA",
                        t.getMonto(),
                        t.getFecha().toString(),
                        t.getDescripcion()
                    });
                }
            }
            table.setModel(model);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las transacciones", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarTransaccionSeleccionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int codTransaccion = (int) table.getValueAt(selectedRow, 0);
            try {
                Transaccion transaccion = transaccionService.recuperarPorCodTransaccion(codTransaccion);
                panelManager.mostrarPanelFormularioTransaccion(transaccion);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al recuperar la transacción", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarTransaccionSeleccionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int codTransaccion = (int) table.getValueAt(selectedRow, 0);
            try {
                transaccionService.eliminarPorCodTransaccion(codTransaccion);
                actualizarTabla();
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la transacción", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
