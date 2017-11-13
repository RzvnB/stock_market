import java.util.Scanner;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.DataOutputStream;

public class Client {
	public static final String IP = "http://localhost:9000";
	public static final String DEMANDS_URL = IP+"/demand";
	public static final String TRANSACTIONS_URL = IP+"/transactions";
	public static final String OFFERS_URL = IP+"/offer";

	public static final String[] URLS = {DEMANDS_URL, TRANSACTIONS_URL,OFFERS_URL};

	public static void main(String args[]){
		int userChoice;
		Client client =  new Client();
		/*
        while((userChoice = menu()) != 4) {
            client.getRequest(URLS[userChoice-1]);
		}
		*/
		client.postRequest(OFFERS_URL,"name=Google\nvalue=45\nquantity=23\ninfo=georgescu");
		client.getRequest(OFFERS_URL);
		/*
        if(userChoice == 4) {
            System.exit(1);
		}
		*/
	}

	public String getRequest(String resource_url) {
		try {
			URL url = new URL(resource_url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine+'\n');
			}

			System.out.println(content);

			in.close();
			con.disconnect();

			return content.toString();

		} catch (MalformedURLException mex) {
			mex.printStackTrace();	
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return null;

	}


	public String postRequest(String resource_url,String urlParameters) {
		try {
			URL url = new URL(resource_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
			"text/plain;charset=UTF-8");
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream (
				connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
			  response.append(line);
			  response.append('\r');
			}
			rd.close();
			System.out.println(response.toString());

			return response.toString();

		} catch (MalformedURLException mex) {
			mex.printStackTrace();	
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return null;
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