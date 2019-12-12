package com.discovery.interstellar.transport.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.discovery.interstellar.transport.system.schema.GetShortestPathRequest;
import com.discovery.interstellar.transport.system.schema.GetShortestPathResponse;

/**
 * The Class ShortestPathEndpoint.
 */
@Endpoint
public class ShortestPathEndpoint {
    
    /** The Constant NAMESPACE_URI. */
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    
    /** The path repository. */
    @Autowired
    ShortestPathRepository pathRepository;


    /**
     * Gets the shortest path.
     *
     * @param request the request
     * @return the shortest path
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getShortestPathRequest")

    @ResponsePayload
    public GetShortestPathResponse getShortestPath(@RequestPayload GetShortestPathRequest request) {
        GetShortestPathResponse response = new GetShortestPathResponse();
        response.setPath(pathRepository.getShortestPath(request.getName()));

        return response;
    }
}