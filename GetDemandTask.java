import java.util.concurrent.Callable;

public class GetDemandTask implements Callable {

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