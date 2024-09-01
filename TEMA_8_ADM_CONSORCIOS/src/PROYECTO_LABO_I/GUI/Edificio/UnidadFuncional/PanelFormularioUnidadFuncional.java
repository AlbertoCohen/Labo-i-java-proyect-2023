package PROYECTO_LABO_I.GUI.Edificio.UnidadFuncional;

import PROYECTO_LABO_I.Entidades.UnidadFuncional;
import PROYECTO_LABO_I.GUI.PanelManager;
import PROYECTO_LABO_I.Service.UnidadFuncionalService;
import PROYECTO_LABO_I.Service.ServiceException;

import javax.swing.*;
import java.awt.*;

public class PanelFormularioUnidadFuncional extends JPanel {
    private PanelManager panelManager;
    private UnidadFuncional unidadFuncional;
    private UnidadFuncionalService unidadFuncionalService;

    private JTextField txtNumeroUnidadFuncional;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtInfoAdicional;
    private JTextField txtPagoMensual;
    private JTextField txtIdOcupante;

    public PanelFormularioUnidadFuncional(PanelManager panelManager, UnidadFuncional unidadFuncional) {
        this.panelManager = panelManager;
        this.unidadFuncional = unidadFuncional;
        this.unidadFuncionalService = new UnidadFuncionalService();

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.add(new JLabel("Número Unidad:"));
        txtNumeroUnidadFuncional = new JTextField();
        txtNumeroUnidadFuncional.setText(null);
        panelFormulario.add(txtNumeroUnidadFuncional);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        txtNombre.setText(null);
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("ID Ocupante:"));
        txtIdOcupante = new JTextField();
        txtIdOcupante.setText(null);
        panelFormulario.add(txtIdOcupante);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        txtTelefono.setText(null);
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Información Adicional:"));
        txtInfoAdicional = new JTextField();
        panelFormulario.add(txtInfoAdicional);

        panelFormulario.add(new JLabel("Pago Mensual:"));
        txtPagoMensual = new JTextField();
        txtPagoMensual.setText(null);
        panelFormulario.add(txtPagoMensual);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                guardarUnidadFuncional();
            } catch (Exception e1) {

                e1.printStackTrace();
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> panelManager.mostrarPanelListaUnidadFuncional());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        if (unidadFuncional != null) {
            cargarDatosUnidadFuncional();
            txtNumeroUnidadFuncional.setEditable(false); 
        }
    }

    private void cargarDatosUnidadFuncional() {
        txtNumeroUnidadFuncional.setText(String.valueOf(unidadFuncional.getNumeroUnidadFuncional()));
        txtNombre.setText(unidadFuncional.getNombreOcupante());
        txtTelefono.setText(unidadFuncional.getTelefono());
        txtInfoAdicional.setText(unidadFuncional.getInfoAdicional());
        txtPagoMensual.setText(String.valueOf(unidadFuncional.getPagoMensual()));
        txtIdOcupante.setText(String.valueOf(unidadFuncional.getIdOcupante()));
    }

    private void guardarUnidadFuncional() throws Exception {
        try {
            Integer numeroUnidad = Integer.parseInt(txtNumeroUnidadFuncional.getText());
            String nombre = txtNombre.getText();
            String telefono = txtTelefono.getText();
            String infoAdicional = txtInfoAdicional.getText();
            Integer pagoMensual = Integer.parseInt(txtPagoMensual.getText());
            Integer idOcupante = Integer.parseInt(txtIdOcupante.getText());

            if (unidadFuncional == null) {
                unidadFuncional = new UnidadFuncional();
            }

            if (txtNumeroUnidadFuncional.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "El campo de numeroUnidad no puede estar vacio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                unidadFuncional.setNumeroUnidadFuncional(numeroUnidad);
            }

            if (txtNombre.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "El campo de nombre no puede estar vacio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                unidadFuncional.setNombreOcupante(nombre);
            }

            if (txtTelefono.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "El campo de telefono no puede estar vacio.", "Error",
                        JOptionPane.ERROR_MESSAGE);

            } else {
                unidadFuncional.setTelefono(telefono);
            }

            unidadFuncional.setInfoAdicional(infoAdicional);

            if (txtPagoMensual.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "El campo de pagoMensual no puede estar vacio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                unidadFuncional.setPagoMensual(pagoMensual);
            }

            if (txtIdOcupante.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "El campo de idOcupante no puede estar vacio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                unidadFuncional.setIdOcupante(idOcupante);
            }

            UnidadFuncional existente = unidadFuncionalService.recuperarUF(numeroUnidad);
            if (existente == null) {
                unidadFuncionalService.guardar(unidadFuncional);
            } else {
                unidadFuncionalService.modificar(unidadFuncional);
            }

            panelManager.mostrarPanelListaUnidadFuncional();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de número incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la unidad funcional: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}