import java.util.concurrent.Callable;

public class PostOfferTask implements Callable {

    private ResourceDAO resources;
    private String offer;
    
        public PostOfferTask(ResourceDAO resources, String offer) {
            this.resources = resources;
            this.offer = offer;
        }
    
        @Override
        public String call() throws Exception {
            String res = resources.addOffer(offer);
            return "DONE";
        }
}