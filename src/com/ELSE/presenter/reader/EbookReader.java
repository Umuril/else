package com.ELSE.presenter.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public abstract class EbookReader {
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
	
	protected EbookReader(final Path path) {
		this.path = path;
	}
	
	public abstract BufferedImage getCover() throws IOException;
	
	public abstract void getFrame() throws IOException;
	
	public abstract int getPageNumber();
	
	public Path getPath() {
		return path;
	}
}