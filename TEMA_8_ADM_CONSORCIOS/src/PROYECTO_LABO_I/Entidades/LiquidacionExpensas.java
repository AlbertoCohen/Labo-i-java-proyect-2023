package PROYECTO_LABO_I.Entidades;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class LiquidacionExpensas {
    private YearMonth periodo;
    private Map<UnidadFuncional, BigDecimal> expensasPorUnidad;
    private BigDecimal totalEntradas;
    private BigDecimal totalGastos;
    private BigDecimal balance;
    private List<String> detalles;

    public LiquidacionExpensas(YearMonth periodo, Map<UnidadFuncional, BigDecimal> expensasPorUnidad, BigDecimal totalEntradas,
                               BigDecimal totalGastos, BigDecimal balance, List<String> detalles) {
        this.periodo = periodo;
        this.expensasPorUnidad = expensasPorUnidad;
        this.totalEntradas = totalEntradas;
        this.totalGastos = totalGastos;
        this.balance = balance;
        this.detalles = detalles;
    }

    public YearMonth getPeriodo() { return periodo; }
    public void setPeriodo(YearMonth periodo) { this.periodo = periodo; }
    public Map<UnidadFuncional, BigDecimal> getExpensasPorUnidad() { return expensasPorUnidad; }
    public void setExpensasPorUnidad(Map<UnidadFuncional, BigDecimal> expensasPorUnidad) { this.expensasPorUnidad = expensasPorUnidad; }
    public BigDecimal getTotalEntradas() { return totalEntradas; }
    public void setTotalEntradas(BigDecimal totalEntradas) { this.totalEntradas = totalEntradas; }
    public BigDecimal getTotalGastos() { return totalGastos; }
    public void setTotalGastos(BigDecimal totalGastos) { this.totalGastos = totalGastos; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public List<String> getDetalles() { return detalles; }
    public void setDetalles(List<String> detalles) { this.detalles = detalles; }
}
