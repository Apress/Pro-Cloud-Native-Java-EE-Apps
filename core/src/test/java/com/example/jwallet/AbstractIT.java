package com.example.jwallet;

import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public class AbstractIT {

    protected static String AUTH_HEADER;
    protected static WebTarget target;

    protected static void init(String serviceName, Integer servicePort) {
        final Client client = ClientBuilder.newClient();
        String keycloakServiceUrlTemplate = "http://%s:%d/realms/jwallet/protocol/openid-connect/token";
        String keycloakServiceUrl;
        String targetServiceUrlTemplate = "http://%s:%d/%s/api";
        String targetServiceUrl;

        boolean testInContainer = System.getProperties().containsKey("tc");
        if (testInContainer) {
            final DockerComposeContainer composeContainer =
                    new DockerComposeContainer(new File("../" + serviceName, "docker-compose.yaml"))
                            .withExposedService(serviceName, servicePort)
                            .waitingFor(serviceName, Wait.forHttp("/" + serviceName + "/api/hello").forPort(servicePort));
            composeContainer.start();

            keycloakServiceUrl = String.format(keycloakServiceUrlTemplate,
                    composeContainer.getServiceHost("keycloak", servicePort + 2000),
                    servicePort + 2000);

            targetServiceUrl = String.format(targetServiceUrlTemplate,
                    composeContainer.getServiceHost(serviceName, servicePort),
                    composeContainer.getServicePort(serviceName, servicePort),
                    serviceName);
        } else {
            keycloakServiceUrl = String.format(keycloakServiceUrlTemplate, "localhost", servicePort + 2000);
            targetServiceUrl = String.format(targetServiceUrlTemplate, "localhost", servicePort, serviceName);
        }

        // login and set token
        final WebTarget keycloak = client.target(keycloakServiceUrl);
        final Form data = new Form();
        data.param("realm", "jwallet");
        data.param("grant_type", "password");
        data.param("client_id", "jwallet-service");
        data.param("username", "max");
        data.param("password", "max");
        final JsonObject response = keycloak.request().post(Entity.form(data), JsonObject.class);
        final String accessToken = response.getString("access_token");
        AUTH_HEADER = "Bearer " + accessToken;

        target = client.target(targetServiceUrl);
        target.register(TestAuthFilter.class);
    }

}
