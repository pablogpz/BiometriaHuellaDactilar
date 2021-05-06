package es.unex.giiis.bss.jgarciapft.helpers;

import es.unex.giiis.bss.jgarciapft.model.BaseImage;

import java.awt.image.BufferedImage;

import static es.unex.giiis.bss.jgarciapft.helpers.GrayscaleToRGB.Variant.BnW;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.ON;

public class GrayscaleToRGB {

    public static BufferedImage grayscaleToRGB1GrayMode(BaseImage inputImage, int mode) {

        BufferedImage outputImage =
                new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                int grayValue = inputImage.getPixel(x, y);

                if (mode == BnW)
                    grayValue = grayValue == ON ? 0 : 255;

                int rgbPixel = (grayValue << 16 | grayValue << 8 | grayValue);

                outputImage.setRGB(x, y, rgbPixel);
            }
        }

        return outputImage;
    }

    public static class Variant {

        public static final int GRAYSCALE = 10;
        public static final int BnW = 20;

    }

}
