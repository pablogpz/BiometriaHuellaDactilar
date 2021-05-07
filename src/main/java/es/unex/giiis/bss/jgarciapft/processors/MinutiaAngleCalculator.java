package es.unex.giiis.bss.jgarciapft.processors;

import es.unex.giiis.bss.jgarciapft.model.FingerprintImage;
import es.unex.giiis.bss.jgarciapft.model.Minutia;
import es.unex.giiis.bss.jgarciapft.model.MinutiaTypes;
import es.unex.giiis.bss.jgarciapft.model.Tuple;

import java.util.Iterator;

import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.BIFURCATION;
import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.CUT;
import static es.unex.giiis.bss.jgarciapft.transformations.BinaryThreshold.ThresholdValues.ON;

public class MinutiaAngleCalculator {

    final static int[][] neighborsSequence = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
    public static final int GRADIENT_NEIGHBORHOUD_WINDOW = 6;

    public static void calculateAnglesOfMinutiae(FingerprintImage inputImage) {

        Iterator<Minutia> minutiae = inputImage.minutiaIterator();

        while (minutiae.hasNext()) {
            Minutia currentMinutia = minutiae.next();

            MinutiaTypes type = currentMinutia.getType();

            if (type == CUT) {
                currentMinutia.setFirstAngle(angleOfBranch(inputImage, currentMinutia, 1));
            } else if (type == BIFURCATION) {
                for (int i = 1; i <= 3; i++) {
                    switch (i) {
                        case 1:
                            currentMinutia.setFirstAngle(angleOfBranch(inputImage, currentMinutia, 1));
                            break;
                        case 2:
                            currentMinutia.setSecondAngle(angleOfBranch(inputImage, currentMinutia, 2));
                            break;
                        case 3:
                            currentMinutia.setThirdAngle(angleOfBranch(inputImage, currentMinutia, 3));
                            break;
                    }
                }
            }
        }
    }

    private static double angleOfBranch(FingerprintImage inputImage, Minutia currentMinutia, int branchingIndex) {
        int startX = currentMinutia.getX();
        int startY = currentMinutia.getY();
        int finalX = startX;
        int finalY = startY;
        int nextNeighborIdx = -1;

        for (int i = 1; i <= GRADIENT_NEIGHBORHOUD_WINDOW; i++) {
            nextNeighborIdx = firstNeighbor(inputImage, new Tuple<>(finalX, finalY), nextNeighborIdx, i == 1 ? branchingIndex : 1);

            if (nextNeighborIdx == neighborsSequence.length) break;

            finalX += neighborsSequence[nextNeighborIdx][0];
            finalY += neighborsSequence[nextNeighborIdx][1];
            nextNeighborIdx = (nextNeighborIdx + neighborsSequence.length / 2) % (neighborsSequence.length);
        }

        float gradientX = finalX - startX;
        float gradientY = finalY - startY;

        return gradientX != 0 ? Math.toDegrees(Math.atan(gradientY / gradientX)) : 90d;
    }

    private static int firstNeighbor(FingerprintImage image, Tuple<Integer, Integer> centralPoint, int ignore, int branchingIndex) {
        int neighborIdx = 0;

        for (; neighborIdx < neighborsSequence.length && branchingIndex > 0; neighborIdx++) {
            if (neighborIdx == ignore) continue;

            int[] currentNeighbor = neighborsSequence[neighborIdx];

            if (image.getPixel(
                    centralPoint.first() + currentNeighbor[0],
                    centralPoint.second() + currentNeighbor[1])
                    == ON)
                branchingIndex--;
        }

        return neighborIdx - 1;
    }

}
