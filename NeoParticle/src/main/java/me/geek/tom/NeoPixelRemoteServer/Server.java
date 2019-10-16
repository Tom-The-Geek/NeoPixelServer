package me.geek.tom.NeoPixelRemoteServer;

import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;

@SuppressWarnings({"unused"})
public class Server {

    public static void main(String[] args) {

        System.out.println("LOADING...");

        Ws281xLedStrip neopixels = new Ws281xLedStrip(
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
        for (int i = 0; i < PixelSettings.LED_BRIGHTNESS; i++) {

            neopixels.setPixel(i - 1, 0, 0, 0);
            neopixels.setPixel(i, 255, 0, 0);
            neopixels.render();
            /*
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             */
        }

        neopixels.setStrip(0,0,0);
        neopixels.render();
    }
}
