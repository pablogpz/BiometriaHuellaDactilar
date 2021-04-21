package es.unex.giiis.bss.jgarciapft.processors;

import es.unex.giiis.bss.jgarciapft.model.BaseImage;

public class HistogramCalculator {

    public static int[] histogramFrom(BaseImage image) {
        int[] histogram = new int[256];

        // Get relative frequencies of gray values and build the histogram
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixelValue = image.getPixel(x, y);
                histogram[pixelValue]++;
            }
        }

        return histogram;
    }

}
