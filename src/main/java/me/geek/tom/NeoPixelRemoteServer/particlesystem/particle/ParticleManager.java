package me.geek.tom.NeoPixelRemoteServer.particlesystem.particle;

import com.google.common.collect.Lists;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.emitter.Emitter;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.BufferStrip;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.IStrip;

import java.util.List;
import java.util.logging.Logger;

public class ParticleManager {

    private BufferStrip buffer;
    private IStrip outputStrip;

    private Logger LOGGER = Logger.getLogger(ParticleManager.class.getName());

    private List<Particle> particles = Lists.newArrayList();
    private List<Emitter> emitters = Lists.newArrayList();

    public ParticleManager(IStrip strip) {
        this.outputStrip = strip;
        this.buffer = new BufferStrip(strip);
        // LOGGER.setLevel(Level.ALL);
    }

    public void tick() {
        for (Emitter em : emitters) {
            em.tick(this);
        }

        // TODO: Add second level emitter stuff for

        List<Particle> deadParticles = Lists.newArrayList();

        for (Particle p : particles) {
            p.tick(this);
            if (p.isDead()) {
                deadParticles.add(p);
            }
        }

        for (Particle p : deadParticles) {
            particles.remove(p);
        }

        deadParticles = Lists.newArrayList();

        for (Particle p : particles) {
            p.draw(buffer);
        }

        // System.out.println(buffer == null);

        buffer.show();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void spawnParticle(Particle particle) {
        this.particles.add(particle);
    }

    public int getLength() {
        return outputStrip.getLength();
    }

    public void registerEmitter(Emitter em) {
        this.emitters.add(em);
    }
    public void registerEmitters(Emitter[] emitters) {
        for (Emitter em : emitters) { registerEmitter(em); }
    }

}
