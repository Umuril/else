package com.ELSE.presenter;

import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.View;

public class CenterPresenter implements KeyEventDispatcher {
	private FileSearcher fileSearcher;
	private final Model model;
	private final Presenter presenter;
	private final View view;
	private final SliderPresenter sliderPresenter;
	private final BookDetailsPresenter bookDetailsPresenter;

	CenterPresenter(View view, Model model, Presenter presenter) {
		this.view = view;
		this.model = model;
		this.presenter = presenter;
		sliderPresenter = new SliderPresenter(view, this);
		bookDetailsPresenter = new BookDetailsPresenter(view, model, presenter);
		fileSearcher = new FileSearcher(model, view, this, 0);
		fileSearcher.start();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	public SliderPresenter getSliderPresenter() {
		return sliderPresenter;
	}

	public BookDetailsPresenter getBookDetailsPresenter() {
		return bookDetailsPresenter;
	}

	public boolean isUpdating() {
		return fileSearcher.getUpdating();
	}

	void aggiorna(int page) {
		if (isUpdating())
			return;
		view.getSliderPage().getUp().removeAll();
		if (page < 0)
			page = fileSearcher.getPage();
		if (page == 0)
			view.enableBackButton(false);
		fileSearcher = new FileSearcher(model, view, this, page);
		fileSearcher.start();
		// Needed on delete
		view.getSliderPage().getUp().revalidate();
		view.getSliderPage().getUp().repaint();
	}

	void change(Image image, BookMetadata book) {
		view.change(image, book);
	}

	/*
	 * public ActionListener customOpenBook(BookMetadata book) { for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) { if (entry.getValue().equals(book)) return new BookDetailsPresenter(Paths.get(entry.getKey()), presenter); } return null; } public ActionListener defaultOpenBook(BookMetadata book) { for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) { if (entry.getValue().equals(book)) return new BookDetailsPresenter(Paths.get(entry.getKey()), presenter); } return null; }
	 */
	void emptyOfBooks() {
		JPanel panel = view.getSliderPage().getUp();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	void loadNextBooks() {
		if (fileSearcher.getUpdating())
			return;
		JPanel panel = view.getSliderPage().getUp();
		panel.removeAll();
		fileSearcher.findNext();
		view.enableBackButton(true);
		// Needed on delete
		panel.revalidate();
		panel.repaint();
	}

	void loadPreviousBooks() {
		if (fileSearcher.getUpdating())
			return;
		int page = fileSearcher.getPage();
		JPanel panel = view.getSliderPage().getUp();
		Utils.log(Utils.Debug.INFO, "Reloading books to page: " + page);
		if (page > 0) {
			panel.removeAll();
			fileSearcher = new FileSearcher(model, view, this, page - 1);
			fileSearcher.start();
			view.enableBackButton(page != 1);
			// Needed on delete
			panel.revalidate();
			panel.repaint();
		}
	}

	/*
	 * public MouseListener openBook(BookMetadata book) { for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) { if (entry.getValue().equals(book)) return new BookDetailsPresenter(Paths.get(entry.getKey()), presenter); } return null; }
	 */
	private BufferedImage saveImage(Path bookPath) throws IOException {
		final Path imagePath = Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + Utils.getMD5Checksum(bookPath) + ".jpg");
		if (Files.exists(imagePath))
			return null;
		final BufferedImage image = presenter.getCover(bookPath);
		Utils.log(Utils.Debug.INFO, "Creating again image of " + image);
		if (Boolean.parseBoolean(Utils.getPreferences("Preview"))) {
			// Need an asyncronus way
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						ImageIO.write(image, "jpg", imagePath.toFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(r).start();
		}
		return image;
	}

	// path of the book OMFG
	void addImage(Path bookPath) throws IOException {
		Path imagePath = Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + Utils.getMD5Checksum(bookPath) + ".jpg");
		BufferedImage image = null;
		long startTime = System.currentTimeMillis();
		if (Files.exists(imagePath)) {
			Utils.log(Utils.Debug.DEBUG, "L'immagine gia esiste.");
			image = ImageIO.read(imagePath.toFile());
		} else {
			view.setStatusText("Cercando nuovi file, potrebbe volerci un po...");
			image = saveImage(bookPath);
			Utils.log(Utils.Debug.DEBUG, "Creazione immagine in corso: " + image);
		}
		Utils.log(Utils.Debug.ERROR, "RENDERING TIME: " + (System.currentTimeMillis() - startTime) + " for file " + bookPath);
		Image img = image.getScaledInstance(-1, 160, Image.SCALE_DEFAULT);
		JButton picLabel = new JButton(new ImageIcon(img));
		picLabel.setToolTipText(bookPath.toString());
		picLabel.addActionListener(new InnerListener(view, image, model.getLibrary().getDatabase().get(bookPath)));
		// picLabel.addActionListener(new
		// ActionListenerWhenClickingOnABook(file));
		picLabel.setBorder(null);
		view.getSliderPage().getUp().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
	}

	private static class InnerListener implements ActionListener {
		private final BookMetadata book;
		private final BufferedImage image;
		private final View view;

		public InnerListener(View view, BufferedImage image, BookMetadata book) {
			this.view = view;
			this.image = image;
			this.book = book;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getBookDetailsPage().setEditable(false);
			view.change(image, book);
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (view.getFrame().isFocused() && e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_F5) {
			aggiorna(-1);
			return true;
		}
		return false;
	}
}
