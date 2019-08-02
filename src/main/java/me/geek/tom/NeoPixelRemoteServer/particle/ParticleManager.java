package me.geek.tom.NeoPixelRemoteServer.particle;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.logging.Logger;

public class ParticleManager {

    private Logger LOGGER = Logger.getLogger(ParticleManager.class.getName());

    private List<Particle> particles = Lists.newArrayList();

    public void tick() {
        LOGGER.info("TICK!");
    }

}
