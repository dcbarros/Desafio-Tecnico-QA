package org.desafioqa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class DataLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private DataLoader() {}

    public static <T> T fromResource(String resourcePath, Class<T> type) {
        String path = normalize(resourcePath);
        try (InputStream in = DataLoader.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalArgumentException("Recurso não encontrado no classpath: " + path);
            }
            return MAPPER.readValue(in, type);
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler JSON do recurso: " + path, e);
        }
    }

    public static <T> T fromResource(String resourcePath, TypeReference<T> typeRef) {
        String path = normalize(resourcePath);
        try (InputStream in = DataLoader.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalArgumentException("Recurso não encontrado no classpath: " + path);
            }
            return MAPPER.readValue(in, typeRef);
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler JSON do recurso: " + path, e);
        }
    }

    public static <T> T fromFile(Path file, Class<T> type) {
        try (InputStream in = Files.newInputStream(file)) {
            return MAPPER.readValue(in, type);
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler JSON do arquivo: " + file, e);
        }
    }

    public static <T> T fromString(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao ler JSON da String", e);
        }
    }

    private static String normalize(String resourcePath) {
        return resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
    }
}
