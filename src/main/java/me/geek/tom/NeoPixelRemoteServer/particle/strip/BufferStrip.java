package me.geek.tom.NeoPixelRemoteServer.particle.strip;

import com.github.mbelling.ws281x.Color;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.logging.Logger;


public class BufferStrip implements IStrip {

    private static Logger LOGGER = Logger.getLogger(BufferStrip.class.getName());

    private List<Color> pixels;
    private int length;
    private IStrip dest;

    public BufferStrip(IStrip dest) {
        this.dest = dest;
        this.length = dest.getLength();
    }

    public void setPixel(int pixel, Color color) {
        if (pixel < 0 || pixel >= this.length) {
            LOGGER.warning("Position is off the strip, ignoring." +
                    "\nPlease contact the developer if this message continues to occur.");
        }
        this.pixels.set(pixel, color);
    }

    public void setPixel(int pixel, int r, int g, int b) {
        if (pixel < 0 || pixel >= this.length) {
            LOGGER.warning("Position is off the strip, ignoring." +
                    "\nPlease contact the developer if this message continues to occur.");
        }
        this.pixels.set(pixel, new Color(r, g, b));
    }

    public void show() {
        for (int pos = 0; pos < length; pos++) {
            dest.setPixel(pos, this.pixels.get(pos));
        }
    }

    public int getLength() {
        return length;
    }

    public void clear() {
        this.pixels = Lists.newArrayList();
        for (int i = 0; i < this.length; i++) {
            this.pixels.add(new Color(0, 0, 0));
        }
    }
}
