package com.ELSE.presenter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.View;

public class FileSearcher extends Thread {
	private static final int perPage = 8;
	private final CenterPresenter centerPresenter;
	private int found, page;
	private final Object lock = new Object();
	private int needToSkip;
	private final Model model;
	private final View view;
	private boolean updating;

	public FileSearcher(Model model, View view, CenterPresenter centerPresenter, int page) {
		this.model = model;
		this.centerPresenter = centerPresenter;
		found = 0;
		this.page = page;
		this.view = view;
		updating = false;
	}

	public void findNext() {
		synchronized (lock) {
			page++;
			found = 0;
			lock.notifyAll(); // only notify() ?
		}
	}

	public int getFound() {
		return found;
	}

	public int getPage() {
		return page;
	}

	@Override
	public void run() {
		needToSkip = page * perPage;
		for (String s : model.getPathbase().getPathsList()) {
			Path path = Paths.get(s);
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						updating = true;
						if (model.acceptableFileType(file.toString())) {
							if (needToSkip > 0) {
								needToSkip--;
								Utils.log(Utils.Debug.DEBUG, needToSkip + " Skipping book: " + file);
								return FileVisitResult.CONTINUE;
							}
							if (found >= perPage) {
								// Here there are another books but i still
								// don't add it
								view.enableNextButton(true);
								updating = false;
								try {
									synchronized (lock) {
										while (found >= perPage)
											lock.wait();
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							Utils.log(Utils.Debug.DEBUG, "Aggiungendo al centro: " + file);
							if (view.isEmpty()) {
								view.setEmpty(false);
								centerPresenter.change(null, null);
							}
							centerPresenter.addImage(file);
							found++; // ++found?
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		updating = false;
		if (found == 0) {
			view.setEmpty(true);
			view.change(null, null);
		}
		view.enableNextButton(false);
	}

	public void setFound(int found) {
		this.found = found;
	}

	public boolean getUpdating() {
		return updating;
	}
}