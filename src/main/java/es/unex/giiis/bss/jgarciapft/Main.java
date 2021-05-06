package es.unex.giiis.bss.jgarciapft;

import es.unex.giiis.bss.jgarciapft.helpers.GrayscaleToRGB;
import es.unex.giiis.bss.jgarciapft.helpers.ImageExporter;
import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold;
import es.unex.giiis.bss.jgarciapft.transformations.Filter;
import es.unex.giiis.bss.jgarciapft.transformations.GrayscaleTransformation;
import es.unex.giiis.bss.jgarciapft.transformations.ZhangSuen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static es.unex.giiis.bss.jgarciapft.helpers.GrayscaleToRGB.Variant;
import static es.unex.giiis.bss.jgarciapft.transformations.GrayscaleTransformation.DerivationMethod;

public class Main {

    public static void main(String[] args) {
        String inputImageFile;

        if (args.length == 0) inputImageFile = Constants.DEFAULT_IMG_NAME;
        else inputImageFile = args[0];

        try {
            // Open input image
            BufferedImage inputImage = ImageIO.read(
                    new File(Constants.DEFAULT_IMG_PATH + inputImageFile));

            // Práctica 1. Transform input to grayscale
            FingerprintImage grayscaleImage =
                    GrayscaleTransformation.toGrayscale(inputImage, DerivationMethod.MEAN_VALUE);
            ImageExporter.exportImage(
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(grayscaleImage, Variant.GRAYSCALE),
                    "fingerprint-grayscale");

            // Práctica 2. Calculate the histogram and equalize the grayscale image
//            FingerprintImage eqdGrayscaleImage = ImageEqualizer.equalizeGrayscaleImage(grayscaleImage);
//            ImageExporter.exportImage(
//                    GrayscaleToRGB.grayscaleToRGB1GrayMode(eqdGrayscaleImage, Variant.GRAYSCALE),
//                    "fingerprint-equalized-grayscale");

            // Práctica 3. Apply binary threshold and filter
            FingerprintImage thresholdBinEqdImage = BinaryThreshold.binaryThreshold(grayscaleImage, -60);
            ImageExporter.exportImage(
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(thresholdBinEqdImage, Variant.BnW),
                    "fingerprint-BW-threshold-equalized-grayscale");
            FingerprintImage filteredThresholdBinEqdImage = Filter.filter(thresholdBinEqdImage);
            ImageExporter.exportImage(
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(filteredThresholdBinEqdImage, Variant.BnW),
                    "fingerprint-BW-filtered-threshold-equalized-grayscale");

            // Práctica 4. ZhangSuen algorithm
            FingerprintImage thinnedThresholdBinEqdImage = ZhangSuen.thinImage(thresholdBinEqdImage);

            // Export processed image
            ImageExporter.exportImage(
                    GrayscaleToRGB.grayscaleToRGB1GrayMode(thinnedThresholdBinEqdImage, Variant.BnW),
                    "fingerprint-BW-thinned-filtered-threshold-equalized-grayscale");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
