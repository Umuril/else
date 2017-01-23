package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.ELSE.view.MenuBar;

public class HTMLReader extends EbookReader {
	private JFXPanel jfxPanel;
	private String content;

	HTMLReader(String path) {
		super(path);
	}

	@Override
	public BufferedImage getCover() {
		URL url = MenuBar.class.getResource("/small_html.jpg");
		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
		// return new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void getFrame() {
		JFrame frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		/*JEditorPane jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		jEditorPane.setEditorKit(kit);
		Document doc = kit.createDefaultDocument();
		jEditorPane.setDocument(doc);
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(super.getPath()));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = contentBuilder.toString();
		System.out.println(content);
		jEditorPane.setText(content);
		JPanel panel = new JPanel();
		panel.add(Box.createHorizontalGlue());
		panel.add(jEditorPane);
		panel.add(Box.createHorizontalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);*/
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(super.getPath()));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
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
				WebView webView = new WebView();
				jfxPanel.setScene(new Scene(webView));
				webView.setDisabled(true);
				webView.getEngine().loadContent(content);
			}
		});
		JScrollPane scrollPane = new JScrollPane(jfxPanel);
		frame.getContentPane().add(scrollPane);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public int getPageNumber() {
		return 0;
	}
}
