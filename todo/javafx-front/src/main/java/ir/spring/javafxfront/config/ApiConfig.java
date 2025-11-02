package ir.spring.javafxfront.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.time.Duration;

public final class ApiConfig {
        public static final String BASE_URL = "https://localhost:8080/todo";
        public static final String GET_API_URL = BASE_URL + "/get_all";
        public static final Duration CONNECT_TIMEOUT = Duration.ofMillis(5000);
        public static final Duration REQUEST_TIMEOUT = Duration.ofMillis(1000);
        private static final HttpClient CLIENT = getHttpClient();
        private static final ObjectMapper MAPPER = new ObjectMapper();

        private static HttpClient getHttpClient() {
                return HttpClient.newBuilder().connectTimeout(CONNECT_TIMEOUT).
                        version(HttpClient.Version.HTTP_1_1).build();
        }

        public static HttpClient getClient() {
                return CLIENT;
        }

        public static ObjectMapper getMapper() {
                return MAPPER;
        }

}
