package com.remproyects.screenmatch.data;

public interface IJsonTransformer {
    <T> T fromJson(String json, Class<T> Tclass);
}
