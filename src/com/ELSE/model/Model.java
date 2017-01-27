package com.ELSE.model;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Model {
	private class CustomFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			if (acceptableFileType(file.toString())) {
				try {
					BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(file.toString())).build();
					if (!library.getDatabase().containsKey(book.getChecksum()))
						library.getDatabase().put(book.getChecksum(), book);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}

	private final MetadataLibrary library;
	private final Pathbase pathbase;

	public Model() {
		// Need to check the names
		pathbase = Pathbase.newInstance(Utils.getPreferences("Pathbase"));
		Utils.log(Utils.Debug.DEBUG, "BEGIN: " + pathbase.getPathsList());
		library = MetadataLibrary.newInstance(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + "metadata.else");
		new Thread(new Runnable() {
			@Override
			public void run() {
				searchForNewBooks();
			}
		}).start();
	}

	public boolean acceptableFileType(String path) {
		return path.endsWith(".pdf") || path.endsWith(".html") || path.endsWith(".epub");
	}

	public MetadataLibrary getLibrary() {
		return library;
	}

	public Pathbase getPathbase() {
		return pathbase;
	}

	public void searchForNewBooks() {
		for (String filename : pathbase.getPathsList()) {
			Path path = Paths.get(filename);
			if (Files.isRegularFile(path)) {
				BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(filename)).build(); // path was file.toString(). Need further checks
				library.getDatabase().put(book.getChecksum(), book);
			}
			if (Files.isDirectory(path))
				try {
					Files.walkFileTree(Paths.get(filename), new CustomFileVisitor());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}