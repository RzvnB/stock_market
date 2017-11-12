package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class PostDemandTask implements Callable<String> {

    private ResourceDAO resources;
    private String demand;
    
        public PostDemandTask(ResourceDAO resources, String demand) {
            this.resources = resources;
            this.demand = demand;
        }
    
        @Override
        public String call() throws Exception {
            resources.addDemand(demand);
            return "DONE";
        }
}