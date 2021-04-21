package es.unex.giiis.bss.jgarciapft.processors;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

public class ImageStatistics {

    public static int calculateMinGray(FingerprintImage image) {
        int[] histogram = image.getHistogram();

        // The minimum gray value is the lowest index of the histogram with a non-zero count
        for (int idx = 0; idx < 256; idx++)
            if (histogram[idx] > 0) return idx;

        // Edge case. A completely black image
        return 255;
    }

    public static int calculateMaxGray(FingerprintImage image) {
        int[] histogram = image.getHistogram();

        // The maximum gray value is the highest index of the histogram with a non-zero count
        for (int idx = 255; idx >= 0; idx--)
            if (histogram[idx] > 0) return idx;

        // Edge case. A completely white image
        return 0;
    }

    public static int calculateMeanGray(FingerprintImage image) {
        int[] histogram = image.getHistogram();

        // Use the histogram to calculate faster the sum of all gray values
        int accumulator = 0;
        for (int i = 0; i < 256; i++) {
            accumulator += i * histogram[i];
        }

        return accumulator / (image.getWidth() * image.getHeight());
    }

}
