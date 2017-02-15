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

/**
 * Classe che gestisce il backend del progetto
 * 
 * @author eddy
 */
public class Model {
	private final MetadataLibrary library;
	private final PathTree pathtree;
	
	/**
	 * Costruttore
	 */
	public Model() {
		pathtree = PathTree.newInstance(Paths.get(Utils.getPreferences("PathTree")));
		Utils.log(Utils.Debug.DEBUG, "BEGIN: " + pathtree.getPathList());
		library = MetadataLibrary.newInstance(Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + "metadata.else"));
		new Thread(new Runnable() {
			@Override
			public void run() {
				searchForNewBooks();
			}
		}).start();
	}
	
	/**
	 * @param path
	 *            Percorso di un file
	 * @return boolean che indica se il formato del file è supportato dal programma
	 */
	public boolean acceptableFileType(final String path) {
		return path.endsWith(".pdf") || path.endsWith(".html") || path.endsWith(".epub");
	}
	
	/**
	 * @return oggetto che gestisce i libri presenti
	 */
	public MetadataLibrary getLibrary() {
		return library;
	}
	
	/**
	 * @return oggetto che gestisce i percorsi delle cartelle/file da seguire
	 */
	public PathTree getPathTree() {
		return pathtree;
	}
	
	/**
	 * Metodo che cerca nuovi libri nella lista dei percorsi e li aggiunge alla libreria
	 */
	public void searchForNewBooks() {
		for (final String filename : pathtree.getPathList()) {
			final Path path = Paths.get(filename);
			if (Files.isRegularFile(path)) {
				final Path file = path.getFileName();
				if (file != null)
					library.getDatabase().put(path, new BookMetadata.Builder(Utils.getMD5Checksum(path)).titolo(file.toString().replaceFirst("[.][^.]+$", "")).pagine(EbookReader.newInstance(path).getPageNumber()).build());
			} else if (Files.isDirectory(path))
				try {
					Files.walkFileTree(path, new CustomFileVisitor());
				} catch (final IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		}
	}
	
	private class CustomFileVisitor extends SimpleFileVisitor<Path> {
		@Override
		public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) {
			if (acceptableFileType(path.toString()))
				try {
					final Path filename = path.getFileName();
					if (filename != null && !library.getDatabase().containsKey(path))
						library.getDatabase().put(path, new BookMetadata.Builder(Utils.getMD5Checksum(path)).titolo(filename.toString().replaceFirst("[.][^.]+$", "")).pagine(EbookReader.newInstance(path).getPageNumber()).build());
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			return FileVisitResult.CONTINUE;
		}
	}
}