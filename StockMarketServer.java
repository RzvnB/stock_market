import java.lang.Object;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.Scanner;



public class StockMarketServer{

    public static void main(String [] args) throws Exception {
        ResourceDAO resources = ResourceDAO.getInstance();
        CompletionService compService = CompletionServiceProvider.getCompletionservice();
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        // server.createContext("/demands", new RootHandler(compService, resources));
        server.createContext("/addTestString", new TestGetHandler(compService, resources));
        server.createContext("/getTestString", new TestAddHandler(compService, resources));
        // server.createContext("/transactions", new EchoHeaderHandler(compService, resources));
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
    }        

}

class TestGetHandler implements HttpHandler {
    private CompletionService cs;
    private ResourceDAO resources;

    public TestGetHandler(CompletionService cs, ResourceDAO resources) {
        this.cs = cs;
        this.resources = resources;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        TransactionHistoryTask task = new TransactionHistoryTask(this.resources);
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing getTestString");
            Future future = cs.submit(task);
            String result = (String) future.get();
            he.sendResponseHeaders(200, result.length());
            os.write(result.getBytes());
            System.out.println("Done getTestString");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            os.write("ERROR Interrupted".getBytes());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            os.write("ERROR Execution".getBytes());
        } finally {
            os.close();
        }
    }
}

class TestAddHandler implements HttpHandler {
    private CompletionService cs;
    private ResourceDAO resources;

    public TestAddHandler(CompletionService cs, ResourceDAO resources) {
        this.cs = cs;
        this.resources = resources;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        DemandsHistoryTask task = new DemandsHistoryTask(this.resources);
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing getTestString");
            Future future = cs.submit(task);
            String result = (String) future.get();
            he.sendResponseHeaders(200, result.length());
            os.write(result.getBytes());
            System.out.println("Done getTestString");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            os.write("ERROR Interrupted".getBytes());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            os.write("ERROR Execution".getBytes());
        } finally {
            os.close();
        }
    }
}



class RootHandler implements HttpHandler {

    private CompletionService cs;
    private ResourceDAO resources;

    public RootHandler(CompletionService cs, ResourceDAO resources) {
        this.cs = cs;
        this.resources = resources;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        DemandsHistoryTask task = new DemandsHistoryTask(this.resources);
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing Root");
            Future future = cs.submit(task);
            String result = ((Integer) future.get()).toString();
            he.sendResponseHeaders(200, result.length());
            os.write(result.getBytes());
            System.out.println("Done writing root");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            os.write("ERROR Interrupted".getBytes());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            os.write("ERROR Execution".getBytes());
        } finally {
            os.close();
        }
    }
}

class EchoHeaderHandler implements HttpHandler {

    private CompletionService cs;
    private ResourceDAO resources;

    public EchoHeaderHandler(CompletionService cs, ResourceDAO resources) {
        this.cs = cs;
        this.resources = resources;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        TransactionHistoryTask task = new TransactionHistoryTask(this.resources);
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing ECHOHeader");
            Future future = cs.submit(task);
            String result = ((Integer) future.get()).toString();
            he.sendResponseHeaders(200, result.length());
            os.write(result.getBytes());
            System.out.println("Done writing echoheader");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            os.write("ERROR Interruption".getBytes());
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            os.write("ERROR Execution".getBytes());
        } finally {
            os.close();
        }
    }
}



