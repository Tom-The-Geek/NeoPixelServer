package me.geek.tom.NeoPixelRemoteServer.particlesystem;

import com.github.mbelling.ws281x.Color;

public class Colour {

    public static Color blendColours(Color c1, Color c2) {
        int r = Math.min(c1.getRed() + c2.getRed(), 255);
        int b = Math.min(c1.getGreen() + c2.getGreen(), 255);
        int g = Math.min(c1.getBlue() + c2.getBlue(), 255);
        return new Color(r, g, b);
    }

}
