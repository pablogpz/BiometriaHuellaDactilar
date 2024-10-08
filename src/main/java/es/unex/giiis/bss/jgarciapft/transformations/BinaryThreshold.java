package es.unex.giiis.bss.jgarciapft.transformations;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.OFF;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.ON;

public class BinaryThreshold {

    public static FingerprintImage binaryThreshold(FingerprintImage inputImage, int thresholdCorrection) {
        FingerprintImage outputImage = new FingerprintImage(inputImage.getWidth(), inputImage.getHeight());
        int meanGray = inputImage.getMeanGray();

        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                if (inputImage.getPixel(x, y) < (meanGray + thresholdCorrection)) outputImage.setPixel(x, y, (char) ON);
                else outputImage.setPixel(x, y, (char) OFF);
            }
        }

        return outputImage;
    }

    public static class ThresholdValues {

        public static final int OFF = 0;
        public static final int ON = 1;

    }

}
