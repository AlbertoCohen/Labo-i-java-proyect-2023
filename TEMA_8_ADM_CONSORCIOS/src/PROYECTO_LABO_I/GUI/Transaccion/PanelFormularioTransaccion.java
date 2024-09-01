package PROYECTO_LABO_I.GUI.Transaccion;

import PROYECTO_LABO_I.Entidades.Entrada;
import PROYECTO_LABO_I.Entidades.Gasto;
import PROYECTO_LABO_I.Entidades.Transaccion;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;
import PROYECTO_LABO_I.GUI.PanelManager;
import PROYECTO_LABO_I.Service.TransaccionService;
import PROYECTO_LABO_I.Service.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PanelFormularioTransaccion extends JPanel {
    private final PanelManager panelManager;
    private Transaccion transaccion;
    private final TransaccionService transaccionService;

    private JTextField txtMonto;
    private JTextField txtDescripcion;
    private JComboBox<String> cmbTipo;

    public PanelFormularioTransaccion(PanelManager panelManager, Transaccion transaccion) {
        this.panelManager = panelManager;
        this.transaccion = transaccion;
        this.transaccionService = new TransaccionService();

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.add(new JLabel("Monto:"));
        txtMonto = new JTextField();
        panelFormulario.add(txtMonto);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(new String[]{"ENTRADA", "GASTO"});
        cmbTipo.addActionListener(e -> actualizarCamposSegunTipo());
        panelFormulario.add(cmbTipo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarOActualizarTransaccion());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> panelManager.mostrarPanelTransacciones());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        if (transaccion != null) {
            cargarDatosTransaccion();
        }
    }

    private void actualizarCamposSegunTipo() {
        String tipo = (String) cmbTipo.getSelectedItem();
        txtDescripcion.setText(tipo);
    }

    private void cargarDatosTransaccion() {
        txtMonto.setText(String.valueOf(transaccion.getMonto()));
        txtDescripcion.setText(transaccion.getDescripcion());
        cmbTipo.setSelectedItem(transaccion instanceof Entrada ? "ENTRADA" : "GASTO");
        actualizarCamposSegunTipo();
    }

    private void guardarOActualizarTransaccion() {
        try {
            BigDecimal monto = new BigDecimal(txtMonto.getText());
            String descripcion = txtDescripcion.getText();
            String tipo = (String) cmbTipo.getSelectedItem();

            if ("ENTRADA".equals(tipo)) {
                if (!(transaccion instanceof Entrada)) {
                    transaccion = new Entrada();
                }
                ((Entrada) transaccion).setUnidadFuncional(new UnidadFuncional()); 
            } else if ("GASTO".equals(tipo)) {
                if (!(transaccion instanceof Gasto)) {
                    transaccion = new Gasto();
                }
            }

            transaccion.setMonto(monto.floatValue());
            transaccion.setDescripcion(descripcion);
            transaccion.setFecha(LocalDateTime.now());

            transaccionService.guardar(transaccion);
            panelManager.mostrarPanelTransacciones();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de número incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la transacción: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
