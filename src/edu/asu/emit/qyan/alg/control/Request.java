package edu.asu.emit.qyan.alg.control;

public class Request {

	private int origen;
	private int destino;
	private int fs;
	private String caminoElegido;
	private int id;





	public Request() {

	}
   
	public Request(int origen, int destino, int fs) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.fs = fs;
	}

	public Request(int origen, int destino, int fs, String caminoElegido, int id) {
		this.origen = origen;
		this.destino = destino;
		this.fs = fs;
		this.caminoElegido = caminoElegido;
		this.id = id;
	}



	public int getOrigen() {
		return origen;
	}



	public void setOrigen(int origen) {
		this.origen = origen;
	}



	public int getDestino() {
		return destino;
	}



	public void setDestino(int destino) {
		this.destino = destino;
	}



	public int getFs() {
		return fs;
	}



	public void setFs(int fs) {
		this.fs = fs;
	}

	public String getCaminoElegido() {
		return caminoElegido;
	}

	public void setCaminoElegido(String caminoElegido) {
		this.caminoElegido = caminoElegido;
	}
    
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Request [origen=" + origen + ", destino=" + destino + ", fs=" + fs + ", caminoElegido=" + caminoElegido
				+ ", id=" + id + "]";
	}


}
