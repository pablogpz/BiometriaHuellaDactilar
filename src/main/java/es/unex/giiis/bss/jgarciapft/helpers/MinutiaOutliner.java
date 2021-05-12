package es.unex.giiis.bss.jgarciapft.helpers;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.model.Minutia;
import es.unex.giiis.bss.jgarciapft.model.Tuple;

import java.awt.image.BufferedImage;
import java.util.Iterator;

public class MinutiaOutliner {

    private static final MinutiaColors MINUTIA_CUT_COLOR = MinutiaColors.BLUE;
    private static final MinutiaColors MINUTIA_BIFURCATION_COLOR = MinutiaColors.RED;

    private static final int OUTLINE_RADIUS_PX = 3;
    private static final int OUTLINE_BORDER_PX = 1;

    public static BufferedImage outlineMinutiae(FingerprintImage fingerprintImage) {
        BufferedImage bufferedFingerprintImage =
                GrayscaleToRGB.grayscaleToRGB1GrayMode(fingerprintImage, GrayscaleToRGB.Variant.BnW);

        Iterator<Minutia> minutiae = fingerprintImage.minutiaIterator();

        while (minutiae.hasNext()) {
            Minutia currentMinutia = minutiae.next();

            switch (currentMinutia.getType()) {
                case CUT:
                    drawOutlineForMinutia(
                            bufferedFingerprintImage,
                            new Tuple<>(currentMinutia.getX(), currentMinutia.getY()),
                            MINUTIA_CUT_COLOR);
                    break;
                case BIFURCATION:
                    drawOutlineForMinutia(
                            bufferedFingerprintImage,
                            new Tuple<>(currentMinutia.getX(), currentMinutia.getY()),
                            MINUTIA_BIFURCATION_COLOR);
                    break;
            }
        }

        return bufferedFingerprintImage;
    }

    private static void drawOutlineForMinutia(
            BufferedImage fingerprintImage,
            Tuple<Integer, Integer> centralPoint,
            MinutiaColors color) {

        int[][] outlineSections = {
                {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        int[][] radialBidimensionalIncrements = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

        for (int currentDrawSection = 0; currentDrawSection < outlineSections.length; currentDrawSection++) {
            for (int borderInc = 0; borderInc < OUTLINE_BORDER_PX; borderInc++) {
                int radiusWithBorder = OUTLINE_RADIUS_PX + borderInc;
                int[] drawSection = outlineSections[currentDrawSection];

                int stepX = centralPoint.first() +
                        radiusWithBorder * radialBidimensionalIncrements[currentDrawSection][0];
                int stepY = centralPoint.second() +
                        radiusWithBorder * radialBidimensionalIncrements[currentDrawSection][1];

                for (int i = 1; i <= radiusWithBorder; i++) {
                    fingerprintImage.setRGB(stepX, stepY, color.rgb());
                    stepX += drawSection[0];
                    stepY += drawSection[1];
                }
            }
        }
    }

    private enum MinutiaColors {
        RED(255 << 16), BLUE(255);

        private final int rgbVal;

        MinutiaColors(int rgbVal) {
            this.rgbVal = rgbVal;
        }

        public int rgb() {
            return rgbVal;
        }
    }

}
