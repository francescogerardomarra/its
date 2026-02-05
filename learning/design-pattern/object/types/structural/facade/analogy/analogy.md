# Real-world analogy: car start simulation
**Let’s say that we want to start a car.**

It can be quite complex and does require some effort to start the engine correctly:
```
airFlowController.takeAir();
fuelInjector.on();
fuelInjector.inject();
starter.start();
coolingController.setTemperatureUpperLimit(DEFAULT_COOLING_TEMP);
coolingController.run();
catalyticConverter.on();
```
Similarly, stopping the engine also requires quite a few steps:
```
fuelInjector.off();
catalyticConverter.off();
coolingController.cool(MAX_ALLOWED_TEMP);
coolingController.stop();
airFlowController.off();
```
A facade is just what we need here. **We’ll hide all the complexity in two methods: `startEngine()` and `stopEngine()`.**

**Let’s see how we can implement it:**
```java
// Facade class to simplify starting and stopping the engine
public class CarEngineFacade {
    private static final int DEFAULT_COOLING_TEMP = 90;
    private static final int MAX_ALLOWED_TEMP = 50;

    private final FuelInjector fuelInjector;
    private final AirFlowController airFlowController;
    private final Starter starter;
    private final CoolingController coolingController;
    private final CatalyticConverter catalyticConverter;

    public CarEngineFacade() {
        this.fuelInjector = new FuelInjector();
        this.airFlowController = new AirFlowController();
        this.starter = new Starter();
        this.coolingController = new CoolingController();
        this.catalyticConverter = new CatalyticConverter();
    }

    public void startEngine() {
        System.out.println("\n🚗 Starting engine...");
        fuelInjector.on();
        airFlowController.takeAir();
        fuelInjector.inject();
        starter.start();
        coolingController.setTemperatureUpperLimit(DEFAULT_COOLING_TEMP);
        coolingController.run();
        catalyticConverter.on();
        System.out.println("✅ Engine started successfully!\n");
    }

    public void stopEngine() {
        System.out.println("\n🛑 Stopping engine...");
        fuelInjector.off();
        catalyticConverter.off();
        coolingController.cool(MAX_ALLOWED_TEMP);
        coolingController.stop();
        airFlowController.off();
        System.out.println("✅ Engine stopped successfully!\n");
    }
}
```
Now, to start and stop a car, we need only 2 lines of code, instead of 13:

```java
public static void main(String[] args) {
    CarEngineFacade facade = new CarEngineFacade();
    
    facade.startEngine();  // Start the car with one call
    // ... car is running ...
    facade.stopEngine();   // Stop the car with one call
}
```

Here is an example that shows the difference side-to-side:
```java
public class CarEngineFacadeDemo {
    public static void main(String[] args) {
        // Without Facade - Complex and error-prone
        System.out.println("🚗 Starting engine without Facade...");
        AirFlowController airFlowController = new AirFlowController();
        FuelInjector fuelInjector = new FuelInjector();
        Starter starter = new Starter();
        CoolingController coolingController = new CoolingController();
        CatalyticConverter catalyticConverter = new CatalyticConverter();
        
        airFlowController.takeAir();
        fuelInjector.on();
        fuelInjector.inject();
        starter.start();
        coolingController.setTemperatureUpperLimit(90);
        coolingController.run();
        catalyticConverter.on();
        System.out.println("✅ Engine started successfully (without Facade)!\n");

        // Without Facade - Stopping the engine is also complex
        System.out.println("🛑 Stopping engine without Facade...");
        fuelInjector.off();
        catalyticConverter.off();
        coolingController.cool(50);
        coolingController.stop();
        airFlowController.off();
        System.out.println("✅ Engine stopped successfully (without Facade)!\n");

        // With Facade - Simplified and clean
        System.out.println("🚗 Starting engine with Facade...");
        CarEngineFacade facade = new CarEngineFacade();
        facade.startEngine();  
        
        // ... car is running ...
        
        System.out.println("🛑 Stopping engine with Facade...");
        facade.stopEngine();
    }
}
```
