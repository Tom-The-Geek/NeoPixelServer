package me.geek.tom.remoteneoparticle.test;

import me.geek.tom.remoteneoparticle.particlesystemext.RemoteStrip;

public class TestClient {

    public static void main(String[] args) {
        new TestClient().main();
    }

    private void main() {
        RemoteStrip strip = new RemoteStrip(16);

        strip.setPixel(1, 255, 255, 255);

        strip.show();
    }
}
