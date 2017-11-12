package src.tasks;

import java.lang.Runnable;
import java.util.List;
import src.store.ResourceDAO;
import src.store.Store;
import src.store.Resource;

public class MatchTask implements Runnable {
    private Store store;
    private ResourceDAO resources;

    public MatchTask(Store store) {
        this.resources = new ResourceDAO(store);
        this.store = store;
    }

    @Override
    public void run() {
        // System.out.println(">>> Trying to match offer with demand!");
        List<Resource> offers = this.store.getOffers();
        List<Resource> demands = this.store.getDemands();
        for(Resource offer: offers) {
            for(Resource demand: demands) {
                if (offer.getStockValue() == demand.getStockValue() && 
                    offer.getStockName().equals(demand.getStockName()) &&
                    !offer.getOwner().equals(demand.getOwner()) ) {
                        System.out.println(">>> Found match!");
                        this.resources.updateStore(demand, offer);
                        return;
                }
            }
        }
    }
}