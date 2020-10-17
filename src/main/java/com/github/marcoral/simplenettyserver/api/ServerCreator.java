package com.github.marcoral.simplenettyserver.api;

import com.github.marcoral.simplenettyserver.api.builder.ServerBuilderRequestClientFactory;
import com.github.marcoral.simplenettyserver.builder.ServerBuilderRequestClientFactoryImpl;

public class ServerCreator {
    public static ServerBuilderRequestClientFactory runNewServer() {
        return new ServerBuilderRequestClientFactoryImpl();
    }
}