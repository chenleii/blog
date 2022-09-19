package com.chen.blog.test.bdd.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/15 14:34
 */
public class ParameterTypes {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @DataTableType(replaceWithEmptyString = "null")
    public String dataTable(String cell) {
        return null;
    }

    @DocStringType
    public Map<String, Object> docString(String string) throws JsonProcessingException {
        return objectMapper.readValue(string, new TypeReference<Map<String, Object>>() {
        });
    }

    @ParameterType(name = "boolean", value = "(true|false)", useForSnippets = true, useRegexpMatchAsStrongTypeHint = true)
    public boolean boolean1(String s) {
        return Boolean.parseBoolean(s);
    }
}
