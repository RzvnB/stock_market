import java.util.concurrent.Callable;

public class GetTransactionTask implements Callable {

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