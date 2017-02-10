package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

class EPUBReader extends EbookReader {
	private JButton back, forward;
	private String content;
	private JFrame frame;
	private JFXPanel jfxPanel;
	private int page;
	private Reader reader;
	private int totpages;
	
	EPUBReader(final Path path) {
		super(path);
	}
	
	private void aggiorna() {
		try {
			content = reader.readSection(page).getSectionContent();
		} catch (ReadingException | OutOfPagesException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		// Creation of scene and future interactions with JFXPanel
		// should take place on the JavaFX Application Thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final WebView webView = new WebView();
				jfxPanel.setScene(new Scene(webView));
				webView.setDisable(true);
				webView.getEngine().loadContent(content);
			}
		});
		jfxPanel.revalidate();
		jfxPanel.repaint();
	}
	
	@Override
	public BufferedImage getCover() {
		final EpubReader epubReader = new EpubReader();
		try (FileInputStream is = new FileInputStream(getPath().toFile())) {
			final Book book = epubReader.readEpub(is);
			final byte[] bytes = book.getCoverImage().getData();
			final BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
			return image;
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void getFrame() {
		reader = new Reader();
		reader.setIsIncludingTextContent(true);
		try {
			reader.setFullContent(getPath().toString());
		} catch (ReadingException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		jfxPanel = new JFXPanel();
		final JScrollPane scrollPane = new JScrollPane(jfxPanel);
		aggiorna();
		frame.getContentPane().add(scrollPane);
		back = new JButton("Back");
		forward = new JButton("Forward");
		back.setEnabled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
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
			public void actionPerformed(final ActionEvent e) {
				if (page < totpages) {
					page++;
					aggiorna();
				}
				back.setEnabled(page != 0);
				forward.setEnabled(page != totpages - 1);
			}
		});
		final JPanel lower = new JPanel();
		lower.add(back);
		lower.add(forward);
		frame.getContentPane().add(lower, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public int getPageNumber() {
		final Reader reader = new Reader();
		try {
			reader.setFullContent(getPath().toString());
			reader.readSection(Integer.MAX_VALUE);
		} catch (final OutOfPagesException ex) {
			return ex.getPageCount();
		} catch (final ReadingException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
}
