package com.marina.spots;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Created by Marry on 03.10.2016.
 */
public class TypeSerializer extends StdSerializer<DotValues> {

    public TypeSerializer() {
        this(null);
    }

    public TypeSerializer(Class<DotValues> t) {
        super(t);
    }

    public void serialize
            (DotValues value, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("value");
        generator.writeString(value.getValue());
        generator.writeEndObject();
    }
}