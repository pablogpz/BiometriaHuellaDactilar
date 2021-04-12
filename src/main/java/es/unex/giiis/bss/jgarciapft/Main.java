package es.unex.giiis.bss.jgarciapft;

import es.unex.giiis.bss.jgarciapft.helpers.GrayscaleToRGB;
import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.transformations.GrayscaleTransformation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static es.unex.giiis.bss.jgarciapft.transformations.GrayscaleTransformation.DerivationMethod;

public class Main {

    public static void main(String[] args) {
        try {
            // Open input image
            BufferedImage inputImage = ImageIO.read(
                    new File(Constants.DEFAULT_IMG_PATH + Constants.DEFAULT_IMG_NAME));

            // Process input image
            FingerprintImage grayscaleImage =
                    GrayscaleTransformation.toGrayscale(inputImage, DerivationMethod.MEAN_VALUE);
            BufferedImage rgbGrayscaleImage =
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(grayscaleImage);

            // Export processed image
            ImageExporter.exportImage(rgbGrayscaleImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
