package com.ELSE.presenter.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Classe astratta di un generico lettore di libri
 * 
 * @author eddy
 */
public abstract class EbookReader {
	/**
	 * Metodo statico che restituisce una nuova istanza di EbookReader
	 * 
	 * @param path
	 *            percorso del libro da leggere
	 * @return un nuovo oggetto
	 */
	public static EbookReader newInstance(final Path path) {
		if (path.toString().endsWith(".epub"))
			return new EPUBReader(path);
		if (path.toString().endsWith(".html"))
			return new HTMLReader(path);
		if (path.toString().endsWith(".pdf"))
			return new PDFReader(path);
		return null;
	}
	
	private final Path path;
	
	/**
	 * Costruttore
	 * 
	 * @param path
	 *            Percorso del libro
	 */
	protected EbookReader(final Path path) {
		this.path = path;
	}
	
	/**
	 * @return immagine copertina del libro
	 * @throws IOException
	 *             errore nella lettura del libro
	 */
	public abstract BufferedImage getCover() throws IOException;
	
	/**
	 * Metodo che restituisce il frame contenente il visualizzatore del libro
	 * 
	 * @throws IOException
	 *             errore nella lettura del libro
	 */
	public abstract void getFrame() throws IOException;
	
	/**
	 * @return numero di pagine del libro (letto tramite libreria esterna)
	 */
	public abstract int getPageNumber();
	
	/**
	 * @return percorso del libro
	 */
	public Path getPath() {
		return path;
	}
}