// Concrete Mediator (Control Tower)
import java.util.ArrayList;
import java.util.List;

class ControlTowerMediator implements Mediator {
    private List<Component> components = new ArrayList<>();

    public void register(Component component) {
        components.add(component);
    }

    @Override
    public void requestLanding(Component component) {
        System.out.println(component.getName() + " requesting landing...");

        for (Component c : components) {
            if (c != component && c.isLanding()) {
                System.out.println("Runway busy. " + component.getName() + " wait.");
                return;
            }
        }

        System.out.println("Landing approved for " + component.getName());
        component.setLanding(true);
        notify(component, "landing");
    }

    @Override
    public void notify(Component sender, String event) {
        for (Component c : components) {
            if (c != sender) {
                c.receive(sender.getName() + " is " + event);
            }
        }
    }
}

