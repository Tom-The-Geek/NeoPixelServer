package me.geek.tom.NeoPixelRemoteServer.particle.strip;

import com.github.mbelling.ws281x.Color;

public interface IStrip {
    void setPixel(int pixel, Color color);
    void setPixel(int pixel, int r, int g, int b);
    void show();
    int getLength();
}
