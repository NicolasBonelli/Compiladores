package Paquetecompi;

public class TipoEtiqueta {
	private String nombre;
	private String ambito;
	private Integer posicion;
	public TipoEtiqueta(String nombre, Integer posicion,String ambito) {
		super();
		this.nombre = nombre;
		this.posicion = posicion;
		this.ambito=ambito;
	}
	public String getAmbito() {
		return ambito;
	}
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getposicion() {
		return posicion;
	}
	public void setposicion(int posicion) {
		this.posicion = posicion;
	}
	@Override
	public boolean equals(Object o) {
		return ((TipoEtiqueta) o).getNombre().equals(this.nombre);
	}
	
	

}
