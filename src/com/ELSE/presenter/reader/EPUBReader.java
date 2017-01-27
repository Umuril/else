package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import com.github.mertakdut.BookSection;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;

class EPUBReader extends EbookReader {
	private JButton back, forward;
	private final File file;
	private JFrame frame;
	private int page;
	private final int totpages = 100;
	private String content;
	private JFXPanel jfxPanel;
	private JScrollPane scrollPane;

	EPUBReader(String path) {
		super(path);
		file = new File(path);
	}

	@Override
	public BufferedImage getCover() {
		/*
		 * URL settings = MenuBar.class.getResource("/epub.jpg"); BufferedImage image = null; try { image = ImageIO.read(settings); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } return image;
		 */
		EpubReader epubReader = new EpubReader();
		try {
			Book book = epubReader.readEpub(new FileInputStream(file));
			byte[] bytes = book.getCoverImage().getData();
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
			return image;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public void getFrame() {
		frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		// You should execute this part on the Event Dispatch Thread
		// because it modifies a Swing component
		jfxPanel = new JFXPanel();
		scrollPane = new JScrollPane(jfxPanel);
		aggiorna();
		frame.getContentPane().add(scrollPane);
		// aggiorna();
		back = new JButton("Back");
		forward = new JButton("Forward");
		back.setEnabled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (page > 0) {
					page--;
					aggiorna();
				}
				back.setEnabled(page != 0);
				forward.setEnabled(page != totpages - 1);
			}
		});
		forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (page < totpages) {
					page++;
					aggiorna();
				}
				back.setEnabled(page != 0);
				forward.setEnabled(page != totpages - 1);
			}
		});
		JPanel lower = new JPanel();
		lower.add(back);
		lower.add(forward);
		frame.getContentPane().add(lower, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void aggiorna() {
		try {
			Reader reader = new Reader();
			// reader.setMaxContentPerSection(1000); // Max string length for the current page.
			reader.setIsIncludingTextContent(true); // Optional, to return the tags-excluded version.
			reader.setFullContent(path); // Must call before readSection.
			BookSection bookSection = reader.readSection(page);
			// System.out.println(bookSection.getSectionContent());
			content = bookSection.getSectionContent(); // Returns content as html.
		} catch (ReadingException | OutOfPagesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Creation of scene and future interactions with JFXPanel
		// should take place on the JavaFX Application Thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				WebView webView = new WebView();
				jfxPanel.setScene(new Scene(webView));
				webView.getEngine().loadContent(content);
			}
		});
		jfxPanel.revalidate();
		jfxPanel.repaint();
	}

	@Override
	public int getPageNumber() {
		Reader reader = new Reader();
		try {
			reader.setFullContent(path); // Must call before readSection.
			reader.readSection(Integer.MAX_VALUE);
		} catch (OutOfPagesException e) {
			return e.getPageCount();
		} catch (ReadingException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
