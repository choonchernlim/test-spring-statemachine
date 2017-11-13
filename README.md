# test-spring-statemachine


## Default State Machine Factory

In this example, `MailingStateMachineConfig` where all states and events are configured in that configuration file.

Run `mvn clean compile spring-boot:run`.

## UML-Based State Machine Factory

In this example, `MailingUmlStateMachineConfig` is used where all states and events are configured in the UML file 
using Eclipse Papyrus.

Run `mvn clean compile spring-boot:run -Drun.profiles=uml` 
