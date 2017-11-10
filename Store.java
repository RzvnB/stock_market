import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


public class Store {
    private ArrayList<Resource> offers;
    private ArrayList<Resource> demands;
    private ArrayList<Resource> transactions;
    private ArrayList<String> testStrings;
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
        this.testStrings = new ArrayList<String>();
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

    public void save_resource(String location,ArrayList<Resource> resource) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(location), "utf-8"))) {
                for(Resource res : resource) {
                    writer.write(res.toString());
                    System.out.println(res);
                }
        } catch(IOException ie) {
            ie.printStackTrace();
            System.exit(-1);
        }
    }

    public int getCounter() {
        int x = this.counter;
        this.counter++;
        return x;
    }
    
    public String getTestString() {
        String res = "";
        for (String s: testStrings) {
            res += s + "\n";
        }
        return res;
    }

    public void addTestString() {
        testStrings.add("abcd");
    }

    public static void main(String args[]) {
        Store s = new Store("offers.txt","demands.txt","transactions.txt");

    }
    
}