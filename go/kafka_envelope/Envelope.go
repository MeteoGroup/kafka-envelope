package kafka_envelope

import "github.com/golang/protobuf/proto"
import "github.com/Shopify/sarama"

type ProducerEnvelope struct {
  Headers map[string]string;
  Payload sarama.Encoder;
}

type ConsumerEnvelope []byte

func (message ConsumerEnvelope) Unmarshal() (envelope Envelope, err error) {
  err = proto.Unmarshal(message, &envelope)
  return
}

// implementing Shopify/sarama/Encoder
func (e ProducerEnvelope) Encode() (message []byte, err error) {
  payload, err := e.Payload.Encode()
  if (err != nil) {
    return
  }
  return proto.Marshal(&Envelope{
    Headers: e.Headers,
    Payload: payload,
  })
}

func (e ProducerEnvelope) Length() int {
  marshaled, _ := e.Encode()
  return len(marshaled)
}
