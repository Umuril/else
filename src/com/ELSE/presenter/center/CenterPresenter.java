package com.ELSE.presenter.center;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.ELSE.model.BookMetadata;
import com.ELSE.model.MD5Checksum;
import com.ELSE.model.Model;
import com.ELSE.presenter.Presenter;
import com.ELSE.view.View;

public class CenterPresenter {
	private View view;
	private Model model;
	private FileSearcher fileSearcher;

	public CenterPresenter(View view, Model model, Presenter presenter) {
		this.view = view;
		this.model = model;
		fileSearcher = new FileSearcher(this, model.getPathbase(), 0);
		fileSearcher.searchAndAddFile();
	}

	public ActionListener clickOnABook(BufferedImage image, BookMetadata book) {
		return new ListenerBookClick(view, image, book);
	}

	public ActionListener backFromBookDetail() {
		return new ListenerBackButton(this, view);
	}

	public ActionListener setBookDetailPageEditable() {
		return new ListenerEditButton(view);
	}

	public ActionListener saveBookDetailPageChanges(BookMetadata book) {
		return new ListenerSaveButton(view, model, book);
	}

	public MouseListener openBook(BookMetadata book) {
		for (Entry<String, BookMetadata> entry : model.getLibrary().getDatabase().entrySet()) {
			if (entry.getValue().equals(book))
				return new ListenerBookPreviewClick(Paths.get(entry.getKey()), presenter);
		}
		return null;
	}

	public void change(Image image, BookMetadata book) {
		view.change(image, book);
	}

	public void loadFromFile(String filename) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("db.txt")), Charset.defaultCharset()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Path path = Paths.get(line).toRealPath();
				model.getPathbase().add(path.toString());
			}
		} catch (IOException e) {
		}
	}

	public void aggiorna(int page) {
		view.getUpSlider().removeAll();
		if(page < 0)
			page = fileSearcher.getPage();
		fileSearcher = new FileSearcher(this, model.getPathbase(), page);
		fileSearcher.searchAndAddFile();
		// Needed on delete
		view.getUpSlider().revalidate();
		view.getUpSlider().repaint();
	}

	public void loadNextBooks() {
		view.getUpSlider().removeAll();
		fileSearcher.findNext();
		// Needed on delete
		view.getUpSlider().revalidate();
		view.getUpSlider().repaint();
	}

	public void loadPreviousBooks() {
		int page = fileSearcher.getPage();
		System.out.println("Reloading books to page: " + page);
		if (page > 0) {
			view.getUpSlider().removeAll();
			fileSearcher = new FileSearcher(this, model.getPathbase(), page - 1);
			fileSearcher.searchAndAddFile();
			// Needed on delete
			view.getUpSlider().revalidate();
			view.getUpSlider().repaint();
		}
	}

	void addImage(File file) throws IOException {
		String filename = System.getProperty("user.home") + File.separator + ".else" + File.separator + MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";
		File imageFile = new File(filename);
		view.setStatusText("Trying to read: " + file.toPath());
		BufferedImage image = null;
		if (imageFile.exists()) {
			System.out.println("L'immagine gia esiste.");
			image = ImageIO.read(imageFile);
		} else {
			image = saveImage(file.toPath());
			System.out.println("Creazione immagine in corso: " + image);
		}
		Image img = image.getScaledInstance(-1, 180, Image.SCALE_DEFAULT);
		JButton picLabel = new JButton(new ImageIcon(img));
		String checksum = "";
		try {
			checksum = MD5Checksum.getMD5Checksum(file.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BookMetadata book = model.getLibrary().getDatabase().get(file.toString());
		if (book == null) {
			System.out.println("The book is null (doesn't exist before)");
			book = new BookMetadata();
			book.setChecksum(checksum);
			model.getLibrary().getDatabase().put(file.toString(), book);
		} else {
			System.out.println("The book is not null (already present)");
			System.out.println(book);
		}
		view.setStatusText("COMIIIIING");
		picLabel.addActionListener(new ListenerBookClick(view, image, book));
		// picLabel.addActionListener(new
		// ActionListenerWhenClickingOnABook(file));
		picLabel.setBorder(null);
		view.getUpSlider().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
	}

	private BufferedImage saveImage(Path file) throws IOException {
		view.setStatusText("Creating image of " + file.toFile());
		String s = System.getProperty("user.home") + File.separator + ".else" + File.separator + MD5Checksum.getMD5Checksum(file.toString()) + ".jpg";
		final File outputfile = new File(s);
		if (outputfile.exists())
			return null;
		final BufferedImage image = presenter.getCover(file);
		System.out.println("Creating again image of " + image);
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
		return image;
	}

	public void addImage(BookMetadata book) throws IOException {
		String filename = System.getProperty("user.home") + File.separator + ".else" + File.separator + book.getChecksum() + ".jpg";
		File imageFile = new File(filename);
		BufferedImage image = null;
		if (imageFile.exists())
			image = ImageIO.read(imageFile);
		else
			System.err.println("Error 404");
		Image img = image.getScaledInstance(-1, 180, Image.SCALE_DEFAULT);
		JButton picLabel = new JButton(new ImageIcon(img));
		if (book != null) {
			System.out.println("The book is not null (already present)");
			System.out.println(book);
		}
		picLabel.addActionListener(new ListenerBookClick(view, image, book));
		picLabel.setBorder(null);
		view.getUpSlider().add(picLabel);
		picLabel.revalidate();
		picLabel.repaint();
	}

	public void emptyOfBooks() {
		view.getUpSlider().removeAll();
		view.getUpSlider().revalidate();
		view.getUpSlider().repaint();
	}

	public ActionListener backBooks() {
		return new ListenerPreviousBooks(this);
	}

	public ActionListener forwardBooks() {
		return new ListenerForwardBooks(this);
	}

	public ActionListener gridView() {
		return new ListenerGridView(this);
	}

	public ActionListener listView() {
		return new ListenerListView(this);
	}
}
