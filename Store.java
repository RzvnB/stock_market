import java.io.IOException;
import java.util.ArrayList;


public class Store {
    private ArrayList<Resource> offers;
    private ArrayList<Resource> demands;
    private ArrayList<Resource> transactions;

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
        offers = new ArrayList<Resource>();
        demands = new ArrayList<Resource>();
        transactions = new ArrayList<Resource>();
        load(offersLocation);
        load(demandsLocation);
        load(transactionsLocation);
    }

    public void load(String location) {
        //parse file for resource
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
        return new Resource(values[0],Integer.valueOf(value[1]),Integer.valueOf(value[2]),value[3],value[4]);
    }

    public void save() {
        //cod de 
        
    }

    
    
}