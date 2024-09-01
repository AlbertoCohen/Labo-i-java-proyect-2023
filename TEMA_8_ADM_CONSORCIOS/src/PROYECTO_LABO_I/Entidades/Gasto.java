package PROYECTO_LABO_I.Entidades;

import java.time.LocalDateTime;

public class Gasto extends Transaccion {
    public Gasto() {
        super();
        setTipo("GASTO");
    }

    public Gasto(int codTransaccion, float monto, LocalDateTime fecha, String descripcion) {
        super(codTransaccion, "GASTO", fecha, descripcion, monto);
    }
}
