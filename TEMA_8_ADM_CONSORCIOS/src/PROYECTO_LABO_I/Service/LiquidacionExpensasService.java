package PROYECTO_LABO_I.Service;

import PROYECTO_LABO_I.DAO.DAOException;
import PROYECTO_LABO_I.DAO.TransaccionDAO.TransaccionDAO;
import PROYECTO_LABO_I.DAO.UnidadFuncionalDAO.UnidadFuncionalDAO;
import PROYECTO_LABO_I.Entidades.Entrada;
import PROYECTO_LABO_I.Entidades.Gasto;
import PROYECTO_LABO_I.Entidades.LiquidacionExpensas;
import PROYECTO_LABO_I.Entidades.UnidadFuncional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class LiquidacionExpensasService {
    private final TransaccionDAO transaccionDAO;
    private final UnidadFuncionalDAO unidadFuncionalDAO;

    public LiquidacionExpensasService() {
        this.transaccionDAO = new TransaccionDAO();
        this.unidadFuncionalDAO = new UnidadFuncionalDAO();
    }

    public LiquidacionExpensas generarLiquidacion(int mes, int año) throws ServiceException {
        YearMonth periodo = YearMonth.of(año, mes);

        try {
            List<Entrada> entradas = new ArrayList<>();
            List<Gasto> gastos = new ArrayList<>();
            for (var transaccion : transaccionDAO.recuperarTodasTransaccion()) {
                if (transaccion != null && transaccion.getFecha() != null) {
                    YearMonth transaccionPeriodo = YearMonth.from(transaccion.getFecha());
                    if (transaccionPeriodo.equals(periodo)) {
                        if (transaccion instanceof Entrada) {
                            entradas.add((Entrada) transaccion);
                        } else if (transaccion instanceof Gasto) {
                            gastos.add((Gasto) transaccion);
                        }
                    }
                }
            }

            BigDecimal totalEntradas = entradas.stream()
                    .map(e -> BigDecimal.valueOf(e.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalGastos = gastos.stream()
                    .map(g -> BigDecimal.valueOf(g.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal balance = totalEntradas.subtract(totalGastos);

            List<UnidadFuncional> unidadesFuncionales = unidadFuncionalDAO.recuperarTodasUF();

            Map<UnidadFuncional, BigDecimal> expensasPorUnidad = new HashMap<>();
            for (UnidadFuncional uf : unidadesFuncionales) {
                BigDecimal pagoMensual = entradas.stream()
                        .filter(e -> e.getUnidadFuncional() != null && e.getUnidadFuncional().equals(uf))
                        .map(e -> BigDecimal.valueOf(e.getMonto()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                expensasPorUnidad.put(uf, pagoMensual);
            }

            List<String> detalles = new ArrayList<>();
            for (Entrada entrada : entradas) {
                UnidadFuncional uf = entrada.getUnidadFuncional();
                detalles.add("Entrada: " + (uf != null ? "Unidad " + uf.getNumeroUnidadFuncional() : entrada.getDescripcion())
                        + " - Monto: " + entrada.getMonto());
            }
            for (Gasto gasto : gastos) {
                detalles.add("Gasto: " + gasto.getDescripcion() + " - Monto: " + gasto.getMonto());
            }

            return new LiquidacionExpensas(periodo, expensasPorUnidad, totalEntradas, totalGastos, balance, detalles);

        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al generar la liquidación: " + e.getMessage(), e);
        }
    }

    public List<YearMonth> obtenerPeriodosConDatos() throws ServiceException {
        try {
            return transaccionDAO.recuperarTodasTransaccion().stream()
                    .filter(Objects::nonNull)
                    .map(t -> YearMonth.from(t.getFecha()))
                    .distinct()
                    .collect(Collectors.toList());
        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al obtener periodos con datos: " + e.getMessage(), e);
        }
    }

    public String generarReporteConsolidado(YearMonth inicio, YearMonth fin) throws ServiceException {
        try {
            List<Entrada> entradas = transaccionDAO.recuperarTodasTransaccion().stream()
                    .filter(t -> t instanceof Entrada)
                    .map(t -> (Entrada) t)
                    .filter(e -> !YearMonth.from(e.getFecha()).isBefore(inicio)
                            && !YearMonth.from(e.getFecha()).isAfter(fin))
                    .collect(Collectors.toList());

            List<Gasto> gastos = transaccionDAO.recuperarTodasTransaccion().stream()
                    .filter(t -> t instanceof Gasto)
                    .map(t -> (Gasto) t)
                    .filter(g -> !YearMonth.from(g.getFecha()).isBefore(inicio)
                            && !YearMonth.from(g.getFecha()).isAfter(fin))
                    .collect(Collectors.toList());

            BigDecimal totalEntradas = entradas.stream()
                    .map(e -> BigDecimal.valueOf(e.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalGastos = gastos.stream()
                    .map(g -> BigDecimal.valueOf(g.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal balance = totalEntradas.subtract(totalGastos);

            return String.format("Reporte Consolidado:\nTotal Entradas: %.2f\nTotal Gastos: %.2f\nBalance: %.2f",
                    totalEntradas, totalGastos, balance);

        } catch (DAOException e) {
            throw new ServiceException("Error SERVICE al generar el reporte consolidado: " + e.getMessage(), e);
        }
    }
}
