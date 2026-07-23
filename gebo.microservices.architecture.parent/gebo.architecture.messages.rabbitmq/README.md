# gebo.architecture.messages.rabbitmq

RabbitMQ implementation of the `gebo.application.messaging` external-bridge SPIs
(`IGExternalMessageEmitterProviderSource` / `IGExternalMessageReceiverProviderSource`),
letting a microservice's in-memory messaging system and broker exchange messages
with the other microservices of the architecture over a RabbitMQ broker.

## Routing model

A `GMessageEnvelope` already carries everything the broker needs to route it:
`sourceModule`/`sourceComponent` (the emitter) and `targetModule`/`targetComponent`
(the receiver). A single microservice hosts **many** emitters and receivers across
several modules and components, so the bridge is **not** tied to a fixed
module/system — it works purely off the envelope's own fields.

The unit of addressing over RabbitMQ is the **microservice**, not the endpoint:

- Each microservice consumes exactly **one inbound queue**, bound to the shared
  exchange with a routing key equal to its own `localMicroserviceId`.
- **Outbound (local broker → RabbitMQ):** every remote receiver endpoint listed in
  `remote-receivers` is registered in the local broker as an `IGMessageReceiver`.
  When a local emitter targets it, the envelope is serialized and published with
  the routing key of the microservice that hosts the target module — resolved from
  the `microservices` map by `targetModule` — landing in that microservice's queue.
- **Inbound (RabbitMQ → local broker):** the single listener deserializes each
  envelope and calls `broker.accept(envelope)` **unchanged**. Its
  `sourceModule`/`sourceComponent` match a remote emitter registered from
  `remote-emitters`, and its `targetModule`/`targetComponent` match a real local
  receiver, so the broker routes it to the right local component.

Envelope (de)serialization is handled by `GMessageEnvelopeCodec` using the shared
Jackson 3 (`tools.jackson`) mapper; the payload is rebuilt into the concrete type
carried by the envelope's own `payloadType` field.

## How it plugs into the memory broker

The existing `MessageBrokeringAssembler` (in `gebo.application.messaging`) collects,
on context refresh, every `IGExternalMessageEmitterProviderSource` and
`IGExternalMessageReceiverProviderSource` bean and registers the emitters/receivers
they provide into the in-memory `IGMessageBroker`. This module contributes one
source of each kind, expanding the configured remote endpoints into broker
emitters (from `remote-emitters`) and receivers (from `remote-receivers`).

## Enabling

The whole integration is inert unless `ai.gebo.messaging.rabbitmq.enabled=true`.
Add this module as a dependency of the bootable app (the app already component-scans
`ai.gebo`) and provide the bindings below.

```yaml
ai:
  gebo:
    messaging:
      rabbitmq:
        enabled: true
        connection:
          host: localhost
          port: 5672
          virtual-host: /
          username: guest
          password: guest
        exchange: gebo.messaging
        exchange-type: direct
        declare-topology: true
        # Identity of THIS microservice: the routing key its inbound queue is bound
        # with (and, unless inbound-queue overrides it, the queue name).
        local-microservice-id: ingestion-service
        # The architecture map: which modules live in which remote microservice.
        # Used to address outbound envelopes by their targetModule.
        microservices:
          - microservice-id: core-service
            modules:
              - core
              - workflow
          - microservice-id: rag-service
            modules:
              - rag
        # Remote endpoints that may send messages INTO this microservice.
        # Registered as broker emitters so inbound envelopes are accepted.
        remote-emitters:
          - module-id: core
            component-id: workflowRouter
            payload-types:
              - ai.gebo.application.messaging.model.GInternalDeletionMessagePayload
        # Remote endpoints this microservice may send messages TO.
        # Registered as broker receivers that publish to the owning microservice.
        remote-receivers:
          - module-id: rag
            component-id: ragIndexer
            accept-every-payload-type: true
```

Notes:

- A `remote-emitters` entry must list every `payload-types` it is allowed to emit —
  the broker rejects envelopes whose type is not advertised by the source emitter.
- Each `remote-receivers` entry's `module-id` must appear under some microservice in
  the `microservices` map so its destination routing key can be resolved; otherwise
  the module id itself is used as the routing key (with a warning).
- `declare-topology: false` skips exchange/queue/binding declaration (provision it
  externally instead). Each microservice declares only its own inbound queue.
- The connection settings are intentionally independent from Spring Boot's
  `spring.rabbitmq.*` so this bridge can target a dedicated broker.
