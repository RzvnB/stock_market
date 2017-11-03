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
// import CompletionServiceProvider;
// import DemandsHistoryTask;
// import TransactionHistoryTask;
import java.util.concurrent.Future;



public class StockMarketServer{
//public StockMarketServer
//post(nume actiune, numar actiuni, pret per actiune) -> sell

    
    public static void main(String [] args) throws Exception {
        CompletionService compService = CompletionServiceProvider.getCompletionservice();
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/", new RootHandler(compService));
        server.createContext("/echoHeader", new EchoHeaderHandler(compService));
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
    }

}

class RootHandler implements HttpHandler {

    private CompletionService cs;

    public RootHandler(CompletionService cs) {
        this.cs = cs;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        DemandsHistoryTask task = new DemandsHistoryTask();
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing Root");
            Future future = cs.submit(task);
            String result = (String) future.get();
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

    public EchoHeaderHandler(CompletionService cs) {
        this.cs = cs;
    }


    @Override
    public void handle(HttpExchange he) throws IOException {
        TransactionHistoryTask task = new TransactionHistoryTask();
        OutputStream os = he.getResponseBody();
        try {
            System.out.println("Executing ECHOHeader");
            Future future = cs.submit(task);
            String result = (String) future.get();
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



