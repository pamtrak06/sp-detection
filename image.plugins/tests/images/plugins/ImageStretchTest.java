package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageSmooth;
import image.plugins.ImageStretch;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ImageStretchTest {

	@Test
	public void test() {
		try {
			ImageStretch imageProcess = new ImageStretch();
			imageProcess.setImagePath("images/piscines.png");
			imageProcess.getNextParameter().setValue(50);
			imageProcess.getNextParameter().setValue(200);
			imageProcess.process();
			File destImagePath = new File("images/piscines-stretch.png");
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
