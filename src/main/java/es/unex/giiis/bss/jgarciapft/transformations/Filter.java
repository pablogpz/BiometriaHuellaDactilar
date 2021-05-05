package es.unex.giiis.bss.jgarciapft.transformations;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.OFF;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.ON;

public class Filter {

    public static FingerprintImage filter(FingerprintImage inputImage) {
        FingerprintImage intermediateImage = new FingerprintImage(inputImage.getWidth(), inputImage.getHeight());
        FingerprintImage outputImage = new FingerprintImage(inputImage.getWidth(), inputImage.getHeight());
        boolean p, a, b, c, d, e, f, g, h, isFilled;

        for (int x = 1; x < inputImage.getWidth() - 1; x++) {
            for (int y = 1; y < inputImage.getHeight() - 1; y++) {
                p = inputImage.getPixel(x, y) != (char) OFF;
                b = inputImage.getPixel(x, y - 1) != (char) OFF;
                d = inputImage.getPixel(x - 1, y) != (char) OFF;
                e = inputImage.getPixel(x + 1, y) != (char) OFF;
                g = inputImage.getPixel(x, y + 1) != (char) OFF;

                isFilled = p || b && g && (d || e) || d && e && (b || g);

                intermediateImage.setPixel(x, y, (char) (isFilled ? ON : OFF));
            }
        }

        intermediateImage.fillMarginsWith((char) OFF);

        for (int x = 1; x < intermediateImage.getWidth() - 1; x++) {
            for (int y = 1; y < intermediateImage.getHeight() - 1; y++) {
                p = intermediateImage.getPixel(x, y) != (char) OFF;
                a = intermediateImage.getPixel(x - 1, y - 1) != (char) OFF;
                b = intermediateImage.getPixel(x, y - 1) != (char) OFF;
                c = intermediateImage.getPixel(x + 1, y - 1) != (char) OFF;
                d = intermediateImage.getPixel(x - 1, y) != (char) OFF;
                e = intermediateImage.getPixel(x + 1, y) != (char) OFF;
                f = intermediateImage.getPixel(x - 1, y + 1) != (char) OFF;
                g = intermediateImage.getPixel(x, y + 1) != (char) OFF;
                h = intermediateImage.getPixel(x + 1, y + 1) != (char) OFF;

                isFilled = p && ((a || b || d) && (e || g || h) || (b || c || e) && (d || f || g));

                outputImage.setPixel(x, y, (char) (isFilled ? ON : OFF));
            }
        }

        outputImage.fillMarginsWith((char) OFF);

        return outputImage;
    }

}
