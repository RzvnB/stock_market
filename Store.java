import java.util.ArrayList;

public class Store {
    private ArrayList<Offer> offers;
    private ArrayList<Demand> demands;
    private ArrayList<Transaction> transactions;

    private String offersLocation, demandsLocation, transactionsLocation;

    public Store(String offersLocation, String demandsLocation, String transactionsLocation) {
        this.offersLocation = offersLocation;
        this.demandsLocation = demandsLocation;
        this.transactionsLocation = transactionsLocation;
        this.init();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() { 
                save();
            }
        });
    }

    public void init() {
        offers = load(offersLocation);
        demands = load(demandsLocation);
        transactions = load(transactionsLocation);
    }

    public ArrayList<Resource> load(String location) {
        //parse file for resource;
        return null;
    }

    public void save() {
        //cod de scris
    }

    
    
}