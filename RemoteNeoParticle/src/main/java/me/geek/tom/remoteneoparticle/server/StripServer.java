package me.geek.tom.remoteneoparticle.server;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import me.geek.tom.NeoPixelRemoteServer.PixelSettings;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.HWStrip;
import me.geek.tom.NeoPixelRemoteServer.particlesystem.strip.IStrip;

@SuppressWarnings("StatementWithEmptyBody")
public class StripServer {
    public static void main(String[] args) {
        new StripServer().run();
    }

    private void run() {
        Ws281xLedStrip s = new Ws281xLedStrip(
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
        IStrip strip = new HWStrip(s);

        ConnectedStrip conStrip = new ConnectedStrip(strip);

        while (true) { }
    }
}
