import java.util.concurrent.Callable;


public class TransactionHistoryTask implements Callable {
	
	
	public TransactionHistoryTask() {}

	@Override
	public String call() throws Exception {
		return "TransactionHistory";
	}
}
