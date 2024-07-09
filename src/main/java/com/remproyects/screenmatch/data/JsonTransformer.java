package com.remproyects.screenmatch.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformer implements IJsonTransformer{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T fromJson(String json, Class<T> Tclass) {
        try {
            return  objectMapper.readValue(json,Tclass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
