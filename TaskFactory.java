import java.util.concurrent.Callable;

public class TaskFactory {

    public static Callable getTask(String task, ResourceDAO resources) {
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

    public static Callable getTask(String task, ResourceDAO resources, String param) {
        if(task.equals("putOffer")) {
            return new PostOfferTask(resources, param);
        }
        if (task.equals("putDemand")) {
            return new PostDemandTask(resources, param);
        }
        throw new RuntimeException("Task not found");
    }
}