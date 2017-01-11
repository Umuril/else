package com.ELSE.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class MetadataLibrary {

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

}
