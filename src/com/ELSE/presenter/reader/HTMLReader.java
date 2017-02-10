package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.ELSE.view.MenuBar;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

class HTMLReader extends EbookReader {
	private String content;
	private JFXPanel jfxPanel;
	
	HTMLReader(final Path path) {
		super(path);
	}
	
	@Override
	public BufferedImage getCover() {
		try {
			return ImageIO.read(MenuBar.class.getResource("/small_html.jpg"));
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void getFrame() {
		final JFrame frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		final StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = Files.newBufferedReader(getPath(), Charset.defaultCharset())) {
			String str;
			while ((str = br.readLine()) != null)
				contentBuilder.append(str);
			br.close();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		content = contentBuilder.toString();
		// You should execute this part on the Event Dispatch Thread
		// because it modifies a Swing component
		jfxPanel = new JFXPanel();
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
		final JScrollPane scrollPane = new JScrollPane(jfxPanel);
		frame.getContentPane().add(scrollPane);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public int getPageNumber() {
		return 0;
	}
}