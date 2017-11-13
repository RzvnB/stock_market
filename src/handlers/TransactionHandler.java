package src.handlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import src.store.ResourceDAO;
import src.tasks.TaskFactory;
import src.store.Store;


public class TransactionHandler implements HttpHandler {
    private ExecutorService taskQueue;
    private Store store;
    private final String MESSAGE_404 = "Resource not found";
    private final String MESSAGE_500 = "Execution error";

    public TransactionHandler(ExecutorService taskQueue, Store store) {
        this.taskQueue = taskQueue;
        this.store = store;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        OutputStream os = he.getResponseBody();
        Callable<String> task;
        
        try {
            if(he.getRequestMethod().equals("GET")) {
                task = TaskFactory.getTask("getTransactions", new ResourceDAO(this.store)); 
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