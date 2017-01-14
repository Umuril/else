package com.ELSE.presenter.center;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;

import com.ELSE.model.Pathbase;

public class FileSearcher extends Thread {
	private static final int perPage = 14;
	private Object lock = new Object();
	private Pathbase pathbase;
	private CenterPresenter centerPresenter;
	private int found, page;
	private int needToSkip;

	public FileSearcher(CenterPresenter centerPresenter, Pathbase pathbase, int page) {
		this.pathbase = pathbase;
		this.centerPresenter = centerPresenter;
		found = 0;
		this.page = page;
	}

	public void findNext() {
		synchronized (lock) {
			page++;
			found = 0;
			lock.notifyAll(); // only notify() ?
		}
	}

	@Override
	public void run() {
		needToSkip = page * perPage;
		for (String s : pathbase.getPathsList()) {
			Path path = Paths.get(s);
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (file.toString().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
							if (needToSkip > 0) {
								needToSkip--;
								System.err.println(needToSkip + " Skipping book: " + file);
								return FileVisitResult.CONTINUE;
							}
							System.out.println("Aggiungendo al centro: " + file);
							centerPresenter.addImage(file.toFile());
							found++; // ++found?
							if (found >= perPage) {
								try {
									synchronized (lock) {
										while (found >= perPage)
											lock.wait();
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getFound() {
		return found;
	}

	public void setFound(int found) {
		this.found = found;
	}

	public int getPage() {
		return page;
	}
}