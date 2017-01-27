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
import java.time.Year;
import java.util.HashMap;
import java.util.Map.Entry;

public class MetadataLibrary implements Printable {
	static MetadataLibrary newInstance(String filename) {
		return new MetadataLibrary(filename);
	}

	private HashMap<String, BookMetadata> database;
	private String filename;

	private MetadataLibrary(String filename) {
		this.filename = filename;
		Path path = Paths.get(filename);
		if (Files.exists(path) && Files.isRegularFile(path))
			try {
				readFromFile();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				database = new HashMap<String, BookMetadata>();
			}
		else
			database = new HashMap<String, BookMetadata>();
	}

	public void createFile() throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(database);
		}
	}

	public HashMap<String, BookMetadata> getDatabase() {
		return database;
	}

	public String getFilename() {
		return filename;
	}

	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if (job.printDialog())
			try {
				job.print();
			} catch (PrinterException e) {
				// TODO Check what happens when this happens
				Utils.log(Utils.Debug.ERROR, "Stampa non avvenuta con successo");
			}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		// TODO Check when there're more pages
		if (page > 0)
			return NO_SUCH_PAGE;
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		int x = 0, y = 30;
		g.drawString("Titolo", x, y);
		g.drawString("Autore", x + 250, y);
		g.drawString("Anno", x + 350, y);
		g.drawString("Pagine", x + 400, y);
		y += 20;
		for (Entry<String, BookMetadata> e : database.entrySet()) {
			BookMetadata b = e.getValue();
			x = 0;
			if (Utils.checkString(b.getTitolo())) {
				g.drawString(b.getTitolo(), x, y);
				g.drawString(b.getAutore().isEmpty() ? b.getAutore() : "-----", x + 250, y);
				g.drawString(b.getAnno() != null && !b.getAnno().equals(Year.of(0)) ? b.getAnno().toString() : "-----", x + 350, y);
				g.drawString(b.getPagine() > 0 ? Integer.toString(b.getPagine()) : "---", x + 420, y);
				y += 20;
			}
		}
		return PAGE_EXISTS;
	}

	public void setDatabase(HashMap<String, BookMetadata> database) {
		this.database = database;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	private void readFromFile() throws IOException, ClassNotFoundException {
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filename))) {
			database = new HashMap<String, BookMetadata>();
			Object obj = oos.readObject();
			if (obj != null && obj instanceof HashMap<?, ?>)
				for (Entry<?, ?> entry : ((HashMap<?, ?>) obj).entrySet())
					if (entry.getKey() instanceof String && entry.getValue() instanceof BookMetadata)
						database.put((String) entry.getKey(), (BookMetadata) entry.getValue());
		}
	}
}
