package com.automationtest.instance.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Utils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(final Object obj) {
        return gson.toJson(obj);
    }

    public static String now() {
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return simpleDateFormat.format(date);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String randomDigit() {
        return String.valueOf(new Random().nextInt(99999));
    }

    public static String generatePassword() {
        final StringBuilder password = new StringBuilder();
        for (final int i : new Random().ints(Constants.PASSWORD_LENGTH, 32, 126).toArray()) {
            password.append((char) i);
        }
        return password.toString();
    }

    public static boolean isJson(final String json) {
        try {
            mapper.reader().readTree(json);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isYaml(final String yaml) {
        try {
            final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            yamlReader.readValue(yaml, Object.class);
        } catch (final Exception e) {
            return false;
        }
        return true;
    }

    public static String yamlToJson(final String yaml) throws IOException {
        if (Strings.isNullOrEmpty(yaml)) {
            return null;
        }
        final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        final Object obj = yamlReader.readValue(yaml, Object.class);
        final ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    public static String jsonToYaml(final String json) throws IOException {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        final JsonNode jsonNode = mapper.reader().readTree(json);
        return new YAMLMapper().writeValueAsString(jsonNode);
    }

    public static void unzipFile(final String ziFile, final String destFolder) throws IOException {
        InputStream input;
        OutputStream output;
        final File destDir = new File(destFolder);
        final ZipFile zipfile = new ZipFile(new File(ziFile));
        final Enumeration zipEntries = zipfile.entries();
        while (zipEntries.hasMoreElements()) {
            final ZipEntry entry = (ZipEntry) zipEntries.nextElement();
            if (entry.isDirectory()) {
                new File(destDir, entry.getName()).mkdir();
                continue;
            }
            input = new BufferedInputStream(zipfile.getInputStream(entry));
            final File destFile = new File(destDir, entry.getName());
            final FileOutputStream fos = new FileOutputStream(destFile);
            output = new BufferedOutputStream(fos);
            copyStreams(input, output);
            input.close();
            output.flush();
            output.close();
        }
    }

    private static void copyStreams(final InputStream input, final OutputStream output) throws IOException {
        int count;
        final byte[] data = new byte[1024];
        while ((count = input.read(data, 0, 1024)) != -1) {
            output.write(data, 0, count);
        }
    }

    public static void deleteFileOrFolder(final String... files) {
        for (final String fileName : files) {
            final File file = new File(fileName);
            if (file.exists()) {
                if (file.isDirectory()) {
                    for (final File subFile : Objects.requireNonNull(file.listFiles())) {
                        deleteFileOrFolder(subFile.getAbsolutePath());
                    }
                    file.delete();
                } else {
                    file.delete();
                }
            }
        }
    }
}
