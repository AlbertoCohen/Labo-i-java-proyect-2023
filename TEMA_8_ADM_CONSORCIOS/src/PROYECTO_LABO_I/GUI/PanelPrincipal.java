package PROYECTO_LABO_I.GUI;

import javax.swing.*;

import PROYECTO_LABO_I.Service.ServiceException;

import java.awt.*;

public class PanelPrincipal extends JPanel {
    public PanelPrincipal(PanelManager panelManager) {
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnUnidadFuncional = new JButton("Administrar Unidades Funcionales");
        btnUnidadFuncional.addActionListener(e -> panelManager.mostrarPanelListaUnidadFuncional());
        add(btnUnidadFuncional);

        JButton btnTransacciones = new JButton("Administrar Transacciones");
        btnTransacciones.addActionListener(e -> panelManager.mostrarPanelTransacciones());
        add(btnTransacciones);

        JButton btnLiquidacion = new JButton("Generar Liquidación Expensas");
        btnLiquidacion.addActionListener(e -> {
            try {
                panelManager.mostrarPanelLiquidacionExpensas();
            } catch (ServiceException e1) {
                e1.printStackTrace();
            }
        });
        add(btnLiquidacion);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }
}
