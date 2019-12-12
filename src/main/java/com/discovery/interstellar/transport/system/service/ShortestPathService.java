package com.discovery.interstellar.transport.system.service;

import org.springframework.stereotype.Service;

import com.discovery.interstellar.transport.system.entity.Edge;
import com.discovery.interstellar.transport.system.entity.Vertex;
import com.discovery.interstellar.transport.system.helper.Graph;

import java.util.*;

/**
 * The Class ShortestPathService.
 */
@Service
public class ShortestPathService {

    /** The vertices. */
    private List<Vertex> vertices;
    
    /** The edges. */
    private List<Edge> edges;
    
    /** The visited vertices. */
    private Set<Vertex> visitedVertices;
    
    /** The unvisited vertices. */
    private Set<Vertex> unvisitedVertices;
    
    /** The previous paths. */
    private Map<Vertex, Vertex> previousPaths;
    
    /** The distance. */
    private Map<Vertex, Float> distance;

    /**
     * Instantiates a new shortest path service.
     */
    public ShortestPathService() {
    }

    /**
     * Instantiates a new shortest path service.
     *
     * @param graph the graph
     */
    public ShortestPathService(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    /**
     * Initialize planets.
     *
     * @param graph the graph
     */
    public void initializePlanets(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    /**
     * Run.
     *
     * @param source the source
     */
    public void run(Vertex source) {
        distance = new HashMap<>();
        previousPaths = new HashMap<>();
        visitedVertices = new HashSet<>();
        unvisitedVertices = new HashSet<>();
        distance.put(source, 0f);
        unvisitedVertices.add(source);
        while (unvisitedVertices.size() > 0) {
            Vertex currentVertex = getVertexWithLowestDistance(unvisitedVertices);
            visitedVertices.add(currentVertex);
            unvisitedVertices.remove(currentVertex);
            evaluateNeighborsWithMinimalDistances(currentVertex);
        }
    }

    /**
     * Gets the vertex with lowest distance.
     *
     * @param vertexes the vertexes
     * @return the vertex with lowest distance
     */
    private Vertex getVertexWithLowestDistance(Set<Vertex> vertexes) {
        Vertex lowestVertex = null;
        for (Vertex vertex : vertexes) {
            if (lowestVertex == null) {
                lowestVertex = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(lowestVertex)) {
                lowestVertex = vertex;
            }
        }
        return lowestVertex;
    }

    /**
     * Evaluate neighbors with minimal distances.
     *
     * @param currentVertex the current vertex
     */
    private void evaluateNeighborsWithMinimalDistances(Vertex currentVertex) {
        List<Vertex> adjacentVertices = getNeighbors(currentVertex);
        for (Vertex target : adjacentVertices) {
            float alternateDistance = getShortestDistance(currentVertex) + getDistance(currentVertex, target);
            if (alternateDistance < getShortestDistance(target)) {
                distance.put(target, alternateDistance);
                previousPaths.put(target, currentVertex);
                unvisitedVertices.add(target);
            }
        }
    }

    /**
     * Gets the neighbors.
     *
     * @param currentVertex the current vertex
     * @return the neighbors
     */
    private List<Vertex> getNeighbors(Vertex currentVertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex destination = fromId(edge.getDestination());
            if (edge.getSource().equals(currentVertex.getVertexId()) && !isVisited(destination)) {
                neighbors.add(destination);
            }
        }
        return neighbors;
    }

    /**
     * From id.
     *
     * @param str the str
     * @return the vertex
     */
    public Vertex fromId(final String str) {
        for (Vertex v : vertices) {
            if (v.getVertexId().equalsIgnoreCase(str)) {
                return v;
            }
        }
        Vertex islandVertex = new Vertex();
        islandVertex.setVertexId(str);
        islandVertex.setName("Island " + str);
        return islandVertex;
    }

    /**
     * Checks if is visited.
     *
     * @param vertex the vertex
     * @return true, if is visited
     */
    private boolean isVisited(Vertex vertex) {
        return visitedVertices.contains(vertex);
    }

    /**
     * Gets the shortest distance.
     *
     * @param destination the destination
     * @return the shortest distance
     */
    private Float getShortestDistance(Vertex destination) {
        Float d = distance.get(destination);
        if (d == null) {
            return Float.POSITIVE_INFINITY;
        } else {
            return d;
        }
    }

    /**
     * Gets the distance.
     *
     * @param source the source
     * @param target the target
     * @return the distance
     */
    private float getDistance(Vertex source, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source.getVertexId()) && edge.getDestination().equals(target.getVertexId())) {
                return edge.getDistance() + edge.getTimeDelay();
            }
        }
        throw new RuntimeException("Error: Something went wrong!");
    }

    /**
     * Gets the path.
     *
     * @param target the target
     * @return the path
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;

        if (previousPaths.get(step) == null) {
            return null;
        }
        path.add(step);
        while (previousPaths.get(step) != null) {
            step = previousPaths.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

}
