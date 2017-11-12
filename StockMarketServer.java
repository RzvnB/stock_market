import java.lang.Object;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class StockMarketServer{

    public static void main(String [] args) throws Exception {
        Store store = new Store("offers.txt", "demands.txt", "transactions.txt");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        MatchTask matchTask = new MatchTask(store);
        scheduler.scheduleAtFixedRate(matchTask, 1, 3, TimeUnit.SECONDS);
        ExecutorService taskQueue = Executors.newCachedThreadPool();
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/offer", new ODHandler(taskQueue, store));
        server.createContext("/demand", new ODHandler(taskQueue, store));
        server.createContext("/transactions", new TransactionHandler(taskQueue, store));
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
    }        

}