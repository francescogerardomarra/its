// Concrete Component (Airplane)
class Airplane extends Component {

    public Airplane(Mediator mediator, String name) {
        super(mediator, name);
    }

    public void requestLanding() {
        mediator.requestLanding(this);
    }

    @Override
    public void receive(String message) {
        System.out.println(name + " received: " + message);
    }
}
