import java.util.Scanner;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;

public class Client {
	public static final String DEMANDS_URL = "http://192.168.0.101:9000/demands";
	public static final String TRANSACTIONS_URL = "http://192.168.0.101:9000/transactions";

	public static final String[] URLS = {DEMANDS_URL, TRANSACTIONS_URL};

	public static void main(String args[]){
		int userChoice;
		Client client =  new Client();

        while((userChoice = menu()) != 4) {
            client.getRequest(URLS[userChoice-1]);
        }

        if(userChoice == 4) {
            System.exit(1);
        }
	}

	public void getRequest(String resource_url) {
		try {
			URL url = new URL(resource_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}

			System.out.println(content);

			in.close();
			con.disconnect();
		} catch (MalformedURLException mex) {
			mex.printStackTrace();	
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}


	public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Welcome to the dankest stock market in the galaxy. May the choice be with you!");
        System.out.println("-------------------------\n");
        System.out.println("1 - Show all demands");
        System.out.println("2 - Show all transactions");
        System.out.println("3 - Show all offers");
        System.out.println("4 - Quit");

        selection = input.nextInt();
        return selection;    
    }
}