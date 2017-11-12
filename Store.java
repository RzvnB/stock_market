import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Store {
    private List<Resource> offers;
    private List<Resource> demands;
    private List<Resource> transactions;
    private volatile List<Resource> immutableOffers;
    private volatile List<Resource> immutableDemands;
    private volatile List<Resource> immutableTransactions;
    private int counter;

    private static final String DEMMAND = "demmand";
    private static final String OFFER = "offer";

    private String offersLocation, demandsLocation, transactionsLocation;

    public Store(String offersLocation, String demandsLocation, String transactionsLocation) {
        //add to config xml
        this.offersLocation = offersLocation;
        this.demandsLocation = demandsLocation;
        this.transactionsLocation = transactionsLocation;
        this.init();
        this.counter = 0;
        this.immutableOffers = Collections.unmodifiableList(this.offers);
        this.immutableDemands = Collections.unmodifiableList(this.demands);
        this.immutableTransactions = Collections.unmodifiableList(this.transactions);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() { 
                save();
            }
        });
    }

    public void init() {
        this.offers = Collections.synchronizedList(new ArrayList<Resource>());
        this.demands = Collections.synchronizedList(new ArrayList<Resource>());
        this.transactions = Collections.synchronizedList(new ArrayList<Resource>());
        load(offersLocation);
        load(demandsLocation);
        load(transactionsLocation);
    }

    public void load(String location) {
        //parse file for resource
        try (BufferedReader br = new BufferedReader(new FileReader(location))) {
            String line;
            while ((line = br.readLine()) != null) {
                Resource res = parseString(line);
                if(res.getResourceType() == DEMMAND)
                    demands.add(res);
                else if(res.getResourceType() == OFFER) 
                    offers.add(res);
                else 
                    transactions.add(res);
            }
        } catch(IOException ie) {
            ie.printStackTrace();
            System.exit(-1);
        }
    }

    private Resource parseString(String resourceString) {
        String[] values = resourceString.split(" ");
        return new Resource(values[0],Integer.valueOf(values[1]),Integer.valueOf(values[2]),values[3],values[4]);
    }

    public void save() {
        save_resource(offersLocation,offers);
        save_resource(demandsLocation,demands);
        save_resource(transactionsLocation,transactions);         
    }

    public void save_resource(String location, List<Resource> resource) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(location), "utf-8"))) {
                for(Resource res : resource) {
                    writer.write(res.toString());
                }
        } catch(IOException ie) {
            ie.printStackTrace();
            System.exit(-1);
        }
    }

    public List<Resource> getOffers() {
        return this.immutableOffers;
    }

    public List<Resource> getDemands() {
        return this.immutableDemands;
    }

    public List<Resource> getTransactions() {
        return this.immutableTransactions;
    }
    
    public void removeDemand(Resource demand) {
        this.demands.remove(demand);
    }

    public void removeOffer(Resource offer) {
        this.offers.remove(offer);
    }

    public void addDemand(Resource demand) {
        this.demands.add(demand);
    }

    public void addOffer(Resource offer) {
        this.offers.add(offer);
    }

    public void addTransaction(Resource transaction) {
        this.transactions.add(transaction);
    }

    public synchronized void updateViews() {
        this.immutableDemands = Collections.unmodifiableList(this.demands);
        this.immutableOffers = Collections.unmodifiableList(this.offers);
        this.immutableTransactions = Collections.unmodifiableList(this.transactions);        
    }
 
}