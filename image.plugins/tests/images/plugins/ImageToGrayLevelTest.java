package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageToGrayLevel;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageToGrayLevelTest {

	@Test
	public void test() {
		try {
			ImageToGrayLevel imageProcess = new ImageToGrayLevel();
			imageProcess.setImagePath("images/piscines.png");
			imageProcess.process();
			File destImagePath = new File("images/piscines-gray.png");
			imageProcess.write(destImagePath.getAbsolutePath());
			assertTrue(destImagePath.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNotNull(null);
		}
	}

}
