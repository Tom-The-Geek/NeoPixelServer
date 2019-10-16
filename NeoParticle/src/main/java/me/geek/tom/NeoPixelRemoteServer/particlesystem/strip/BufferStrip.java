package me.geek.tom.NeoPixelRemoteServer.particlesystem.strip;

import com.github.mbelling.ws281x.Color;
import com.google.common.collect.Lists;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.Colour;

import java.util.List;
import java.util.logging.Logger;


@SuppressWarnings({"WeakerAccess", "unused"})
public class BufferStrip implements IStrip {

    private static final Logger LOGGER = Logger.getLogger(BufferStrip.class.getName());

    private List<Color> pixels;
    private final int length;
    private final IStrip destination;

    public BufferStrip(IStrip destination) {
        this.destination = destination;
        this.length = destination.getLength();
        this.clear();
    }

    public void setPixel(int pixel, Color color) {
        if (pixel < 0 || pixel >= this.length) {
            LOGGER.warning("Position is off the strip, ignoring." +
                    "\nPlease contact the developer if this message continues to occur.");
        } else {
            Color c = Colour.blendColours(color, getPixel(pixel));
            this.pixels.set(pixel, c);
        }
    }

    public void setPixel(int pixel, int r, int g, int b) {
        if (pixel >= 0 && pixel < this.length) {
            Color c = Colour.blendColours(new Color(r, g, b), getPixel(pixel));
            this.pixels.set(pixel, c);
        }
        // LOGGER.warning("Position is off the strip, ignoring." +
        //         "\nPlease contact the developer if this message continues to occur.");
        // Would be in an else body
    }

    public void show() {
        for (int pos = 0; pos < length; pos++) {
            destination.setPixel(pos, this.pixels.get(pos));
            // System.out.print(this.pixels.get(pos).getColorBits());
        }
        // System.out.println();
        destination.show();
        this.clear();
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

    public Color getPixel(int pos) {
        return this.pixels.get(pos);
    }
}
