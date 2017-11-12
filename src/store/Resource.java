package src.store;

public class Resource {
    private String stockName;
    private int stockValue;
    private int stockQuantity;
    private String specificInfo;
    private String resourceType;

    public Resource(String stockName, int stockValue, int stockQuantity, String specificInfo, String resourceType) {
        this.stockName=stockName;
        this.stockValue=stockValue;
        this.stockQuantity=stockQuantity;
        this.specificInfo=specificInfo;
        this.resourceType=resourceType;
    }

    public String getStockName() {
        return stockName;
    }

    public int getStockValue() {
        return stockValue;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getOwner() {
        return specificInfo;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String toString() {
        return stockName + " " + stockValue + " " + stockQuantity + " " + specificInfo + " " + resourceType;
    }
}