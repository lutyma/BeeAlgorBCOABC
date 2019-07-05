package edu.asu.emit.qyan.alg.control;

public class Idrequest {

	private int id;
	private String camino;
	
	
	public Idrequest(int id, String camino) {
		super();
		this.id = id;
		this.camino = camino;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCamino() {
		return camino;
	}
	public void setCamino(String camino) {
		this.camino = camino;
	}
	@Override
	public String toString() {
		return "Idrequest [id=" + id + ", camino=" + camino + "]";
	}
	
	
}
