package es.unex.giiis.bss.jgarciapft.model;

public interface BaseImage {

    int getWidth();

    int getHeight();

    char getPixel(int x, int y);

    void setPixel(int x, int y, char color);

}
