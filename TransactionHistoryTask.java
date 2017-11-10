import java.util.concurrent.Callable;

public class TransactionHistoryTask implements Callable {
	
	private ResourceDAO resources;

	public TransactionHistoryTask(ResourceDAO resources) {
		this.resources = resources;
	}

	@Override
	public String call() throws Exception {
		resources.addTestString();
		return "DONE";
	}
}
