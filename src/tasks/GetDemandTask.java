package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class GetDemandTask implements Callable<String> {

    private ResourceDAO resources;
    
        public GetDemandTask(ResourceDAO resources) {
            this.resources = resources;
        }
    
        @Override
        public String call() throws Exception {
            String res = resources.getDemands();
            return res;
        }
}