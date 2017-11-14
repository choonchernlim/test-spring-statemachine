# test-spring-statemachine

Examples on configuring Spring State Machine and how to test the state machine.

## Default State Machine Factory

In this example, `MailingStateMachineConfig` where all states and events are configured in that configuration file.

* How to Run:
    * `mvn clean compile spring-boot:run`.

* Pros:
    * For simple state machine, it's simple to configure.
    
* Cons:
    * For complex state machine, the states/events/actions configuration can get very tedious.
    * Difficult to get "full picture" of the state machine containing many states and pseudo-states.

## UML-Based State Machine Factory

In this example, `MailingUmlStateMachineConfig` is used where all states and events are configured in the UML file 
using Eclipse Papyrus.

* How to Run:
    * `mvn clean compile spring-boot:run -Drun.profiles=uml` 

* Pros:
    * The UML file becomes a living documentation of the state machine.
    * Easy to understand the entire state machine through UML visualization.
    
* Cons:
    * Learning curve to create the UML file using Eclipse Papyrus.
    * States and events must be type `String` instead of `Enum`.

