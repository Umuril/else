package com.ELSE.model;

import java.io.Serializable;
import java.time.Year;

public class BookMetadata implements Serializable {
	private static final long serialVersionUID = 1650124436860899303L;
	private Year anno;
	private String autore = "";
	private String checksum;
	private int pagine;
	private String titolo;
	
	private BookMetadata(final Builder builder) {
		checksum = builder.checksum;
		titolo = builder.titolo;
		anno = builder.anno;
		pagine = builder.pagine;
	}
	
	public Year getAnno() {
		return Year.from(anno);
	}
	
	public String getAutore() {
		return autore;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public int getPagine() {
		return pagine;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setAnno(final CharSequence anno) {
		this.anno = Year.parse(anno);
	}
	
	public void setAnno(final int anno) {
		this.anno = Year.of(anno);
	}
	
	public void setAutore(final String autore) {
		if (Utils.validString(autore))
			this.autore = autore;
	}
	
	public void setChecksum(final String checksum) {
		if (Utils.validString(checksum))
			this.checksum = checksum;
	}
	
	public void setPagine(final int pagine) {
		if (pagine >= 0)
			this.pagine = pagine;
	}
	
	public void setTitolo(final String titolo) {
		if (Utils.validString(titolo))
			this.titolo = titolo;
	}
	
	@Override
	public String toString() {
		return titolo + " - " + autore + " del " + anno + "  Pagine: " + pagine + " Checksum: " + checksum;
	}
	
	public static class Builder {
		private final Year anno = Year.of(0);
		private final String checksum;
		private int pagine = 0;
		private String titolo = "";
		
		public Builder(final String checksum) {
			this.checksum = checksum;
		}
		
		public BookMetadata build() {
			return new BookMetadata(this);
		}
		
		public Builder pagine(final int pagine) {
			this.pagine = pagine;
			return this;
		}
		
		public Builder titolo(final String titolo) {
			if (Utils.validString(titolo))
				this.titolo = titolo;
			return this;
		}
	}
}
