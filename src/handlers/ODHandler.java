package src.handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import src.store.ResourceDAO;
import src.tasks.TaskFactory;
import src.store.Store;



public class ODHandler implements HttpHandler {
    private ExecutorService taskQueue;
    private ResourceDAO resources;
    private TaskFactory taskFactory;
    private final String MESSAGE_404 = "Resource not found";
    private final String MESSAGE_500 = "Execution error";

    public ODHandler(ExecutorService taskQueue, Store store) {
        this.taskQueue = taskQueue;
        this.resources = new ResourceDAO(store);
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        OutputStream os = he.getResponseBody();
        Callable<String> task;
        String getTaskName;
        String putTaskName;
        System.out.println("ODHandler handling stuff");

        if (he.getHttpContext().getPath().equals("/demand")) {
            getTaskName = "getDemands";
            putTaskName = "putDemand";
        } else {
            getTaskName = "getOffers";
            putTaskName = "putOffer";
        }

        try {
            if(he.getRequestMethod().equals("GET")) {
                task = TaskFactory.getTask(getTaskName, this.resources);
            } else if (he.getRequestMethod().equals("POST")) {
                String param = "";
                String str = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(he.getRequestBody()));
                while ((str = reader.readLine()) != null) {
                    System.out.println(str);
                    param += str + "=";
                }
                task = TaskFactory.getTask(putTaskName, this.resources, param);
            } else {
                he.sendResponseHeaders(404, MESSAGE_404.length());
                os.write(MESSAGE_404.getBytes());
                os.close();
                return;
            }
            Future future = taskQueue.submit(task);
            String result = (String) future.get();
            he.sendResponseHeaders(200, result.length());
            os.write(result.getBytes());
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            he.sendResponseHeaders(500, MESSAGE_500.length());
            os.write("ERROR Interrupted".getBytes());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            he.sendResponseHeaders(500, MESSAGE_500.length());
            os.write(MESSAGE_500.getBytes());
        } finally {
            os.close();
        }
    }
}