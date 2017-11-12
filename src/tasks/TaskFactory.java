package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class TaskFactory {

    public static Callable<String> getTask(String task, ResourceDAO resources) {
        if (task.equals("getDemands")) {
            return new GetDemandTask(resources);
        }
        if(task.equals("getOffers")) {
            return new GetOfferTask(resources);
        }
        
        if(task.equals("getTransactions")) {
            return new GetTransactionTask(resources);
        }
        throw new RuntimeException("Task not found");
    }

    public static Callable<String> getTask(String task, ResourceDAO resources, String param) {
        if(task.equals("putOffer")) {
            return new PostOfferTask(resources, param);
        }
        if (task.equals("putDemand")) {
            return new PostDemandTask(resources, param);
        }
        throw new RuntimeException("Task not found");
    }
}