package es.unex.giiis.bss.jgarciapft;

import es.unex.giiis.bss.jgarciapft.helpers.GrayscaleToRGB;
import es.unex.giiis.bss.jgarciapft.helpers.ImageExporter;
import es.unex.giiis.bss.jgarciapft.model.BaseImage;
import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.transformations.GrayscaleTransformation;
import es.unex.giiis.bss.jgarciapft.transformations.ImageEqualizer;

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

            // Práctica 1. Transform input input to grayscale
            BaseImage grayscaleImage =
                    GrayscaleTransformation.toGrayscale(inputImage, DerivationMethod.MEAN_VALUE);

            // Práctica 2. Calculate the histogram and equalize the grayscale image
            FingerprintImage eqdGrayscaleImage = ImageEqualizer.equalizeGrayscaleImage((FingerprintImage) grayscaleImage);

            // Show processed image statistics
            System.out.printf("Processed image stats:\n\tMinimum gray value: %d\n\tMaximum gray value: %d\n\tMean gray value: %d\n",
                    eqdGrayscaleImage.getMinGray(), eqdGrayscaleImage.getMaxGray(), eqdGrayscaleImage.getMeanGray());

            // Export processed image
            BufferedImage rgbGrayscaleImage =
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(eqdGrayscaleImage);
            ImageExporter.exportImage(rgbGrayscaleImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
