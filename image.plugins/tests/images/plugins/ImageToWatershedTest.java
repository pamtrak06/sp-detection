package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageToWatershed;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageToWatershedTest {

	@Test
	public void test() {
		try {
			ImageToWatershed imageProcess = new ImageToWatershed();
			imageProcess.setImagePath("images/piscines-spectrum_0.png");
			imageProcess.getNextParameter().setValue(5);
			imageProcess.process();
			File destImagePath = new File("images/piscines-segmented.png");
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
