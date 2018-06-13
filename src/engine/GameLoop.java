package engine;

import common.Logger.Logger;

public abstract class GameLoop extends Thread {
    private long lastLoopTime = System.nanoTime();
    private long TARGET_FPS = 60;
    private long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private boolean running;
    private long lastFpsTime;
    protected long _fps;
    protected long fps;


    public void run() {
        try {
            this.init();
            long timeToSleep = 0;

            while (running) {
                // work out how long its been since the last update, this
                // will be used to calculate how far the entities should
                // move this loop
                long now = System.nanoTime();
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                double delta = updateLength / ((double) OPTIMAL_TIME);

                // update the frame counter
                lastFpsTime += updateLength;
                _fps++;

                // update our FPS counter if a second has passed since
                // we last recorded
                if (lastFpsTime >= 1000000000) {
                    lastFpsTime = 0;
                    fps = _fps;
                    _fps = 0;
                }

                this.tick(delta);

                // we want each frame to take 10 milliseconds, to do this
                // we've recorded when we started the frame. We add 10 milliseconds
                // to this and then factor in the current time to give
                // us our final value to wait for
                // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
                try {
                    timeToSleep = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                    if (timeToSleep > 0) {
                        Thread.sleep(timeToSleep);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Logger.debug("Game crashed ... well fuck");
            Logger.outputLogToFile(e);
        }
    }

    protected abstract void init();


    public abstract void tick(double deltaT) throws Exception;

    /**
     * Stops the gameloop
     */
    public void stopGameLoop() {
        this.running = false;
    }

    /**
     * Starts the gameloop
     */
    public void startGameLoop(long targetFps) {
        this.running = true;
        TARGET_FPS = targetFps;
        OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        this.start();
    }

}
