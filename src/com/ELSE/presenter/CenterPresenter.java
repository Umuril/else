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
	private final BookDetailsPresenter bookDetailsPresenter;
	private FileSearcher fileSearcher;
	private final Model model;
	private final Presenter presenter;
	private final SliderPresenter sliderPresenter;
	private final View view;
	
	CenterPresenter(final View view, final Model model, final Presenter presenter) {
		this.view = view;
		this.model = model;
		this.presenter = presenter;
		sliderPresenter = new SliderPresenter(view, this);
		bookDetailsPresenter = new BookDetailsPresenter(view, model, presenter);
		fileSearcher = new FileSearcher(model, view, this, 0);
		fileSearcher.start();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}
	
	void addImage(final Path bookPath) throws IOException {
		final Path imagePath = Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + Utils.getMD5Checksum(bookPath) + ".jpg");
		BufferedImage image = null;
		final long startTime = System.currentTimeMillis();
		if (Files.exists(imagePath)) {
			Utils.log(Utils.Debug.DEBUG, "L'immagine gia esiste.");
			image = ImageIO.read(imagePath.toFile());
		} else {
			view.setStatusText("Cercando nuovi file, potrebbe volerci un po...");
			image = saveImage(bookPath);
			Utils.log(Utils.Debug.DEBUG, "Creazione immagine in corso: " + image);
		}
		Utils.log(Utils.Debug.WARNING, "RENDERING TIME: " + (System.currentTimeMillis() - startTime) + " for file " + bookPath);
		final JButton picLabel = new JButton(new ImageIcon(image.getScaledInstance(-1, 160, Image.SCALE_DEFAULT)));
		picLabel.setToolTipText(bookPath.toString());
		picLabel.addActionListener(new InnerListener(view, image, model.getLibrary().getDatabase().get(bookPath)));
		picLabel.setBorder(null);
		view.getSliderPage().getUp().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
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
		view.getSliderPage().getUp().revalidate();
		view.getSliderPage().getUp().repaint();
	}
	
	void change(final Image image, final BookMetadata book) {
		view.change(image, book);
	}
	
	@Override
	public boolean dispatchKeyEvent(final KeyEvent event) {
		if (view.getFrame().isFocused() && event.getID() == KeyEvent.KEY_RELEASED && event.getKeyCode() == KeyEvent.VK_F5) {
			aggiorna(-1);
			return true;
		}
		return false;
	}
	
	public BookDetailsPresenter getBookDetailsPresenter() {
		return bookDetailsPresenter;
	}
	
	public SliderPresenter getSliderPresenter() {
		return sliderPresenter;
	}
	
	public boolean isUpdating() {
		return fileSearcher.getUpdating();
	}
	
	void loadNextBooks() {
		if (isUpdating())
			return;
		final JPanel panel = view.getSliderPage().getUp();
		panel.removeAll();
		fileSearcher.findNext();
		view.enableBackButton(true);
		panel.revalidate();
		panel.repaint();
	}
	
	void loadPreviousBooks() {
		if (isUpdating())
			return;
		final int page = fileSearcher.getPage();
		final JPanel panel = view.getSliderPage().getUp();
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
	
	void removeAllBooks() {
		final JPanel panel = view.getSliderPage().getUp();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	
	private BufferedImage saveImage(final Path bookPath) throws IOException {
		final Path imagePath = Paths.get(Utils.getPreferences("Folder") + FileSystems.getDefault().getSeparator() + Utils.getMD5Checksum(bookPath) + ".jpg");
		if (Files.exists(imagePath))
			return null;
		final BufferedImage image = presenter.getCover(bookPath);
		Utils.log(Utils.Debug.INFO, "Creating again image of " + image);
		if (Boolean.parseBoolean(Utils.getPreferences("Preview"))) {
			// Need an asyncronus way
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ImageIO.write(image, "jpg", imagePath.toFile());
					} catch (final IOException ex) {
						ex.printStackTrace();
					}
				}
			}).start();
		}
		return image;
	}
	
	private static class InnerListener implements ActionListener {
		private final BookMetadata book;
		private final BufferedImage image;
		private final View view;
		
		public InnerListener(final View view, final BufferedImage image, final BookMetadata book) {
			this.view = view;
			this.image = image;
			this.book = book;
		}
		
		@Override
		public void actionPerformed(final ActionEvent e) {
			view.getBookDetailsPage().setEditable(false);
			view.change(image, book);
		}
	}
}
