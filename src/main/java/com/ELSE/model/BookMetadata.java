package com.ELSE.model;

import java.io.Serializable;
import java.time.Year;

/**
 * Classe che contiene i metadati di un libro
 * 
 * @author eddy
 */
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
	
	/**
	 * @return anno in cui è stato scritto il libro
	 */
	public Year getAnno() {
		return Year.from(anno);
	}
	
	/**
	 * @return autore del libro
	 */
	public String getAutore() {
		return autore;
	}
	
	/**
	 * @return checksum generato dal file del libro
	 */
	public String getChecksum() {
		return checksum;
	}
	
	/**
	 * @return numero di pagine del libro
	 */
	public int getPagine() {
		return pagine;
	}
	
	/**
	 * @return titolo del libro
	 */
	public String getTitolo() {
		return titolo;
	}
	
	/**
	 * Metodo setter del anno del libro
	 * 
	 * @param anno
	 *            Stringa da interpretare come anno
	 */
	public void setAnno(final CharSequence anno) {
		this.anno = Year.parse(anno);
	}
	
	/**
	 * Metodo setter del anno del libro
	 * 
	 * @param anno
	 *            nuovo anno del libro
	 */
	public void setAnno(final int anno) {
		this.anno = Year.of(anno);
	}
	
	/**
	 * Metodo setter del autore del libro
	 * 
	 * @param autore
	 *            autore del libro
	 */
	public void setAutore(final String autore) {
		if (Utils.validString(autore))
			this.autore = autore;
	}
	
	/**
	 * Metodo setter del checksum del libro
	 * 
	 * @param checksum
	 *            checksum del file del libro
	 */
	public void setChecksum(final String checksum) {
		if (Utils.validString(checksum))
			this.checksum = checksum;
	}
	
	/**
	 * Metodo setter del numero di pagine del libro
	 * 
	 * @param pagine
	 *            numero di pagine del libro
	 */
	public void setPagine(final int pagine) {
		if (pagine >= 0)
			this.pagine = pagine;
	}
	
	/**
	 * Metodo setter del titolo del libro
	 * 
	 * @param titolo
	 *            titolo del libro
	 */
	public void setTitolo(final String titolo) {
		if (Utils.validString(titolo))
			this.titolo = titolo;
	}
	
	@Override
	public String toString() {
		return titolo + " - " + autore + " del " + anno + "  Pagine: " + pagine + " Checksum: " + checksum;
	}
	
	/**
	 * Classe che costruisce un nuovo oggetto libro
	 * 
	 * @author eddy
	 */
	public static class Builder {
		private final Year anno = Year.of(0);
		private final String checksum;
		private int pagine = 0;
		private String titolo = "";
		
		/**
		 * Costruttore
		 * 
		 * @param checksum
		 *            checksum del nuovo libro
		 */
		public Builder(final String checksum) {
			this.checksum = checksum;
		}
		
		/**
		 * Metodo che restiuisce un oggetto di tipo libro
		 * 
		 * @return nuovo libro
		 */
		public BookMetadata build() {
			return new BookMetadata(this);
		}
		
		/**
		 * Metodo che setta il numero di pagine
		 * 
		 * @param pagine
		 *            pagine del libro
		 * @return Costruttore del libro
		 */
		public Builder pagine(final int pagine) {
			this.pagine = pagine;
			return this;
		}
		
		/**
		 * Metodo che setta il titolo del libro
		 * 
		 * @param titolo
		 *            titolo del libro
		 * @return Costruttore del libro
		 */
		public Builder titolo(final String titolo) {
			if (Utils.validString(titolo))
				this.titolo = titolo;
			return this;
		}
	}
}
