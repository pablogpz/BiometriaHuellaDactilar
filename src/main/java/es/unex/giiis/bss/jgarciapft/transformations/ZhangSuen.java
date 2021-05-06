package es.unex.giiis.bss.jgarciapft.transformations;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.model.Tuple;

import java.util.ArrayList;
import java.util.List;

import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.OFF;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.ON;

public class ZhangSuen {

    final static int[][] nbrs = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1},
            {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}};

    final static int[][][] nbrGroups = {{{0, 2, 4}, {2, 4, 6}}, {{0, 2, 6},
            {0, 4, 6}}};

    static List<Tuple<Integer, Integer>> toWhite = new ArrayList<>();

    public static FingerprintImage thinImage(FingerprintImage inputImage) {
        FingerprintImage outputImage = new FingerprintImage(inputImage);
        boolean firstStep = false;
        boolean hasChanged;

        do {
            hasChanged = false;
            firstStep = !firstStep;

            for (int r = 1; r < outputImage.getWidth() - 1; r++) {
                for (int c = 1; c < outputImage.getHeight() - 1; c++) {

                    if (outputImage.getPixel(r, c) != (char) ON)
                        continue;

                    int nn = numNeighbors(outputImage, r, c);

                    if (nn < 2 || nn > 6)
                        continue;

                    if (numTransitions(outputImage, r, c) != 1)
                        continue;

                    if (!atLeastOneIsWhite(outputImage, r, c, firstStep ? 0 : 1))
                        continue;

                    toWhite.add(new Tuple<>(r, c));
                    hasChanged = true;
                }
            }

            for (Tuple<Integer, Integer> t : toWhite)
                outputImage.setPixel(t.first(), t.second(), (char) OFF);
            toWhite.clear();

        } while (firstStep || hasChanged);

        return outputImage;
    }

    private static int numNeighbors(FingerprintImage image, int r, int c) {
        int count = 0;

        for (int i = 0; i < nbrs.length - 1; i++)
            if (image.getPixel(r + nbrs[i][1], c + nbrs[i][0]) == (char) ON)
                count++;

        return count;
    }

    private static int numTransitions(FingerprintImage image, int r, int c) {
        int count = 0;

        for (int i = 0; i < nbrs.length - 1; i++)
            if (image.getPixel(r + nbrs[i][1], c + nbrs[i][0]) == (char) OFF) {
                if (image.getPixel(r + nbrs[i + 1][1], c + nbrs[i + 1][0]) == (char) ON)
                    count++;
            }

        return count;
    }

    private static boolean atLeastOneIsWhite(FingerprintImage image, int r, int c, int step) {
        int count = 0;
        int[][] group = nbrGroups[step];

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < group[i].length; j++) {
                int[] nbr = nbrs[group[i][j]];
                if (image.getPixel(r + nbr[1], c + nbr[0]) == (char) OFF) {
                    count++;
                    break;
                }
            }

        return count > 1;
    }

}
