package com.ELSE.model;

import java.io.Serializable;
import java.time.Year;

public class BookMetadata implements Serializable {

	private static final long serialVersionUID = 1L;

	private String checksum;
	private String titolo;
	private String autore;
	private Year anno;
	// Warning: The field is transient but isn't set by deserialization
	// private transient String percorso; // Do I really need it here?
	private int npagine;

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String string) {
		this.checksum = string;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Year getAnno() {
		return anno;
	}

	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public int getNpagine() {
		return npagine;
	}

	public void setNpagine(int npagine) {
		this.npagine = npagine;
	}

	@Override
	public String toString() {
		return checksum + ":" + titolo + ":" + autore + ":" + anno + ":" + npagine;
	}

}
