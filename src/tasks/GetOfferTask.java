package src.tasks;

import java.util.concurrent.Callable;
import src.store.ResourceDAO;

public class GetOfferTask implements Callable<String> {

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