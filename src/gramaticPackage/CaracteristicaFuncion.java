package gramaticPackage;

public class CaracteristicaFuncion {
    private String tipoDevuelto;
    private String tipoParametro;
    private String nombreParametro;

    // Constructor
    public CaracteristicaFuncion(String tipoDevuelto, String tipoParametro, String nombreParametro) {
        this.tipoDevuelto = tipoDevuelto;
        this.tipoParametro = tipoParametro;
        this.nombreParametro = nombreParametro;
    }

    // Getters
    public String getTipoDevuelto() {
        return tipoDevuelto;
    }

    public String getTipoParametro() {
        return tipoParametro;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    // Setters
    public void setTipoDevuelto(String tipoDevuelto) {
        this.tipoDevuelto = tipoDevuelto;
    }

    public void setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }
    @Override
    public String toString() {
        return "CaracteristicaFuncion{" +
                "tipoDevuelto='" + tipoDevuelto + '\'' +
                ", tipoParametro='" + tipoParametro + '\'' +
                ", nombreParametro='" + nombreParametro + '\'' +
                '}';
    }
    
}
