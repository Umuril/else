package com.ELSE.presenter.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public abstract class EbookReader {
	protected String path;

	public abstract BufferedImage getCover() throws IOException;

	public abstract void getFrame() throws IOException;

	protected EbookReader(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public static EbookReader newInstance(Path path) {
		if (path.toString().endsWith(".epub"))
			return new EPUBReader(path.toString());
		if (path.toString().endsWith(".html"))
			return new HTMLReader(path.toString());
		if (path.toString().endsWith(".pdf"))
			return new PDFReader(path.toString());
		return null;
	}
}
