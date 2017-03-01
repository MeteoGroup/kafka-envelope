package org.meteogroup.kafka.envelope;

import java.util.HashMap;
import java.util.Map;

public class Envelope<T> {

  public final Map<String, String> headers = new HashMap<>();
  public final T payload;

  public static <T> Envelope<T> envelope(Map<String, String> headers, T payload) {
    return new Envelope<T>(headers, payload);
  }

  public static <T> Envelope<T> envelope(T payload) {
    return new Envelope<T>(payload);
  }

  public Envelope(Map<String, String> headers, T payload) {
    this.headers.putAll(headers);
    this.payload = payload;
  }

  public Envelope(T payload) {
    this.payload = payload;
  }
}
