import java.util.concurrent.Callable;


public class DemandsHistoryTask implements Callable {
	    
	public DemandsHistoryTask() {}

	@Override
	public String call() throws Exception {
		return "DemandsHistory";
	}
}