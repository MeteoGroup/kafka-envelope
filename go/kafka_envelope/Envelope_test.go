package kafka_envelope

import (
  "testing"
  "github.com/Shopify/sarama"
  "github.com/golang/protobuf/proto"
)

var _ = proto.Unmarshal

func ProducerEnvelope_can_be_used_as_ProducerMessage_Value(envelope ProducerEnvelope) interface{} {
  return sarama.ProducerMessage{Value: envelope}
}

func TestRoundTrip(t *testing.T) {
  message, _ := ProducerEnvelope{
    Headers:map[string]string{"key": "value"},
    Payload: sarama.StringEncoder("payload"),
  }.Encode()

  envelope, err := ConsumerEnvelope(message).Unmarshal()

  if (err != nil) {
    t.Fatal(err)
  }
  if string(envelope.Payload) != "payload" {
    t.Fatal("payload lost during marshaling/unmarshaling, expected \"payload\", got: \"" + string(envelope.Payload) + "\"")
  }

  if string(envelope.Headers["key"]) != "value" {
    t.Fatal("headers lost during marshaling/unmarshaling, expected \"value\", got: \"" + envelope.Headers["key"] + "\"")
  }
}
