package es.unex.giiis.bss.jgarciapft.transformations;

import es.unex.giiis.bss.jgarciapft.model.BaseImage;
import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

import java.awt.image.BufferedImage;

public class GrayscaleTransformation {

    public static BaseImage toGrayscale(BufferedImage inputImage, int derivationMethod) {

        BaseImage outputFingerprintImage = new FingerprintImage(inputImage.getWidth(), inputImage.getHeight());

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                int rgb = inputImage.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                int grayLevel;
                switch (derivationMethod) {
                    case DerivationMethod.MEAN_VALUE:
                        grayLevel = rgbToGray(r, g, b);
                        break;
                    case DerivationMethod.WEIGHTED_MEAN:
                        grayLevel = rgbToGrayWeighted(r, g, b);
                        break;
                    default:
                        throw new IllegalArgumentException(
                                String.format("%s isn't a valid derivation constant", derivationMethod));
                }

                outputFingerprintImage.setPixel(x, y, (char) grayLevel);
            }
        }

        return outputFingerprintImage;
    }

    private static int rgbToGray(int r, int g, int b) {
        return (r + g + b) / 3;
    }

    private static int rgbToGrayWeighted(int r, int g, int b) {
        return (int) ((0.2126 * r + 0.7152 * g + 0.0722 * b) / 3);
    }

    public static class DerivationMethod {
        public static final int MEAN_VALUE = 10;
        public static final int WEIGHTED_MEAN = 20;
    }

}
