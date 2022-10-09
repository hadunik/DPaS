import java.util.Timer;
import java.util.TimerTask;


public class Main {
    public static void main(String[] argc) {
        ChildThread ct = new ChildThread();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ct.start();
            }
        }, 0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ct.interrupt();
                timer.cancel();
                System.out.println("Timer thread finished...");
            }
        }, 2000);
    }
}
