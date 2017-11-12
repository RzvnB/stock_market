import java.util.concurrent.Callable;

public class GetOfferTask implements Callable {

    private ResourceDAO resources;
    
        public GetOfferTask(ResourceDAO resources) {
            this.resources = resources;
        }
    
        @Override
        public String call() throws Exception {
            String res = resources.getOffers();
            return res;
        }
}