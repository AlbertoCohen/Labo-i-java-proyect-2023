package PROYECTO_LABO_I.GUI.Edificio;

import PROYECTO_LABO_I.Entidades.LiquidacionExpensas;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;
import PROYECTO_LABO_I.Service.LiquidacionExpensasService;
import PROYECTO_LABO_I.Service.ServiceException;
import PROYECTO_LABO_I.GUI.PanelManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map.Entry;

public class PanelLiquidacionExpensas extends JPanel {
    private final LiquidacionExpensasService liquidacionExpensasService;
    private final JTable tablaLiquidacion;

    public PanelLiquidacionExpensas(PanelManager panelManager) throws ServiceException {
        this.liquidacionExpensasService = new LiquidacionExpensasService();

        setLayout(new BorderLayout());

        tablaLiquidacion = new JTable();
        actualizarTabla();

        JButton btnGenerarLiquidacion = new JButton("Generar Liquidación");
        btnGenerarLiquidacion.addActionListener(e -> SwingUtilities.invokeLater(this::generarLiquidacionParaMes));

        JButton btnVolver = new JButton("Volver al menu");
        btnVolver.addActionListener(e -> panelManager.mostrarPanelPrincipal());

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGenerarLiquidacion);
        panelBotones.add(btnVolver);

        add(new JScrollPane(tablaLiquidacion), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        String[] columnNames = { "Mes", "Total Entradas", "Total Gastos", "Balance" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            List<YearMonth> periodos = liquidacionExpensasService.obtenerPeriodosConDatos();
            for (YearMonth periodo : periodos) {
                LiquidacionExpensas liquidacion = liquidacionExpensasService.generarLiquidacion(
                        periodo.getMonthValue(), periodo.getYear());
                model.addRow(new Object[] {
                        periodo,
                        liquidacion.getTotalEntradas(),
                        liquidacion.getTotalGastos(),
                        liquidacion.getBalance()
                });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los periodos con datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        tablaLiquidacion.setModel(model);
    }

    private void generarLiquidacionParaMes() {
        int selectedRow = tablaLiquidacion.getSelectedRow();
        if (selectedRow != -1) {
            YearMonth periodo = (YearMonth) tablaLiquidacion.getValueAt(selectedRow, 0);
            try {
                LiquidacionExpensas liquidacion = liquidacionExpensasService.generarLiquidacion(
                        periodo.getMonthValue(), periodo.getYear());
                mostrarDetalleLiquidacion(liquidacion);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al generar la liquidación para el mes: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDetalleLiquidacion(LiquidacionExpensas liquidacion) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Periodo: ").append(liquidacion.getPeriodo()).append("\n");
        detalles.append("Total Entradas: ").append(liquidacion.getTotalEntradas()).append("\n");
        detalles.append("Total Gastos: ").append(liquidacion.getTotalGastos()).append("\n");

        detalles.append("\nDetalles de transacciones:\n");
        for (String detalle : liquidacion.getDetalles()) {
            detalles.append(detalle).append("\n");
        }

        detalles.append("\nDetalles por unidad funcional:\n");
        for (Entry<UnidadFuncional, BigDecimal> entry : liquidacion.getExpensasPorUnidad().entrySet()) {
            UnidadFuncional uf = entry.getKey();
            detalles.append("Unidad: ").append(uf != null ? uf.getNumeroUnidadFuncional() : "Eliminada")
                    .append(" - Pago Total: ").append(entry.getValue()).append("\n");
        }

        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Detalle de Liquidación");
        frame.add(scrollPane);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
