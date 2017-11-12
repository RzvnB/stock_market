import java.lang.Runnable;
import java.util.List;

public class MatchTask implements Runnable {
    private Store store;
    private ResourceDAO resources;

    public MatchTask(Store store) {
        this.resources = new ResourceDAO(store);
        this.store = store;
    }

    @Override
    public void run() {
        List<Resource> offers = this.store.getOffers();
        List<Resource> demands = this.store.getDemands();
        for(Resource offer: offers) {
            for(Resource demand: demands) {
                if (offer.getStockValue() == demand.getStockValue() && 
                    offer.getStockName().equals(demand.getStockName()) &&
                    !offer.getOwner().equals(demand.getOwner()) ) {
                        this.resources.updateStore(demand, offer);
                        return;
                }
            }
        }
    }
}