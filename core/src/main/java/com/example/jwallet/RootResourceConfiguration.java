package com.example.jwallet;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("api")
@LoginConfig(authMethod = "MP-JWT")
public class RootResourceConfiguration extends Application {
}
