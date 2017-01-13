package com.ELSE.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.prefs.Preferences;

public class Model {
	private Preferences prefs;
	private MetadataLibrary library;
	private Pathbase pathbase;

	public Model() {
		prefs = Preferences.userRoot().node(this.getClass().getName());
		String filename = prefs.get("MetadataLibrary", "metadata.txt");
		// Need to check the names
		library = new MetadataLibrary(filename);
		setPathbase(new Pathbase());
		// Read from the stored file
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("db.txt")), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Path path = Paths.get(line).toRealPath();
				getPathbase().add(path.toString());
			}
		} catch (IOException e) {
		}
		for (String s : pathbase.getPathsList()) {
			File path = new File(s);
			try {
				if (path.isDirectory())
					Files.walkFileTree(Paths.get(s), new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							if (acceptableFileType(file.toString())) {
								BookMetadata book = new BookMetadata();
								// book.setPercorso(file.toString());
								try {
									book.setChecksum(MD5Checksum.getMD5Checksum(file.toString()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return FileVisitResult.CONTINUE;
						}
					});
				else {
					BookMetadata book = new BookMetadata();
					// book.setPercorso(path.toString());
					try {
						book.setChecksum(MD5Checksum.getMD5Checksum(path.toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					library.getDatabase().put(book.getChecksum(), book);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Pathbase getPathbase() {
		return pathbase;
	}

	public void setPathbase(Pathbase pathbase) {
		this.pathbase = pathbase;
	}

	public MetadataLibrary getLibrary() {
		return library;
	}

	public void setLibrary(MetadataLibrary library) {
		this.library = library;
	}

	public void createPathbaseFile() {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("db.txt")), Charset.defaultCharset()))) {
			for (String p : pathbase.getPathsList()) {
				writer.append(p);
				writer.newLine();
			}
		} catch (IOException e) {
			// TODO
		}
	}

	public boolean acceptableFileType(String path) {
		return path.endsWith(".pdf") || path.endsWith(".html") || path.endsWith(".epub");
	}
}
