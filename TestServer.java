import java.util.Scanner;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

enum URLS {
	DEMANDS_URL("http://localhost:9000/demands"),
	TRANSACTIONS_URL("http://localhost:9000/echoHeader");

	private final String url;

	URLS(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}
}

public class TestServer implements Runnable{
	
	private URLS url;
	public TestServer(URLS url){
		this.url = url;
	}
	
	public static void main(String [] args){

	URLS [] urls = URLS.values();
	int rand;

		Random random = new Random();
		rand = random.nextInt(urls.length);
		for(int i = 0; i < 100; i++){
		rand = random.nextInt(urls.length);
		(new Thread(new TestServer(urls[rand]))).start();
		}

	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println("requested: " + this.url + " number: " + i);
			getRequest(this.url.getURL());
		}
	}

	public static void getRequest(String resource_url) {
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

}