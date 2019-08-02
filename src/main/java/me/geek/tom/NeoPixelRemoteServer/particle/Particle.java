package me.geek.tom.NeoPixelRemoteServer.particle;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.Ws281xLedStrip;

public class Particle {

    private long start_time;

    private boolean dead = false;

    // Track the particles position
    private int pos;

    private int age;

    // Time for the particle to die in Milliseconds
    private static int decayTime = 2000;

    public boolean isDead() {
        return dead;
    }

    public void dead() {
        this.dead = true;
    }

    private static Color[] particle = {
            new Color(128, 0, 0),
            new Color(255, 0, 0),
            new Color(128, 0, 0),

    };

    public Particle(int pos) {
        this.pos = pos;
        this.age = 0;
        this.start_time = System.nanoTime() / 1000000;
    }



    public void draw(Ws281xLedStrip strip) {
        for (int i = 0; i < particle.length; i++) {
            int pixelPos = pos + i - 1;

            strip.setPixel(pixelPos, particle[i]);
        }
    }

    public void tick(ParticleManager manager) {
        this.age += (System.nanoTime() / 1000000) - start_time;
        if (this.age >= decayTime) {
            this.dead();
        }
    }

}
