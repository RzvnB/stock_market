import java.util.concurrent.Callable;

public class PostDemandTask implements Callable {

    private ResourceDAO resources;
    private String demand;
    
        public PostDemandTask(ResourceDAO resources, String demand) {
            this.resources = resources;
            this.demand = demand;
        }
    
        @Override
        public String call() throws Exception {
            String res = resources.addDemand(demand);
            return "DONE";
        }
}