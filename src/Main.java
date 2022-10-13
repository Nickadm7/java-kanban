import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import elements.Task;
import elements.utilenum.Status;
import management.http.HttpTaskManager;
import management.http.HttpTaskServer;
import management.http.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static management.http.TasksToGsonTime.gson;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    public static HttpTaskManager manager;
    public static HttpTaskServer httpTaskServer;

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer server = new KVServer();
        server.start();
        manager = new HttpTaskManager("http://localhost:8078");
    }
}