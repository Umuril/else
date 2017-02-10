package com.ELSE.presenter.reader;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

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

class PDFReader extends EbookReader {
	private JButton back, forward;
	private JFrame frame;
	private JLabel label;
	private int page, totpages;
	
	PDFReader(final Path path) {
		super(path);
	}
	
	private void aggiorna() throws IOException {
		final PDDocument doc = PDDocument.load(getPath().toFile());
		label.setIcon(new ImageIcon(new PDFRenderer(doc).renderImage(page)));
		doc.close();
	}
	
	@Override
	public BufferedImage getCover() throws IOException {
		final PDDocument doc = PDDocument.load(getPath().toFile());
		final BufferedImage img = new PDFRenderer(doc).renderImage(0);
		doc.close();
		return img;
	}
	
	@Override
	public void getFrame() throws IOException {
		frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		label = new JLabel();
		final JPanel panel = new JPanel();
		panel.add(Box.createHorizontalGlue());
		panel.add(label);
		panel.add(Box.createHorizontalGlue());
		final JScrollPane scrollPane = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane);
		final PDDocument doc = PDDocument.load(getPath().toFile());
		totpages = doc.getNumberOfPages();
		doc.close();
		aggiorna();
		back = new JButton("Back");
		forward = new JButton("Forward");
		back.setEnabled(false);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (page > 0) {
					page--;
					try {
						aggiorna();
					} catch (final IOException ex) {
						// TODO Needs check
						ex.printStackTrace();
					}
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
					try {
						aggiorna();
					} catch (final IOException ex) {
						// TODO Needs check
						ex.printStackTrace();
					}
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
		try {
			final PDDocument doc = PDDocument.load(getPath().toFile());
			final int num = doc.getNumberOfPages();
			doc.close();
			return num;
		} catch (final IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return 0;
	}
}
