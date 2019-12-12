package com.discovery.interstellar.transport.system.helper;

import java.util.ArrayList;
import java.util.List;

import com.discovery.interstellar.transport.system.entity.Edge;
import com.discovery.interstellar.transport.system.entity.Traffic;
import com.discovery.interstellar.transport.system.entity.Vertex;

/**
 * The Class Graph.
 */
public class Graph {

    /** The vertexes. */
    private List<Vertex> vertexes;
    
    /** The edges. */
    private List<Edge> edges;
    
    /** The traffics. */
    private List<Traffic> traffics;
    
    /** The undirected graph. */
    private boolean undirectedGraph;
    
    /** The traffic allowed. */
    private boolean trafficAllowed;

    /**
     * Instantiates a new graph.
     *
     * @param vertexes the vertexes
     * @param edges the edges
     * @param traffics the traffics
     */
    public Graph(List<Vertex> vertexes, List<Edge> edges, List<Traffic> traffics) {
        this.vertexes = vertexes;
        this.edges = edges;
        this.traffics = traffics;
    }

    /**
     * Gets the traffics.
     *
     * @return the traffics
     */
    public List<Traffic> getTraffics() {
        return traffics;
    }

    /**
     * Gets the vertexes.
     *
     * @return the vertexes
     */
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    /**
     * Gets the edges.
     *
     * @return the edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Checks if is undirected graph.
     *
     * @return true, if is undirected graph
     */
    public boolean isUndirectedGraph() {
        return undirectedGraph;
    }

    /**
     * Sets the undirected graph.
     *
     * @param undirectedGraph the new undirected graph
     */
    public void setUndirectedGraph(boolean undirectedGraph) {
        this.undirectedGraph = undirectedGraph;
    }

    /**
     * Checks if is traffic allowed.
     *
     * @return true, if is traffic allowed
     */
    public boolean isTrafficAllowed() {
        return trafficAllowed;
    }

    /**
     * Sets the traffic allowed.
     *
     * @param trafficAllowed the new traffic allowed
     */
    public void setTrafficAllowed(boolean trafficAllowed) {
        this.trafficAllowed = trafficAllowed;
    }

    /**
     * Process traffics.
     */
    public void processTraffics() {
        if (traffics != null && !traffics.isEmpty()) {
            for (Traffic traffic : traffics) {
                for (Edge edge : edges) {
                    if (checkObjectsEqual(edge.getEdgeId(), traffic.getRouteId())) {
                        if (checkObjectsEqual(edge.getSource(), traffic.getSource()) && checkObjectsEqual(edge.getDestination(), traffic.getDestination())) {
                            edge.setTimeDelay(traffic.getDelay());
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the undirected edges.
     *
     * @return the undirected edges
     */
    public List<Edge> getUndirectedEdges() {
        List<Edge> undirectedEdges = new ArrayList<Edge>();
        for (Edge fromEdge : edges) {
            Edge toEdge = copyAdjacentEdge(fromEdge);
            undirectedEdges.add(fromEdge);
            undirectedEdges.add(toEdge);
        }
        return undirectedEdges;
    }

    /**
     * Copy adjacent edge.
     *
     * @param fromEdge the from edge
     * @return the edge
     */
    public Edge copyAdjacentEdge(Edge fromEdge) {
        Edge toEdge = new Edge();
        toEdge.setEdgeId(fromEdge.getEdgeId());
        toEdge.setSource(fromEdge.getDestination());
        toEdge.setDestination(fromEdge.getSource());
        toEdge.setDistance(fromEdge.getDistance());
        toEdge.setTimeDelay(fromEdge.getTimeDelay());
        return toEdge;
    }

    /**
     * Check objects equal.
     *
     * @param object the object
     * @param otherObject the other object
     * @return true, if successful
     */
    public boolean checkObjectsEqual(Object object, Object otherObject) {
        if (object == null && otherObject == null) {
            //Both objects are null
            return true;
        } else if (object == null || otherObject == null) {
            //One of the objects is null
            return false;
        } else if (object instanceof String && otherObject instanceof String) {
            return ((String) object).equalsIgnoreCase((String) otherObject);
        } else {
            //Both objects are not null
            return object.equals(otherObject);
        }

    }
}
