package com.ELSE.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;

public class MetadataLibrary implements Printable {
	static MetadataLibrary newInstance(final Path path) {
		return new MetadataLibrary(path);
	}
	
	private HashMap<Path, BookMetadata> database;
	private Path path;
	
	private MetadataLibrary(final Path path) {
		if (Files.isRegularFile(path)) {
			this.path = path;
			try {
				readLibraryFromFile();
			} catch (ClassNotFoundException | IOException ex) {
				ex.printStackTrace();
				database = new HashMap<Path, BookMetadata>();
			}
		} else
			database = new HashMap<Path, BookMetadata>();
	}
	
	public void createLibraryFile() throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
			oos.writeObject(database.size());
			for (final Entry<Path, BookMetadata> entry : database.entrySet()) {
				oos.writeObject(entry.getKey().toString());
				oos.writeObject(entry.getValue());
			}
		}
	}
	
	public HashMap<Path, BookMetadata> getDatabase() {
		return database;
	}
	
	public void print() {
		final PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if (job.printDialog())
			try {
				job.print();
			} catch (final PrinterException ex) {
				// TODO Check what happens when this happens
				Utils.log(Utils.Debug.ERROR, "Stampa non avvenuta con successo");
			}
	}
	
	@Override
	public int print(final Graphics g, final PageFormat pf, final int page) throws PrinterException {
		// TODO Check when there're more pages
		if (page > 0)
			return Printable.NO_SUCH_PAGE;
		((Graphics2D) g).translate(pf.getImageableX(), pf.getImageableY());
		int x = 0, y = 30;
		g.drawString("Titolo", x, y);
		g.drawString("Autore", x + 250, y);
		g.drawString("Anno", x + 350, y);
		g.drawString("Pagine", x + 400, y);
		y += 20;
		for (final Entry<Path, BookMetadata> entry : database.entrySet()) {
			final BookMetadata book = entry.getValue();
			x = 0;
			if (Utils.validString(book.getTitolo())) {
				g.drawString(book.getTitolo(), x, y);
				g.drawString(Utils.validString(book.getAutore()) ? book.getAutore() : "-----", x + 250, y);
				g.drawString(Utils.validYear(book.getAnno()) ? book.getAnno().toString() : "-----", x + 350, y);
				g.drawString(book.getPagine() > 0 ? Integer.toString(book.getPagine()) : "---", x + 420, y);
				y += 20;
			}
		}
		return Printable.PAGE_EXISTS;
	}
	
	private void readLibraryFromFile() throws IOException, ClassNotFoundException {
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path.toFile()))) {
			database = new HashMap<Path, BookMetadata>();
			int size = (int) oos.readObject();
			while (size-- > 0) {
				final Object path = oos.readObject();
				final Object book = oos.readObject();
				if (path != null && book != null && path instanceof String && book instanceof BookMetadata)
					database.put(Paths.get((String) path), (BookMetadata) book);
			}
		}
	}
}