package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFReader extends EbookReader {
	private JButton back, forward;
	private final Path file;
	private JFrame frame;
	private JLabel label;
	private int page, totpages;

	PDFReader(String path) {
		super(path);
		file = Paths.get(path);
	}

	@Override
	public BufferedImage getCover() throws IOException {
		PDDocument doc = PDDocument.load(file.toFile());
		PDFRenderer renderer = new PDFRenderer(doc);
		BufferedImage img = renderer.renderImage(0);
		doc.close();
		return img;
	}

	@Override
	public void getFrame() throws IOException {
		frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		label = new JLabel();
		JPanel panel = new JPanel();
		panel.add(Box.createHorizontalGlue());
		panel.add(label);
		panel.add(Box.createHorizontalGlue());
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane);
		PDDocument doc = PDDocument.load(file.toFile());
		totpages = doc.getNumberOfPages();
		doc.close();
		aggiorna();
		back = new JButton("Back");
		forward = new JButton("Forward");
		back.setEnabled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (page > 0) {
					page--;
					try {
						aggiorna();
					} catch (IOException e1) {
						// TODO Needs check but this is called before so no error should be found
						e1.printStackTrace();
					}
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
					try {
						aggiorna();
					} catch (IOException e1) {
						// TODO Needs check but this is called before so no error should be found
						e1.printStackTrace();
					}
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

	private void aggiorna() throws IOException {
		PDDocument doc = PDDocument.load(file.toFile());
		PDFRenderer renderer = new PDFRenderer(doc);
		BufferedImage image;
		image = renderer.renderImage(page);
		label.setIcon(new ImageIcon(image));
		// ImageIO.write(image, "PNG", new File("custom-render.png"));
		doc.close();
	}

	@Override
	public int getPageNumber() {
		PDDocument doc;
		try {
			doc = PDDocument.load(file.toFile());
			int num = doc.getNumberOfPages();
			doc.close();
			return num;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
