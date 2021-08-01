package com.example.snowball.serializer;

import com.example.snowball.domain.user.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class UserSessionSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("id", user.getId());
    jsonGenerator.writeStringField("uuid", user.getUuid());
    jsonGenerator.writeStringField("login", user.getLogin());
    jsonGenerator.writeStringField("name", user.getName());
    jsonGenerator.writeEndObject();
  }
}
