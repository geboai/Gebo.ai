# gebo.architecture.messages.rabbitmq

RabbitMQ implementation of the `gebo.application.messaging` external-bridge SPIs
(`IGExternalMessageEmitterProviderSource` / `IGExternalMessageReceiverProviderSource`),
letting the in-memory messaging system and broker exchange messages with remote
components over a RabbitMQ broker.

## How it plugs into the memory broker

The existing `MessageBrokeringAssembler` (in `gebo.application.messaging`) collects,
on context refresh, every `IGExternalMessageEmitterProviderSource` and
`IGExternalMessageReceiverProviderSource` bean and registers the emitters/receivers
they provide into the in-memory `IGMessageBroker`. This module contributes two such
sources:

- **Emitter source (inbound, RabbitMQ → local broker):** each configured emitter
  bridge is registered as an `IGMessageEmitter`. `RabbitMqInboundBridge` consumes the
  bound queue, deserializes the `GMessageEnvelope` and calls `broker.accept(...)`,
  which routes it to the local target receiver.
- **Receiver source (outbound, local broker → RabbitMQ):** each configured receiver
  bridge is registered as an `IGMessageReceiver`. When a local emitter targets it, the
  envelope is serialized and published to the exchange with the bridge's routing key.

Envelope (de)serialization is handled by `GMessageEnvelopeCodec` using the shared
Jackson 3 (`tools.jackson`) mapper; the payload is rebuilt into the concrete type
carried by the envelope's own `payloadType` field.

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
        exchange-type: topic
        declare-topology: true
        # Inbound: remote emitters exposed to the local broker
        emitters:
          - messaging-module-id: remote-module
            messaging-system-id: remote-emitter-component
            queue: gebo.inbound.remote
            routing-key: remote.emitter
            payload-types:
              - ai.gebo.application.messaging.model.GInternalDeletionMessagePayload
        # Outbound: local receivers that forward to RabbitMQ
        receivers:
          - messaging-module-id: remote-module
            messaging-system-id: remote-receiver-component
            queue: gebo.outbound.remote
            routing-key: remote.receiver
            accept-every-payload-type: true
```

Notes:

- For an inbound emitter, `payload-types` must list every payload type it is allowed
  to emit — the broker rejects envelopes whose type is not advertised by the source
  emitter.
- `routing-key` defaults to the `queue` name when omitted.
- `declare-topology: false` skips exchange/queue/binding declaration (provision it
  externally instead).
- The connection settings are intentionally independent from Spring Boot's
  `spring.rabbitmq.*` so this bridge can target a dedicated broker.
