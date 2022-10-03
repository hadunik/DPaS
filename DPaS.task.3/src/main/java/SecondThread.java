import java.util.ArrayList;
import java.util.List;

public class SecondThread extends Thread {
    String num;
    SecondThread(String i){
        num = i;
    }
    @Override
    public void run() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("String from " + num + ": " + i);
        }
        printData(list);

    }

    static void printData(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
