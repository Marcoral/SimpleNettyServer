package com.github.marcoral.simplenettyserver.api.builder;

import com.github.marcoral.simplenettyserver.api.client.ClientFactory;

public interface ServerBuilderRequestClientFactory {
    ServerBuilder clientFactory(ClientFactory clientFactory);
}