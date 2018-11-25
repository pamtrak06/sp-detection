package image.process;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Gray-Level Watershed Segmentation
 * 
 * @author Xavier Philippeau
 */
public class WatershedGrayLevel {

	// original gray-level image
	private int width, height;
	private int[][] image = null;
	private final int GRAYLEVEL = 256;

	// region map
	private int[][] rmap = null;
	private int maxid = 0;
	private int WATERSHED = -1;

	// pixel and list of pixel structure
	class Pixel {
		int x, y, level;

		public Pixel(int x, int y, int l) {
			this.x = x;
			this.y = y;
			this.level = l;
		}
	}

	class ListOfPixels extends LinkedList<Pixel> {
	}

	// list of pixels (one per level) to process
	private ListOfPixels[] explorelist;

	// offsets of the 8 neighbors
	private int[] dx8 = new int[] { -1, 0, 1, 1, 1, 0, -1, -1 };
	private int[] dy8 = new int[] { -1, -1, -1, 0, 1, 1, 1, 0 };

	// ---------------------------------------------------

	public WatershedGrayLevel(int[][] image, int width, int height, boolean isBrightOnDark) {
		this.image = image;
		this.width = width;
		this.height = height;
		// negative image if needed
		if (isBrightOnDark)
			for (int y = 0; y < height; y++)
				for (int x = 0; x < width; x++)
					this.image[x][y] = GRAYLEVEL - 1 - this.image[x][y];
	}

	// ------------------------------------------------------------------------------------

	// allocate memory
	private void init() {
		maxid = 0;
		this.rmap = new int[this.width][this.height];
		this.explorelist = new ListOfPixels[GRAYLEVEL];
		for (int i = 0; i < GRAYLEVEL; i++)
			this.explorelist[i] = new ListOfPixels();
	}

	// free memory
	private void clear() {
		this.rmap = null;
		this.explorelist = null;
	}

	// ------------------------------------------------------------------------------------

	// gray-level watershed algorithm : return the boolean watershep map
	public boolean[][] process(int step) {
		init();

		// flooding level by level
		int level = 0, yoffset = 0;
		while (level < GRAYLEVEL) {

			// extend region by exploring neighbors of known pixels
			while (true) {
				Pixel p = nextPixel(level, step);
				if (p == null)
					break;
				extend(p);
			}

			// find a new seed for this level
			Pixel seed = findSeed(level, yoffset);
			if (seed != null) {
				// create and assign a new region to this seed
				this.rmap[seed.x][seed.y] = (++maxid);
				yoffset = seed.y;

				// add this seed to the list of pixel to explore
				explorelist[level].add(seed);
			} else {
				// no more seed for this level -> next level
				level++;
				yoffset = 0;
			}
		}

		// build the watershed map
		boolean[][] shedmap = new boolean[this.width][this.height];
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				if (this.rmap[x][y] == WATERSHED)
					shedmap[x][y] = true;

		// free memory
		clear();

		// return the watershed map
		return shedmap;
	}

	// find a seed ( = unassigned pixel ) at the specified level
	private Pixel findSeed(int level, int yoffset) {
		for (int y = yoffset; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				if (this.image[x][y] == level && this.rmap[x][y] == 0)
					return new Pixel(x, y, level);

		return null;
	}

	// return the next pixel to explore
	private Pixel nextPixel(int level, int step) {
		// return the first pixel found in the explorelist
		for (int i = level; i < level + step && i < GRAYLEVEL; i++) {
			if (!explorelist[i].isEmpty())
				return explorelist[i].remove(0);
		}
		return null;
	}

	// explore the 8 neighbors of a pixel and set the region
	private void extend(Pixel p) {
		int region = this.rmap[p.x][p.y];

		// this pixel is a watershed => cannot extend it
		if (region == WATERSHED)
			return;

		// for each neighbor pixel
		for (int k = 0; k < 8; k++) {
			int xk = p.x + dx8[k];
			int yk = p.y + dy8[k];
			if (xk < 0 || xk >= this.width)
				continue;
			if (yk < 0 || yk >= this.height)
				continue;

			// level and region of neighbor
			int vk = this.image[xk][yk];
			int rk = this.rmap[xk][yk];

			// neighbor is a watershed => ignore
			if (rk == -1)
				continue;

			// neighbor as no region assigned => set it
			if (rk == 0) {
				this.rmap[xk][yk] = region;
				this.explorelist[vk].add(new Pixel(xk, yk, vk));
				continue;
			}

			// neighbor is assigned to the same region => nothing to do
			if (rk == region)
				continue;

			// neighbor is assigned to another region => it's a watershed
			this.rmap[xk][yk] = -1;
		}
	}


}
