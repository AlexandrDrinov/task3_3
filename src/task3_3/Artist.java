package task3_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Artist {

    private final long DRAW_SLEEP = 3000;
    private final long ERASE_SLEEP = 150;

    private boolean drawOn = false;
    private final Pencil pencil;
    private final Eraser eraser;
    private ExecutorService es;

    public Artist(Pencil pencil, Eraser eraser) {
        this.pencil = pencil;
        this.eraser = eraser;
    }

    public void workMiracles(long workTime) {
        es = Executors.newCachedThreadPool();
        es.execute(new EraseThread());
        es.execute(new DrawThread());
        System.out.println("The artist began to work miracles. After " + workTime / 1000 + " seconds you are ready");

        try {
            Thread.sleep(workTime);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        workWondersEnd();
    }

    private void workWondersEnd() {
        es.shutdownNow();
        System.out.println("              ___.-~\"~-._   __....__");
        System.out.println("            .'    `    \\ ~\\\"~        ``-.");
        System.out.println("           /` _      )  `\\              `\\");
        System.out.println("          /`  a)    /     |               `\\");
        System.out.println("         :`        /      |                 \\");
        System.out.println("    <`-._|`  .-.  (      /   .            `;\\\\");
        System.out.println("     `-. `--'_.'-.;\\___/'   .      .       | \\\\");
        System.out.println("  _     /:--`     |        /     /        .'  \\\\");
        System.out.println(" (\"\\   /`/        |       '     '         /    :`;");
        System.out.println(" `\\'\\_/`/         .\\     /`~`=-.:        /     ``");
        System.out.println("   `._.'          /`\\    |      `\\      /(");
        System.out.println("                 /  /\\   |        `Y   /  \\");
        System.out.println("                /  /  Y  |         |  /`\\  \\");
        System.out.println("               /  |   |  |         |  |  |  |");
        System.out.println("            \\\"---\\\"  /___|        /___|  /__|");
        System.out.println("                     '\"\"\"         '\"\"\"  '\"\"\"");
        System.out.println();
        System.out.println("The artist completed the work. A surprising result, is not it?");
    }

    private void draw() {
        synchronized (this) {
            pencil.draw();
            drawOn = true;
            notifyAll();
        }
    }

    private synchronized void waitForDraw() throws InterruptedException {
        while (drawOn == false) {
            wait();
        }
    }

    private void erase() {
        synchronized (this) {
            eraser.erase();
            drawOn = false;
            notifyAll();
        }
    }

    private synchronized void waitForErase() throws InterruptedException {
        while (drawOn == true) {
            wait();
        }
    }

    private class DrawThread implements Runnable {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(DRAW_SLEEP);
                    draw();
                    waitForErase();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class EraseThread implements Runnable {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    waitForDraw();
                    Thread.sleep(ERASE_SLEEP);
                    erase();
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
