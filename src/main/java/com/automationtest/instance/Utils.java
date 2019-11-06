package com.automationtest.instance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Utils {
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static final ObjectMapper mapper = new ObjectMapper();

  public static String toJson(Object obj){
    return gson.toJson(obj);
  }
  public static String now() {
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    return simpleDateFormat.format(date);
  }

  public static String uuid() {
    return UUID.randomUUID().toString();
  }

  public static String randomDigit() {
    return String.valueOf( new Random().nextInt(99999) );
  }

  public static String generatePassword() {
    StringBuilder password = new StringBuilder();
    for (int i : new Random().ints(Constants.PASSWORD_LENGTH, 32, 126).toArray()) {
      password.append((char) i);
    }
    return password.toString();
  }

  public static boolean isJson(String json) {
    try {
      mapper.reader().readTree(json);
    }catch (Exception e){
      return false;
    }
    return true;
  }

  public static boolean isYaml(String yaml) {
    try {
      ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
      yamlReader.readValue(yaml, Object.class);
    }catch (Exception e){
      return false;
    }
    return true;
  }

  public static String yamlToJson(String yaml) throws IOException {
    if (Strings.isNullOrEmpty(yaml)){
        return null;
    }
    ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
    Object obj = yamlReader.readValue(yaml, Object.class);
    ObjectMapper jsonWriter = new ObjectMapper();
    return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
  }

  public static String jsonToYaml(String json) throws IOException {
    if (Strings.isNullOrEmpty(json)){
        return null;
    }
    JsonNode jsonNode = mapper.reader().readTree(json);
    return new YAMLMapper().writeValueAsString(jsonNode);
  }
}
