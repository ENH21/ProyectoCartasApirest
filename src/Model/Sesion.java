package Model;

import java.io.Serializable;
/**
 * Clase con el modelo de la Sesion
 * @author Esteban
 *
 */
public class Sesion implements Serializable{
	private int idsesion;
	private int idpartida;
	private String idjugador;
	
	public Sesion(String idjugador) {
		this.idjugador = idjugador;
		this.idpartida = 0;
	}
	public Sesion() {
		this.idpartida = 0;
	}
	
	public Sesion(int idsesion, int idpartida, String idjugador) {
		this.idsesion = idsesion;
		this.idpartida = idpartida;
		this.idjugador = idjugador;
	}
	
	public int getIdsesion() {
		return idsesion;
	}

	public int getIdpartida() {
		return idpartida;
	}

	public String getIdjugador() {
		return idjugador;
	}

	public void setIdsesion(int idsesion) {
		this.idsesion = idsesion;
	}

	public void setIdpartida(int idpartida) {
		this.idpartida = idpartida;
	}

	public void setIdjugador(String idjugador) {
		this.idjugador = idjugador;
	}
	
	@Override
    public String toString() {
        return "Sesion{" +
                "idsesion=" + idsesion +
                ", idpartida=" + idpartida +
                ", idjugador='" + idjugador + '\'' +
                '}';
    }
	
}
