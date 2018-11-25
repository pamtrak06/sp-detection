package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageSpectrumBlue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageSpectrumBlueTest {

	@Test
	public void test() {
		try {
			ImageSpectrumBlue imageProcess = new ImageSpectrumBlue();
			imageProcess.setImagePath("images/piscines.png");
			imageProcess.process();
			File destImagePath = new File("images/piscines-spectrum-blue.png");
			imageProcess.write(destImagePath.getAbsolutePath());
			assertTrue(destImagePath.exists());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertNotNull(null);
		}
	}

}
