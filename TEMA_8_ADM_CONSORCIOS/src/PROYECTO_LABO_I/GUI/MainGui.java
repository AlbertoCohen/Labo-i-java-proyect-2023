package PROYECTO_LABO_I.GUI;

import javax.swing.*; 

public class MainGui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Administración de Edificio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            PanelManager panelManager = new PanelManager(frame);
            panelManager.mostrarPanelPrincipal();

            frame.setVisible(true);
        });
    }
}
