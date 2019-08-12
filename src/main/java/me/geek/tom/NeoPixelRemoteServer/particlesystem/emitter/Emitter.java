package me.geek.tom.NeoPixelRemoteServer.particlesystem.emitter;

import me.geek.tom.NeoPixelRemoteServer.particlesystem.particle.Particle;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.particle.ParticleManager;

import java.util.Random;

public class Emitter {

    private long start_time;
    private int age;

    public Class<? extends Particle> getParticle() {
        return particle;
    }

    private Class<? extends Particle> particle;
    private int probability;
    private Random random = new Random();

    public Emitter(Class<? extends Particle> particle, int probability) {
        this.particle = particle;
        this.probability = probability;
        this.start_time = System.nanoTime() / 1000000;
        this.age = 0;
    }


    public void tick(ParticleManager manager) {

        this.age += (System.nanoTime() / 1000000) - start_time;

        if (random.nextInt(1001) <= probability && this.age >= 1000 / 20) {
            this.spawnParticle(manager);
            this.age = 0;
            this.start_time = System.nanoTime() / 1000000;
        }
    }

    public void spawnParticle(ParticleManager manager) {
        try {
            Particle newParticle = particle.newInstance();
            newParticle.setPos(random.nextInt(manager.getLength()));
            manager.spawnParticle(newParticle);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
