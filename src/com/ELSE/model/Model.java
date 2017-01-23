package com.ELSE.model;

import java.io.File;
import java.io.IOException;
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
					BookMetadata book = new BookMetadata.Builder(MD5Checksum.getMD5Checksum(file.toString())).build();
					if (!library.getDatabase().containsKey(book.getChecksum()))
						library.getDatabase().put(book.getChecksum(), book);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}

	private MetadataLibrary library;
	private Pathbase pathbase;

	public Model() {
		// Need to check the names
		pathbase = Pathbase.newInstance(Utils.getPreferences("Pathbase"));
		Utils.log(Utils.Debug.DEBUG, "BEGIN: " + pathbase.getPathsList());
		library = MetadataLibrary.newInstance(Utils.getPreferences("Folder") + File.separator + "metadata.else");
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

	private void searchForNewBooks() {
		for (String path : pathbase.getPathsList()) {
			File file = new File(path);
			if (file.isFile()) {
				BookMetadata book = new BookMetadata.Builder(MD5Checksum.getMD5Checksum(path)).build(); // path was file.toString(). Need further checks
				library.getDatabase().put(book.getChecksum(), book);
			}
			if (file.isDirectory())
				try {
					Files.walkFileTree(Paths.get(path), new CustomFileVisitor());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}