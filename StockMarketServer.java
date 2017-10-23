import java.lang.Object;

public class StockMarketServer{
	//public StockMarketServer
	//post(nume actiune, numar actiuni, pret per actiune) -> sell

	public static void main(String [] args){
		int port = 9000;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		System.out.println("server started at " + port);
		server.createContext("/", new RootHandler());
		server.createContext("/echoHeader", new EchoHeaderHandler());
		server.setExecutor(null);
		server.start();
	}

}

class RootHandler implements HttpHandler {

         @Override

         public void handle(HttpExchange he) throws IOException {
                 String response = "<h1>Server start success 
                 if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
                 he.sendResponseHeaders(200, response.length());
                 OutputStream os = he.getResponseBody();
                 os.write(response.getBytes());
                 os.close();
         }
}


class EchoHeaderHandler implements HttpHandler {

         @Override
         public void handle(HttpExchange he) throws IOException {
                 Headers headers = he.getRequestHeaders();
                 Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
                 String response = "";
                 for (Map.Entry<String, List<String>> entry : entries)
                          response += entry.toString() + "\n";
                 he.sendResponseHeaders(200, response.length());
                 OutputStream os = he.getResponseBody();
                 os.write(response.toString().getBytes());
                 os.close();
         }}



