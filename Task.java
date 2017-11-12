public interface Task extends Callable {
    void setDAO(ResourceDAO resource);
}