package PROYECTO_LABO_I.GUI;

import javax.swing.*;
import PROYECTO_LABO_I.Entidades.Transaccion;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;
import PROYECTO_LABO_I.GUI.Edificio.PanelLiquidacionExpensas;
import PROYECTO_LABO_I.GUI.Edificio.UnidadFuncional.PanelFormularioUnidadFuncional;
import PROYECTO_LABO_I.GUI.Edificio.UnidadFuncional.PanelListaUnidadFuncional;
import PROYECTO_LABO_I.GUI.Transaccion.PanelFormularioTransaccion;
import PROYECTO_LABO_I.GUI.Transaccion.PanelTransacciones;
import PROYECTO_LABO_I.Service.ServiceException;

public class PanelManager {
    private JFrame frame;

    public PanelManager(JFrame frame) {
        this.frame = frame;
    }

    public void mostrarPanelPrincipal() {
        PanelPrincipal panelPrincipal = new PanelPrincipal(this);
        frame.setContentPane(panelPrincipal);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarPanelListaUnidadFuncional() {
        PanelListaUnidadFuncional panel = new PanelListaUnidadFuncional(this);
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarPanelFormularioUnidadFuncional(UnidadFuncional unidadFuncional) {
        PanelFormularioUnidadFuncional panel = new PanelFormularioUnidadFuncional(this, unidadFuncional);
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarPanelTransacciones() {
        PanelTransacciones panel = new PanelTransacciones(this);
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarPanelFormularioTransaccion(Transaccion transaccion) {
        PanelFormularioTransaccion panel = new PanelFormularioTransaccion(this, transaccion);
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostrarPanelLiquidacionExpensas() throws ServiceException {
        PanelLiquidacionExpensas panel = new PanelLiquidacionExpensas(this);
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}
