package PROYECTO_LABO_I.Entidades;

import java.time.LocalDateTime;

public class Entrada extends Transaccion {
    private UnidadFuncional unidadFuncional;

    public Entrada() {
        super();
        setTipo("ENTRADA");
    }

    public Entrada(int codTransaccion, float monto, LocalDateTime fecha, String descripcion, UnidadFuncional unidadFuncional) {
        super(codTransaccion, "ENTRADA", fecha, descripcion, monto);
        this.unidadFuncional = unidadFuncional;
    }

    public UnidadFuncional getUnidadFuncional() { return unidadFuncional; }
    public void setUnidadFuncional(UnidadFuncional unidadFuncional) { this.unidadFuncional = unidadFuncional; }
}
