package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class GenericEnvelopeSerdeRoundTripTest {

  GenericEnvelopeSerializer<String> serializer;
  GenericEnvelopeDeserializer<String> deserializer;

  @BeforeMethod
  public void setUp() throws Exception {
    serializer = new GenericEnvelopeSerializer<>(new StringSerializer());
    deserializer = new GenericEnvelopeDeserializer<>(new StringDeserializer());
  }

  @Test
  public void string_payload_and_headers_can_be_serialized_and_deserialized_again() throws Exception {
    Envelope<String> envelope = new Envelope<>(Collections.singletonMap("header", "value"), "payload");

    byte[] bytes = serializer.serialize("any topic", envelope);
    Envelope<String> recovered = deserializer.deserialize("any topic", bytes);

    assertThat(recovered.headers).containsOnly(entry("header", "value"));
    assertThat(recovered.payload).isEqualTo("payload");
  }
}