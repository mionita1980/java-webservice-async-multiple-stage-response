package com.mio;

import com.mio.dtos.ResponseDTO;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.json.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.stream.JsonGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@MicronautTest
class DemoTest {
    private static final Logger log = LoggerFactory.getLogger(DemoTest.class);

    @Property(name = "micronaut.application.name")
    String appName;

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testResponse() throws InterruptedException {
        log.info("Application name: {}", appName);
        log.info("########################## FIRST");
        //first request
        {
            HttpRequest<?> request = HttpRequest.GET("/demo/index/gigi/1111").accept(MediaType.APPLICATION_JSON);
            String response = client.toBlocking().retrieve(request);
            log.info("Response JSON: {}", response);

            //pretty print JSON
            JsonObject jsonObj= Json.createReader(new StringReader(response)).readObject();
            Map<String, Object> map = Map.of(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(map);
            StringWriter sw = new StringWriter();
            try (JsonWriter jsonWriter = writerFactory.createWriter(sw)) {
                jsonWriter.writeObject(jsonObj);
            }
            log.info("Response JSON (pretty print): {}", sw.toString());


            //tranform JSON to class
            Jsonb jsonb = JsonbBuilder.create();
            ResponseDTO responseDTO = jsonb.fromJson(response, ResponseDTO.class);
            log.info("Response DTO: {}", responseDTO);

            //expected
            ResponseDTO expected = new ResponseDTO("testName gigi 1111", "testAddress");

            //assertions
//            Assertions.assertNotNull(responseDTO);
//            Assertions.assertEquals(expected, responseDTO);
        }
        //second request
        Thread.sleep(1000);
        log.info("########################## SECOND");
        {
            HttpRequest<?> request = HttpRequest.GET("/demo/retrieve/gigi").accept(MediaType.APPLICATION_JSON);
            String response = client.toBlocking().retrieve(request);
            log.info("Complete response JSON: {}", response);
            //tranform JSON to class
            Jsonb jsonb = JsonbBuilder.create();
            ResponseDTO responseDTO = jsonb.fromJson(response, ResponseDTO.class);
            log.info("Response DTO: {}", responseDTO);
        }
        //third request
        Thread.sleep(3000);
        log.info("########################## THIRD");
        {
            HttpRequest<?> request = HttpRequest.GET("/demo/retrieve/gigi").accept(MediaType.APPLICATION_JSON);
            String response = client.toBlocking().retrieve(request);
            log.info("Complete response JSON: {}", response);
            //tranform JSON to class
            Jsonb jsonb = JsonbBuilder.create();
            ResponseDTO responseDTO = jsonb.fromJson(response, ResponseDTO.class);
            log.info("Response DTO: {}", responseDTO);
        }
        //final
        Thread.sleep(3000);
        log.info("########################## DONE");
    }

}
