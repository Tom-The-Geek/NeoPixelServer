package me.geek.tom.NeoPixelRemoteServer.particlesystem.particle;

import com.github.mbelling.ws281x.Color;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.IStrip;

import java.util.Random;

@SuppressWarnings("unused")
public class Particle {

    private final long start_time;

    private boolean dead = false;

    public void setPos(int pos) {
        this.pos = pos;
    }

    // Track the particles position
    private int pos;

    private int age;

    public boolean isDead() {
        return dead;
    }

    private void makeDead() {
        this.dead = true;
    }

    private static final Color[][] particlePool = {
            {new Color(128, 0, 0), new Color(255, 0, 0), new Color(128, 0, 0)},
            {new Color(0, 128, 0), new Color(0, 255, 0), new Color(0, 128, 0)},
            {new Color(0, 0, 128), new Color(0, 0, 255), new Color(0, 0, 128)},
    };

    private final Color[] particle;

    public Particle() {
        this.pos = 0;
        this.age = 0;
        this.start_time = System.nanoTime() / 1000000;
        this.particle = particlePool[new Random().nextInt(particlePool.length)];
        for (Color c : this.particle) {
            System.out.println("R: " + c.getRed() + " G: " + c.getGreen() + " B: " + c.getBlue());
        }
        System.out.println();
    }



    public void draw(IStrip strip) {
        for (int i = 0; i < particle.length; i++) {
            int pixelPos = pos + i - 1;

            if (!(i >= strip.getLength() || pos < 0)) {
                strip.setPixel(pixelPos, particle[i]);
            }
        }
    }

    public void tick(ParticleManager manager) {
        this.age += (System.nanoTime() / 1000000) - start_time;
        // Time for the particle to die in Milliseconds
        int decayTime = 5000;
        if (this.age >= decayTime) {
            this.makeDead();
        }
    }

}
