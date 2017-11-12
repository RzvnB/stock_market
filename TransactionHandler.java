import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;




public class TransactionHandler implements HttpHandler {
    private ExecutorService taskQueue;
    private ResourceDAO resources;
    private final String MESSAGE_404 = "Resource not found";
    private final String MESSAGE_500 = "Execution error";

    public TransactionHandler(ExecutorService taskQueue, Store store) {
        this.taskQueue = taskQueue;
        this.resources = new ResourceDAO(store);
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        OutputStream os = he.getResponseBody();
        Callable task;
        
        try {
            if(he.getRequestMethod().equals("GET")) {
                task = TaskFactory.getTask("getTransactions", this.resources); 
            } else {
                he.sendResponseHeaders(404, MESSAGE_404.length());
                os.write(MESSAGE_404.getBytes());
                os.close();
                return;
            }
            task.setDAO(this.resources);
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