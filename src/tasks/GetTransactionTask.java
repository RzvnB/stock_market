package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class GetTransactionTask implements Callable<String> {

    private ResourceDAO resources;
    
        public GetTransactionTask(ResourceDAO resources) {
            this.resources = resources;
        }
    
        @Override
        public String call() throws Exception {
            String res = resources.getTransactions();
            return res;
        }
}