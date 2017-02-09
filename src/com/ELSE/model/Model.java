package com.ELSE.model;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.ELSE.presenter.reader.EbookReader;

public class Model {
	private final MetadataLibrary library;
	private final Pathbase pathbase;

	public Model() {
		pathbase = Pathbase.newInstance(Paths.get(Utils.getPreferences("Pathbase")));
		Utils.log(Utils.Debug.DEBUG, "BEGIN: " + pathbase.getPathsList());
		library = MetadataLibrary.newInstance(Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + "metadata.else"));
		searchForNewBooks();
		/*
		 * new Thread(new Runnable() {
		 * @Override public void run() { searchForNewBooks(); } }).start();
		 */
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
				BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(path)).titolo(path.getFileName().toString().replaceFirst("[.][^.]+$", "")).pagine(EbookReader.newInstance(path).getPageNumber()).build();
				// BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(path)).build(); // path was file.toString(). Need further checks
				library.getDatabase().put(path, book);
			}
			if (Files.isDirectory(path))
				try {
					Files.walkFileTree(path, new CustomFileVisitor());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	private class CustomFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
			if (acceptableFileType(path.toString())) {
				try {
					BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(path)).titolo(path.getFileName().toString().replaceFirst("[.][^.]+$", "")).pagine(EbookReader.newInstance(path).getPageNumber()).build();
					// BookMetadata book = new BookMetadata.Builder(Utils.getMD5Checksum(file)).build();
					if (!library.getDatabase().containsKey(path))
						library.getDatabase().put(path, book);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}
}