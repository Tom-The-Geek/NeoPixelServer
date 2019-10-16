package me.geek.tom.NeoPixelRemoteServer;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.emitter.Emitter;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.particle.Particle;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.particle.ParticleManager;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.HWStrip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@SuppressWarnings("unsused")
public class TestCodeParticles {

    static {
        InputStream stream = TestCodeParticles.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Logger LOGGER = Logger.getLogger(TestCodeParticles.class.getName());

    public static void main(String[] args) {

        Logger.getGlobal().setLevel(Level.ALL);

        Ws281xLedStrip strip = new Ws281xLedStrip(
                PixelSettings.NUM_PIXELS,
                PixelSettings.LED_PIN,
                PixelSettings.LED_FREQ,
                PixelSettings.LED_DMA,
                PixelSettings.LED_BRIGHTNESS,
                PixelSettings.LED_CHANNEL,
                false,
                LedStripType.WS2811_STRIP_GRB,
                false
        );

        HWStrip hwStrip = new HWStrip(strip);

        ParticleManager manager = new ParticleManager(hwStrip);

        manager.registerEmitter(new Emitter(Particle.class, 750));

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        while (true) {
            manager.tick();
            // scanner.nextLine();
        }

    }

}
