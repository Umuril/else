package com.ELSE.model;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;

public class FileSearcher extends Thread {
	private Object lock = new Object();
	private Path path;
	private int alreadyfound;

	public FileSearcher(Path p) {
		path = p;
	}

	public void findNext() {
		synchronized (lock) {
			lock.notifyAll(); // only notify() ?
		}
	}

	@Override
	public void run() {
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.toString().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
						alreadyfound++; // ++already?
						if (alreadyfound >= 10) {
							try {
								synchronized (lock) {
									while (alreadyfound >= 10)
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