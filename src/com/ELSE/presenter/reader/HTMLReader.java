package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

public class HTMLReader implements EbookReader {
	@Override
	public BufferedImage getCover() {
		return new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
	}

	public HTMLReader(String path) {
		JFrame frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		JEditorPane jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		jEditorPane.setEditorKit(kit);
		Document doc = kit.createDefaultDocument();
		jEditorPane.setDocument(doc);
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}
		String content = contentBuilder.toString();
		jEditorPane.setText(content);
		JPanel panel = new JPanel();
		panel.add(Box.createHorizontalGlue());
		panel.add(jEditorPane);
		panel.add(Box.createHorizontalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
