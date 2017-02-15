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

/**
 * Classe che cerca i libri richiesti nelle cartelle indicate precedentemente
 * 
 * @author eddy
 */
class FileSearcher extends Thread {
	private static final int perPage = 8;
	private final CenterPresenter centerPresenter;
	private int found, page;
	private final Object lock = new Object();
	private final Model model;
	private int needToSkip;
	private boolean updating;
	private final View view;
	
	/**
	 * Costruttore
	 * 
	 * @param model
	 *            Modello generale del progetto
	 * @param view
	 *            Vista generale del progetto
	 * @param centerPresenter
	 *            Presenter del pannello centrale
	 * @param page
	 *            pagina da visualizzare
	 */
	FileSearcher(final Model model, final View view, final CenterPresenter centerPresenter, final int page) {
		this.model = model;
		this.centerPresenter = centerPresenter;
		this.page = page;
		this.view = view;
		found = 0;
		updating = false;
	}
	
	/**
	 * Metodo che cerca i prossimi libri nelle directory
	 */
	void findNext() {
		synchronized (lock) {
			page++;
			found = 0;
			lock.notifyAll();
		}
	}
	
	/**
	 * @return il numero di libri trovati finora
	 */
	public int getFound() {
		return found;
	}
	
	/**
	 * @return pagina da cercare richiesta
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * @return vero se la ricerca è in corso
	 */
	public boolean getUpdating() {
		return updating;
	}
	
	@Override
	public void run() {
		needToSkip = page * FileSearcher.perPage;
		for (final String s : model.getPathTree().getPathList()) {
			final Path path = Paths.get(s);
			try {
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
						updating = true;
						if (model.acceptableFileType(file.toString())) {
							if (needToSkip > 0) {
								needToSkip--;
								Utils.log(Utils.Debug.DEBUG, needToSkip + " Skipping book: " + file);
								return FileVisitResult.CONTINUE;
							}
							if (found >= FileSearcher.perPage) {
								// Here there are another books but i still don't add it
								view.enableNextButton(true);
								updating = false;
								try {
									synchronized (lock) {
										while (found >= FileSearcher.perPage)
											lock.wait();
									}
								} catch (final InterruptedException ex) {
									ex.printStackTrace();
								}
							}
							Utils.log(Utils.Debug.DEBUG, "Aggiungendo al centro: " + file);
							if (view.isEmpty()) {
								view.setEmpty(false);
								centerPresenter.change(null, null);
							}
							centerPresenter.addImage(file);
							found++;
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		updating = false;
		if (found == 0) {
			view.setEmpty(true);
			view.change(null, null);
		}
		view.enableNextButton(false);
	}
}