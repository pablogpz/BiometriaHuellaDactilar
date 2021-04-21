package es.unex.giiis.bss.jgarciapft.model;

import es.unex.giiis.bss.jgarciapft.processors.HistogramCalculator;
import es.unex.giiis.bss.jgarciapft.processors.ImageStatistics;

public class FingerprintImage implements BaseImage {

    private final int width;
    private final int height;
    private final Character[][] pixels;

    private final MemoizedProperty<int[]> histogramMemoized;

    private final MemoizedProperty<Integer> minGrayMemoized;
    private final MemoizedProperty<Integer> maxGrayMemoized;
    private final MemoizedProperty<Integer> meanGrayMemoized;

    public FingerprintImage(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new Character[height][width];

        Object[] memoDependencies = {pixels};

        histogramMemoized = new MemoizedProperty<>(() -> HistogramCalculator.histogramFrom(this),
                memoDependencies);

        minGrayMemoized = new MemoizedProperty<>(() -> ImageStatistics.calculateMinGray(this),
                memoDependencies);

        maxGrayMemoized = new MemoizedProperty<>(() -> ImageStatistics.calculateMaxGray(this),
                memoDependencies);

        meanGrayMemoized = new MemoizedProperty<>(() -> ImageStatistics.calculateMeanGray(this),
                memoDependencies);
    }

    @Override
    public char getPixel(int x, int y) {
        if (validateXYCoord(x, y))
            return pixels[y][x];
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    @Override
    public void setPixel(int x, int y, char color) {
        if (validateXYCoord(x, y)) pixels[y][x] = color;
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    private boolean validateXYCoord(int x, int y) {
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

}

