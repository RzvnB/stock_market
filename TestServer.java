import java.util.Scanner;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import java.io.InputStream;
import java.io.DataOutputStream;

enum URLS {
	DEMANDS_URL("http://localhost:9000/demand"),
	TRANSACTIONS_URL("http://localhost:9000/transactions"),
	OFFER_URL("http://localhost:9000/offer");

	private final String url;

	URLS(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}
}

public class TestServer implements Runnable {

	private URLS url;
	private static final Random random = new Random();
	private static final String[] companies = { "Google", "Amazon", "Ebay" };
	private static final String[] names = { "Ramy", "Rares", "Razvan", "Bogo" };

	public TestServer(URLS url) {
		this.url = url;
	}

	public static void main(String[] args) {

		URLS[] urls = URLS.values();
		int rand;

		Random random = new Random();
		rand = random.nextInt(urls.length);
		for (int i = 0; i < 100; i++) {
			rand = random.nextInt(urls.length);
			(new Thread(new TestServer(urls[rand]))).start();
		}

	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println("requested: " + this.url + " thread id: " + Thread.currentThread().getId());
			if (this.url == URLS.TRANSACTIONS_URL) {
				System.out.println("GET");
				getRequest(this.url.getURL());
			}

			else {
				System.out.println("POST");
				postRequest(this.url.getURL(),
						"name=" + companies[random.nextInt(companies.length)] + "\nvalue=" + random.nextInt(100)
								+ "\nquantity=" + random.nextInt(100) + "\ninfo="
								+ names[random.nextInt(names.length)]);
				getRequest(this.url.getURL());
			}

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

	public static String postRequest(String resource_url, String urlParameters) {
		try {
			URL url = new URL(resource_url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");
			connection.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// Java version 5+
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

}