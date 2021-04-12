package es.unex.giiis.bss.jgarciapft.helpers;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

import java.awt.image.BufferedImage;

public class GrayscaleToRGB {

    public static BufferedImage grayscaleToRGB1GrayMode(FingerprintImage inputFingerprintImage) {

        BufferedImage outputImage =
                new BufferedImage(inputFingerprintImage.getWidth(), inputFingerprintImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < inputFingerprintImage.getWidth(); x++) {
            for (int y = 0; y < inputFingerprintImage.getHeight(); y++) {
                int grayValue = inputFingerprintImage.getPixel(x, y);

                /*if (modo == 0) {
                    grayValue *= 255;
                }*/

                int rgbPixel = (255 << 24 | grayValue << 16 | grayValue << 8 | grayValue);

                outputImage.setRGB(x, y, rgbPixel);
            }
        }

        return outputImage;
    }

}
