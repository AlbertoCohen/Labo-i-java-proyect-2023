package PROYECTO_LABO_I.Entidades;

import java.time.LocalDateTime;

public abstract class Transaccion {
    private int codTransaccion;
    private String tipo;
    private LocalDateTime fecha;
    private String descripcion;
    private float monto;

    public Transaccion() {}

    public Transaccion(int codTransaccion, String tipo, LocalDateTime fecha, String descripcion, float monto) {
        this.codTransaccion = codTransaccion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getCodTransaccion() { return codTransaccion; }
    public void setCodTransaccion(int codTransaccion) { this.codTransaccion = codTransaccion; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }
}
