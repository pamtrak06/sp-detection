package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageSmooth;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageSmoothTest {

	@Test
	public void test() {
		try {
			ImageSmooth imageProcess = new ImageSmooth();
			imageProcess.setImagePath("images/piscines.png");
			imageProcess.getNextParameter().setValue(2);
			imageProcess.process();
			File destImagePath = new File("images/piscines-smoothed.png");
			imageProcess.write(destImagePath.getAbsolutePath());
			assertTrue(destImagePath.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNotNull(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNotNull(null);
		}
	}

}
