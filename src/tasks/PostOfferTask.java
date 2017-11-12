package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class PostOfferTask implements Callable<String> {

    private ResourceDAO resources;
    private String offer;
    
        public PostOfferTask(ResourceDAO resources, String offer) {
            this.resources = resources;
            this.offer = offer;
        }
    
        @Override
        public String call() throws Exception {
            resources.addOffer(offer);
            return "DONE";
        }
}