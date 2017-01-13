package com.ELSE.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class MetadataLibrary implements Printable {

	private HashMap<String, BookMetadata> database;
	private String filename;

	public MetadataLibrary() {
		database = new HashMap<String, BookMetadata>();
	}

	MetadataLibrary(String filename) {
		database = new HashMap<String, BookMetadata>();
		this.filename = filename;
		readFromFile();
		for (BookMetadata book : database.values())
			System.out.println(book);

	}

	// Throws?
	public void createFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(filename))) {

			oos.writeObject(database);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Need to check this one in the future
	private void readFromFile() {
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(
				filename))) {

			database = new HashMap<String, BookMetadata>();

			Object obj = null;

			try {
				while ((obj = oos.readObject()) != null)
					if (obj instanceof HashMap<?, ?>) {
						for (Entry<?, ?> entry : ((HashMap<?, ?>) obj)
								.entrySet()) {
							if (entry.getKey() instanceof String
									&& entry.getValue() instanceof BookMetadata) {
								database.put((String) entry.getKey(),
										(BookMetadata) entry.getValue());
							}
						}
					}
			} catch (EOFException e) {

			}

		} catch (FileNotFoundException e) {
			// TODO e.printStackTrace();
			System.err.println("File " + filename + " not found.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, BookMetadata> getDatabase() {
		return database;
	}

	public void setDatabase(HashMap<String, BookMetadata> database) {
		this.database = database;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e) {
				// The job did not successfully
				// complete
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
		// We have only one page, and 'page'
		// is zero-based
		if (page > 0) {
			return NO_SUCH_PAGE;
		}

		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		int x = 0, y = 30;

		// Now we perform our rendering
		g.drawString("Titolo", x, y);
		g.drawString("Autore", x + 250, y);
		g.drawString("Anno", x + 350, y);
		g.drawString("Pagine", x + 400, y);
		y += 20;

		for (Entry<String, BookMetadata> e : database.entrySet()) {
			BookMetadata b = e.getValue();
			x = 0;
			if (b.getTitolo() != null)
				g.drawString(b.getTitolo().toString(), x, y);
			if (b.getAutore() != null)
				g.drawString(b.getAutore().toString(), x + 250, y);
			if (b.getAnno() != null)
				g.drawString(b.getAnno().toString(), x + 350, y);
			g.drawString(Integer.toString(b.getNpagine()), x + 420, y);
			y += 20;
		}

		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;
	}
}
