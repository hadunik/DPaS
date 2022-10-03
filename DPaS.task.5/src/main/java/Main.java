public class Main {
    public static void main(String[] argc) {
        ChildThread ct = new ChildThread();
        ct.start();
        try {
            Thread.sleep(2000);
            ct.interrupt();
        } catch (InterruptedException ignored) {
        }
        System.out.println("Main thread finished...");
    }
}
