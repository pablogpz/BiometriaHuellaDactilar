package es.unex.giiis.bss.jgarciapft.helpers;

import es.unex.giiis.bss.jgarciapft.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageExporter {

    public static void exportImage(BufferedImage image, String path) {
        exportImageWithExtension(image, path, Constants.DEFAULT_EXPORTED_IMG_EXTENSION);
    }

    public static void exportImageWithExtension(BufferedImage image, String path, String extension) {
        try {
            File outputImageFile = new File(path + "." + extension);
            ImageIO.write(image, extension, outputImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
