package es.unex.giiis.bss.jgarciapft;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageExporter {

    public static void exportImage(BufferedImage image) {
        try {
            File outputImageFile = new File(Constants.DEFAULT_EXPORTED_IMG_PATH);
            ImageIO.write(image, Constants.DEFAULT_EXPORTED_IMG_EXTENSION, outputImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
