package es.unex.giiis.bss.jgarciapft.transformations;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;

public class ImageEqualizer {

    public static FingerprintImage equalizeGrayscaleImage(FingerprintImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        int numberOfPixels = width * height;
        FingerprintImage eqdImage = new FingerprintImage(width, height);

        // Get the histogram
        int[] histogram = inputImage.getHistogram();

        int i;
        int sum = 0;

        // Build LUT to be used to equalize the input image
        float[] lut = new float[256];
        for (i = 0; i < 256; i++) {
            sum += histogram[i];
            lut[i] = (sum * 255f) / numberOfPixels;
        }

        // Equalize the input image using LUT table
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char eqdValue = (char) lut[inputImage.getPixel(x, y)];
                eqdImage.setPixel(x, y, eqdValue);
            }
        }

        return eqdImage;
    }

}
