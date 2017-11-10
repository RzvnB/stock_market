import java.util.concurrent.Callable;


public class DemandsHistoryTask implements Callable {
	
	private ResourceDAO resources;
	
	public DemandsHistoryTask(ResourceDAO resources) {
		this.resources = resources;
	}

	@Override
	public String call() throws Exception {
		return resources.getTestString();
	}
}