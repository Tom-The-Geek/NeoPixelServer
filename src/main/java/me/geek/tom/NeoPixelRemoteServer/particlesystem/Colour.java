package me.geek.tom.NeoPixelRemoteServer.particlesystem;

import com.github.mbelling.ws281x.Color;

public class Colour {

    public static Color blendColours(Color c1, Color c2) {
        int r = Math.max(c1.getRed() + c2.getRed(), 255);
        int b = Math.max(c1.getGreen() + c2.getGreen(), 255);
        int g = Math.max(c1.getBlue() + c2.getBlue(), 255);
        return new Color(r, g, b);
    }

}
