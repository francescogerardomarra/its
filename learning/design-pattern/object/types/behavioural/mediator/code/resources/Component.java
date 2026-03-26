// Component Base Class
abstract class Component {
    protected Mediator mediator;
    protected String name;
    protected boolean landing;

    public Component(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
        this.landing = false;
    }

    public String getName() {
        return name;
    }

    public boolean isLanding() {
        return landing;
    }

    public void setLanding(boolean landing) {
        this.landing = landing;
    }

    public abstract void receive(String message);
}

