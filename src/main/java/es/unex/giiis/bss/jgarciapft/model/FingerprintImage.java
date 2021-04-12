package es.unex.giiis.bss.jgarciapft.model;

public class FingerprintImage {

    private final int width;
    private final int height;
    private final char[][] pixels;

    public FingerprintImage(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new char[height][width];
    }

    public char getPixel(int x, int y) {
        if (validateXYCoord(x, y))
            return pixels[y][x];
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    public void setPixel(int x, int y, char color) {
        if (validateXYCoord(x, y)) pixels[y][x] = color;
        else
            throw new IndexOutOfBoundsException(
                    String.format("XY coordinates (%d-%d) out of the pixels matrix", x, y));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean validateXYCoord(int x, int y) {
        return x < getWidth() && y < getHeight();
    }

}

