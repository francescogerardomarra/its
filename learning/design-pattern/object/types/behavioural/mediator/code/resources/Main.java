// Client Code
public class MediatorPatternDemo {
    public static void main(String[] args) {
        ControlTowerMediator mediator = new ControlTowerMediator();

        Airplane a1 = new Airplane(mediator, "Flight A");
        Airplane a2 = new Airplane(mediator, "Flight B");
        Airplane a3 = new Airplane(mediator, "Flight C");

        mediator.register(a1);
        mediator.register(a2);
        mediator.register(a3);

        a1.requestLanding();
        a2.requestLanding();
        a3.requestLanding();
    }
}
