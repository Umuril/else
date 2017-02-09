package com.ELSE.model;

import java.io.Serializable;
import java.time.Year;

public class BookMetadata implements Serializable {
	public static class Builder {
		private Year anno = Year.of(0);
		private String autore = "";
		private final String checksum;
		private int pagine = 0;
		private String titolo = "";

		public Builder(String checksum) {
			this.checksum = checksum;
		}

		public Builder anno(CharSequence anno) {
			this.anno = Year.parse(anno);
			return this;
		}

		public Builder anno(int anno) {
			this.anno = Year.of(anno);
			return this;
		}

		public Builder autore(String autore) {
			if (Utils.checkString(autore))
				this.autore = autore;
			return this;
		}

		public BookMetadata build() {
			return new BookMetadata(this);
		}

		public Builder pagine(int val) {
			this.pagine = val;
			return this;
		}

		public Builder titolo(String titolo) {
			if (Utils.checkString(titolo))
				this.titolo = titolo;
			return this;
		}
	}

	private static final long serialVersionUID = 1L;
	private Year anno;
	private String autore;
	private String checksum;
	private int pagine;
	private String titolo;

	private BookMetadata(Builder builder) {
		checksum = builder.checksum;
		titolo = builder.titolo;
		autore = builder.autore;
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

	public void setAnno(CharSequence anno) {
		this.anno = Year.parse(anno);
	}

	public void setAnno(int anno) {
		this.anno = Year.of(anno);
	}

	public void setAutore(String autore) {
		if (Utils.checkString(autore))
			this.autore = autore;
	}

	public void setChecksum(String checksum) {
		if (Utils.checkString(checksum))
			this.checksum = checksum;
	}

	public void setPagine(int pagine) {
		if (pagine >= 0)
			this.pagine = pagine;
	}

	public void setTitolo(String titolo) {
		if (Utils.checkString(titolo))
			this.titolo = titolo;
	}

	@Override
	public String toString() {
		return titolo + " - " + autore + " del " + anno + "  Pagine: " + pagine + " Checksum: " + checksum;
	}
}
