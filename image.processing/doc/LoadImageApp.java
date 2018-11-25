package image.ui;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class LoadImageApp extends Component {

	private BufferedImage img;
	BufferedImage outImg;

	private String filename_;

	public void paint(Graphics g) {
		//processDetection();
		g.drawImage(img, 0, 0, null);
		/* Draw the image, applying the filter */
		// float[] scales = { 1f, 1f, 1f, 0.5f };
		// float[] offsets = new float[4];
		// RescaleOp rop = new RescaleOp(scales, offsets, null);
		// ((Graphics2D)g).drawImage(img, rop, 0, 0);

	}

	public LoadImageApp(BufferedImage img) {
		this.img = img;
	}
	
	public LoadImageApp(String filename) {
		this.filename_ = filename;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
		}

		// int w = img.getWidth(null);
		// int h = img.getHeight(null);
		// BufferedImage bi = new
		// BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		// Graphics g = bi.getGraphics();
		// g.drawImage(img, 0, 0, null);

		// float[] scales = { 1f, 1f, 1f, 0.5f };
		// float[] offsets = new float[4];
		// RescaleOp rop = new RescaleOp(scales, offsets, null);

		/* Draw the image, applying the filter */
		// ((Graphics2D)g).drawImage(bi, rop, 0, 0);
		// img = bi;

	}

	public void processDetection() {
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		BufferedImage outImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				// int color = img.getRGB(x, y);
				Graphics g = img.getGraphics();
				Color color = g.getColor();
				Graphics2D g2 = (Graphics2D) outImg.getGraphics();
				if (color.getBlue()> 0) {
					g2.setColor(Color.white);
				} else {
					g2.setColor(Color.black);
				}

			}
		}
		try {
			ImageIO.write(outImg, "PNG", new File("data/output.png"));
		} catch (IOException e) {
			System.err.println("Unable to output results");
		}
	}

	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Load Image Sample");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.add(new LoadImageApp("data/Toulouse-piscines.png"));
		f.pack();
		f.setVisible(true);
	}
}
