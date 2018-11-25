package images.plugins;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import image.plugins.ImageSpectrum;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ImageSpectrumTest {

	@Test
	public void test() {
		try {
			int num = 0;

			Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();
			map.put(num++, new Object[] { .5, 0.35, 0.5, .75, 1.0, 1.0 });
//			map.put(num++, new Object[] { .5, 0, 0, .6, 1.0, 1.0 });
//			map.put(num++, new Object[] { .6, 0, 0, .65, 1.0, 1.0 });
//			map.put(num++, new Object[] { .65, 0, 0, .7, 1.0, 1.0 });
//			map.put(num++, new Object[] { .7,  0, 0, .75, 1.0, 1.0 });
			
			for (int i = 0; i < map.size(); i++) {
				ImageSpectrum imageProcess = new ImageSpectrum();
				imageProcess.setImagePath("images/Toulouse-piscines.png");
				Object[] objects = map.get(i);
				for (Object object : objects) {
					imageProcess.getNextParameter().setValue(object);
				}
				imageProcess.process();
				File destImagePath = new File("images/piscines-spectrum_" + i + ".png");
				imageProcess.write(destImagePath.getAbsolutePath());
				assertTrue(destImagePath.exists());
			}
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
