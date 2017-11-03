import com.oracle.json;

public abstract class JsonCreator {

    private Resource resource;

    private static final String RESOURCE_TYPE = "resource_type";
    private static final String STOCK_NAME = "stock_name";
    private static final String STOCK_VALUE = "stock_value";
    private static final String STOCK_QUANTITY = "stock_quantity";
    private static final String SPECIFIC_INFO = "specific_info";

    private JsonCreator(Resource resource) {
        this.resource=resource;
    }

    public JsonObject getJson() {
        return  Json.createObjectBuilder()
                    .add(RESOURCE_TYPE,resource.getResourceType())
                    .add(STOCK_NAME,resource.getStockName())
                    .add(STOCK_VALUE,String.valueOf(resource.getStockValue()))
                    .add(STOCK_QUANTITY,String.valueOf(resource.getStockQuantity()))
                    .add(SPECIFIC_INFO,resource.getSpecificInfo())
                    .build();
    }
}