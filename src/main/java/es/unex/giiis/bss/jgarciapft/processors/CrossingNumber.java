package es.unex.giiis.bss.jgarciapft.processors;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.model.Minutia;
import es.unex.giiis.bss.jgarciapft.model.MinutiaTypes;
import es.unex.giiis.bss.jgarciapft.model.Tuple;

import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.BIFURCATION;
import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.CUT;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.OFF;

public class CrossingNumber {

    final static int[][] neighborsSequence = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}};

    public static void crossingNumber(
            FingerprintImage inputImage,
            Tuple<Integer, Integer> upperBoundCorner,
            Tuple<Integer, Integer> lowerBoundCorner) {

        for (int x = upperBoundCorner.first(); x < lowerBoundCorner.first(); x++) {
            for (int y = upperBoundCorner.second(); y < lowerBoundCorner.second(); y++) {

                if (inputImage.getPixel(x, y) == OFF) continue;

                int cnp = 0;
                for (int i = 0; i < neighborsSequence.length - 1; i++) {
                    int[] currentNeighbor = neighborsSequence[i];
                    int[] nextNeighbor = neighborsSequence[i + 1];

                    cnp += Math.abs(
                            inputImage.getPixel(x + currentNeighbor[0], y + currentNeighbor[1]) -
                                    inputImage.getPixel(x + nextNeighbor[0], y + nextNeighbor[1])
                    );
                }

                cnp /= 2;

                if (cnp == CUT.num() || cnp == BIFURCATION.num())
                    inputImage.addMinutia(new Minutia(x, y, MinutiaTypes.fromVal(cnp)));
            }
        }
    }

}
