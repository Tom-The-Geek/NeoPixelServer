package me.geek.tom.neopixelremoteserver.particlesystem.particle;

import com.github.mbelling.ws281x.Color;
import me.geek.tom.neopixelremoteserver.particlesystem.strip.IStrip;

import java.util.Random;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Particle {

    private final long start_time;

    private boolean dead = false;

    // Track the particles position
    private int pos;

    // For how long can this particle exist?
    private int decayTime = 20000;

    private int age;

    private static final Color[][] particlePool = {
            {new Color(128, 0, 0), new Color(255, 0, 0), new Color(128, 0, 0)},
            {new Color(0, 128, 0), new Color(0, 255, 0), new Color(0, 128, 0)},
            {new Color(0, 0, 128), new Color(0, 0, 255), new Color(0, 0, 128)},
    };

    private final Color[] particle;

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isDead() {
        return dead;
    }

    private void makeDead() {
        this.dead = true;
    }

    public int getPos() { return this.pos; }

    public Particle() {
        this.pos = 0;
        this.age = 0;
        this.start_time = System.nanoTime() / 1000000;
        this.particle = particlePool[new Random().nextInt(particlePool.length)];
        /* for (Color c : this.particle) {
            System.out.println("R: " + c.getRed() + " G: " + c.getGreen() + " B: " + c.getBlue());
        }
        System.out.println();
        */
    }



    public void draw(IStrip strip) {
        float sf = ((float) this.age) / ((float) this.decayTime) * 2;
        for (int i = 0; i < particle.length; i++) {
            int pixelPos = pos + i - 1;

            if (!(i >= strip.getLength() || pos < 0)) {
                strip.setPixel(pixelPos, scaleColour(particle[i], sf));
            }
        }
    }

    public void tick(ParticleManager manager) {
        this.age += (System.nanoTime() / 1000000) - start_time;
        // Time for the particle to die in Milliseconds
        if (this.age >= decayTime) {
            this.makeDead();
        }
    }

    private Color scaleColour(Color colour, float sf) {
        int r = 1 - (int) Math.floor(colour.getRed() * sf);
        int g = 1 - (int) Math.floor(colour.getGreen() * sf);
        int b = 1 - (int) Math.floor(colour.getBlue() * sf);
        return new Color(r, g, b);
    }
}
