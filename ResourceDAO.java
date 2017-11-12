import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {
    
    private Store store;

    public ResourceDAO(Store store) { 
        this.store = store;
    }

    public String getOffers() {
        List<Resource> offerList = store.getOffers();
        return processResourceList(offerList);
    }

    public String getDemands() {
        List<Resource> demandList= store.getDemands();
        return processResourceList(demandList);
    }

    public String getTransactions() {
        List<Resource> transactionList = store.getTransactions();
        return processResourceList(transactionList);
    }

    private String processResourceList(List<Resource> resourceList) {
        String resources = "";
        for(Resource resource: resourceList) {
            resources += resource.toString() + "\n";
        }
        return resources;
    }
   
    public void updateStore(Resource demand, Resource offer) {
        int demandQuantity = demand.getStockQuantity();
        int offerQuantity = offer.getStockQuantity();
        int transactionQuantity;
        if (demandQuantity == offerQuantity) {
            transactionQuantity = demandQuantity;
            store.removeOffer(offer);
            store.removeDemand(demand);
        } else if (demandQuantity > offerQuantity) {
            transactionQuantity = offerQuantity;
            Resource newDemand = new Resource(demand.getStockName(), demand.getStockValue(), demandQuantity - offerQuantity,
                                              demand.getOwner(), demand.getResourceType());
            store.removeOffer(offer);
            store.removeDemand(demand);
            store.addDemand(newDemand);
        } else {
            transactionQuantity = demandQuantity;
            Resource newOffer = new Resource(offer.getStockName(), offer.getStockValue(), offerQuantity - demandQuantity,
                                             offer.getOwner(), offer.getResourceType());
            
            store.removeOffer(offer);
            store.removeDemand(demand);
            store.addOffer(newOffer);
        }
        Resource transaction = new Resource(demand.getStockName(), demand.getStockValue(), transactionQuantity, demand.getOwner() + ":" + offer.getOwner(), "transaction");
        store.addTransaction(transaction);
        store.updateViews();
    } 

}