package com.ELSE.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

@SuppressWarnings("serial")
public class PDFReader extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel label;
	private int page, totpages;
	private File file;
	private JFrame frame;
	private JButton back, forward;

	public PDFReader(String path) {
		frame = new JFrame("Viewer");
		frame.setBounds(100, 100, 800, 500);
		frame.getContentPane().setLayout(new BorderLayout());

		file = new File(path);

		label = new JLabel();

		JPanel panel = new JPanel();
		panel.add(Box.createHorizontalGlue());
		panel.add(label);
		panel.add(Box.createHorizontalGlue());

		JScrollPane scrollPane = new JScrollPane(panel);

		frame.getContentPane().add(scrollPane);

		PDDocument doc;
		try {
			doc = PDDocument.load(file);
			totpages = doc.getNumberOfPages();
			doc.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void aggiorna() {
		PDDocument doc = null;
		try {
			doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			BufferedImage image;
			image = renderer.renderImage(page);

			label.setIcon(new ImageIcon(image));
			// ImageIO.write(image, "PNG", new File("custom-render.png"));
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
