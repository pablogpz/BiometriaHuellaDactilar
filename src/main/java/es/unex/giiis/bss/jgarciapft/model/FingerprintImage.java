package es.unex.giiis.bss.jgarciapft.model;

import es.unex.giiis.bss.jgarciapft.processors.HistogramCalculator;
import es.unex.giiis.bss.jgarciapft.processors.ImageStatistics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static es.unex.giiis.bss.jgarciapft.model.MinutiaTypes.CUT;

public class FingerprintImage implements BaseImage {

    private final int width;
    private final int height;
    private final Character[][] pixels;
    private final List<Minutia> minutiae;

    private final MemoizedValue<int[]> histogramMemoized;
    private final MemoizedValue<Integer> minGrayMemoized;
    private final MemoizedValue<Integer> maxGrayMemoized;
    private final MemoizedValue<Integer> meanGrayMemoized;

    public FingerprintImage(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new Character[height][width];
        minutiae = new ArrayList<>();

        Object[] memoDependencies = {pixels};

        histogramMemoized = new MemoizedValue<>(() -> HistogramCalculator.histogramFrom(this),
                memoDependencies);

        minGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMinGray(this),
                memoDependencies);

        maxGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMaxGray(this),
                memoDependencies);

        meanGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMeanGray(this),
                memoDependencies);
    }

    public FingerprintImage(FingerprintImage original) {
        width = original.getWidth();
        height = original.getHeight();
        pixels = new Character[height][width];
        minutiae = new ArrayList<>();

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                pixels[y][x] = original.getPixel(x, y);
            }
        }

        Object[] memoDependencies = {pixels};

        histogramMemoized = new MemoizedValue<>(() -> HistogramCalculator.histogramFrom(this),
                memoDependencies);

        minGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMinGray(this),
                memoDependencies);

        maxGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMaxGray(this),
                memoDependencies);

        meanGrayMemoized = new MemoizedValue<>(() -> ImageStatistics.calculateMeanGray(this),
                memoDependencies);
    }

    @Override
    public char getPixel(int x, int y) {
        if (validateXYCoordinate(x, y))
            return pixels[y][x];
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    @Override
    public void setPixel(int x, int y, char color) {
        if (validateXYCoordinate(x, y)) pixels[y][x] = color;
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    public void fillMarginsWith(char filler) {
        for (int x = 0; x < getWidth(); x++) {
            setPixel(x, 0, filler);
            setPixel(x, getHeight() - 1, filler);
        }

        for (int y = 1; y < getHeight() - 1; y++) {
            setPixel(0, y, filler);
            setPixel(getWidth() - 1, y, filler);
        }
    }

    public void addMinutia(Minutia minutia) {
        if (minutia == null) return;

        minutiae.add(minutia);
    }

    public Iterator<Minutia> minutiaIterator() {
        return minutiae.listIterator();
    }

    private boolean validateXYCoordinate(int x, int y) {
        return x < getWidth() && y < getHeight();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int[] getHistogram() {
        return histogramMemoized.get();
    }

    public int getMinGray() {
        return minGrayMemoized.get();
    }

    public int getMaxGray() {
        return maxGrayMemoized.get();
    }

    public int getMeanGray() {
        return meanGrayMemoized.get();
    }

    public void logMinutiae() {
        for (Minutia minutia : minutiae) {
            MinutiaTypes minutiaType = minutia.getType();
            System.out.printf("[Minutia %s] at (%d, %d) with angle(s) (" + (minutiaType == CUT ? "%.2f" : "%.2f | %.2f | %.2f") + ")\n",
                    minutiaType,
                    minutia.getX(),
                    minutia.getY(),
                    minutia.getAngles().first(),
                    minutia.getAngles().second(),
                    minutia.getAngles().third());
        }
    }

}

