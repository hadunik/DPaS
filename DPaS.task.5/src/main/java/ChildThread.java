public class ChildThread extends Thread {
    @Override
    public void run() {
        int count = 0;
        while (!isInterrupted()) {
            System.out.println("Loop " + count++);
        }
        System.out.printf("%s finished...\n", Thread.currentThread().getName());
    }
}
