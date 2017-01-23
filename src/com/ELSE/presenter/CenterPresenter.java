package com.ELSE.presenter;

import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.MD5Checksum;
import com.ELSE.model.Model;
import com.ELSE.model.Utils;
import com.ELSE.view.View;

public class CenterPresenter implements KeyEventDispatcher {
	private FileSearcher fileSearcher;
	private Model model;
	private Presenter presenter;
	private View view;
	private SliderPresenter sliderPresenter;
	private BookDetailsPresenter bookDetailsPresenter;

	public CenterPresenter(View view, Model model, Presenter presenter) {
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

	public void addImage(BookMetadata book) throws IOException {
		String filename = Utils.getPreferences("Folder") + File.separator + book.getChecksum() + ".jpg";
		File imageFile = new File(filename);
		if (!imageFile.exists()) {
			System.err.println("Error 404");
			return;
		}
		BufferedImage image = ImageIO.read(imageFile);
		JButton picLabel = new JButton(new ImageIcon(image.getScaledInstance(-1, 180, Image.SCALE_DEFAULT)));
		picLabel.addActionListener(new InnerListener(view, image, book));
		picLabel.setBorder(null);
		view.getSliderPage().getUp().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
	}

	public boolean isUpdating() {
		return fileSearcher.getUpdating();
	}

	public void aggiorna(int page) {
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

	public void change(Image image, BookMetadata book) {
		view.change(image, book);
	}

	/*
	 * public ActionListener customOpenBook(BookMetadata book) { for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) { if (entry.getValue().equals(book)) return new BookDetailsPresenter(Paths.get(entry.getKey()), presenter); } return null; } public ActionListener defaultOpenBook(BookMetadata book) { for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) { if (entry.getValue().equals(book)) return new BookDetailsPresenter(Paths.get(entry.getKey()), presenter); } return null; }
	 */
	public void emptyOfBooks() {
		JPanel panel = view.getSliderPage().getUp();
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	public void loadFromFile(String filename) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Utils.getPreferences("Pathbase"))), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Path path = Paths.get(line).toRealPath();
				model.getPathbase().add(path.toString());
			}
		} catch (IOException e) {
		}
	}

	public void loadNextBooks() {
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

	public void loadPreviousBooks() {
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
	private BufferedImage saveImage(Path file) throws IOException {
		String s = Utils.getPreferences("Folder") + File.separator + MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";
		final File outputfile = new File(s);
		if (outputfile.exists())
			return null;
		final BufferedImage image = presenter.getCover(file);
		Utils.log(Utils.Debug.INFO, "Creating again image of " + image);
		if (Boolean.parseBoolean(Utils.getPreferences("Preview"))) {
			// Need an asyncronus way
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						ImageIO.write(image, "jpg", outputfile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(r).start();
		}
		return image;
	}

	void addImage(File file) throws IOException {
		String filename = Utils.getPreferences("Folder") + File.separator + MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";
		File imageFile = new File(filename);
		BufferedImage image = null;
		long startTime = System.currentTimeMillis();
		if (imageFile.exists()) {
			Utils.log(Utils.Debug.DEBUG, "L'immagine gia esiste.");
			image = ImageIO.read(imageFile);
		} else {
			view.setStatusText("Cercando nuovi file, potrebbe volerci un po...");
			image = saveImage(file.toPath());
			Utils.log(Utils.Debug.DEBUG, "Creazione immagine in corso: " + image);
		}
		Utils.log(Utils.Debug.ERROR, "RENDERING TIME: " + (System.currentTimeMillis() - startTime) + " for file " + file);
		Image img = image.getScaledInstance(-1, 160, Image.SCALE_DEFAULT);
		JButton picLabel = new JButton(new ImageIcon(img));
		String checksum = "";
		try {
			checksum = MD5Checksum.getMD5Checksum(file.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BookMetadata book = model.getLibrary().getDatabase().get(file.toString());
		if (book == null) {
			Utils.log(Utils.Debug.DEBUG, "The book is null (doesn't exist before)");
			book = new BookMetadata.Builder(checksum).titolo(file.getName().replaceFirst("[.][^.]+$", "")).build();
			model.getLibrary().getDatabase().put(file.toString(), book);
		} else {
			Utils.log(Utils.Debug.DEBUG, "The book is not null (already present)");
			Utils.log(Utils.Debug.DEBUG, book);
		}
		picLabel.addActionListener(new InnerListener(view, image, book));
		// picLabel.addActionListener(new
		// ActionListenerWhenClickingOnABook(file));
		picLabel.setBorder(null);
		view.getSliderPage().getUp().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
	}

	private static class InnerListener implements ActionListener {
		private BookMetadata book;
		private BufferedImage image;
		private View view;

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
