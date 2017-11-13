import com.sun.net.httpserver.HttpServer;
import java.util.concurrent.Callable;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import src.handlers.ODHandler;
import src.handlers.TransactionHandler;
import src.store.Store;
import src.tasks.MatchTask;

public class StockMarketServer{

    public static void main(String [] args) throws Exception {
        Store store = new Store("offers.txt", "demands.txt", "transactions.txt");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20);
        MatchTask matchTask = new MatchTask(store);
        scheduler.scheduleAtFixedRate(new MatchTask(store), 1, 50, TimeUnit.MILLISECONDS);
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