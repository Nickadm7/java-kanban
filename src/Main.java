import management.Managers;
import management.http.KVServer;
import management.utilinterface.TaskManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
            KVServer server = new KVServer();
            server.start();
            TaskManager taskManager = Managers.getDefault();
    }
}