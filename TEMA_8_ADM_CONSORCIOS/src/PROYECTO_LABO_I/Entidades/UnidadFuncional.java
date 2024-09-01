package PROYECTO_LABO_I.Entidades;

public class UnidadFuncional {
    private int numeroUnidadFuncional;
    private int idOcupante;
    private String nombreOcupante;
    private String telefono;
    private String infoAdicional;
    private int pagoMensual;

    public int getNumeroUnidadFuncional() {
        return numeroUnidadFuncional;
    }
    public void setNumeroUnidadFuncional(int numeroUnidadFuncional) {
        this.numeroUnidadFuncional = numeroUnidadFuncional;
    }
    public int getIdOcupante() {
        return idOcupante;
    }
    public void setIdOcupante(int idOcupante) {
        this.idOcupante = idOcupante;
    }
    public String getNombreOcupante() {
        return nombreOcupante;
    }
    public void setNombreOcupante(String nombreOcupante) {
        this.nombreOcupante = nombreOcupante;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getInfoAdicional() {
        return infoAdicional;
    }
    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }
    public int getPagoMensual() {
        return pagoMensual;
    }
    public void setPagoMensual(int pagoMensual) {
        this.pagoMensual = pagoMensual;
    }

}
