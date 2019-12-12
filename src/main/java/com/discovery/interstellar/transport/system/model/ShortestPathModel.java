package com.discovery.interstellar.transport.system.model;


/**
 * The Class ShortestPathModel.
 */
public class ShortestPathModel {

    /** The selected vertex. */
    private String selectedVertex;
    
    /** The selected vertex name. */
    private String selectedVertexName;
    
    /** The vertex id. */
    private String vertexId;
    
    /** The vertex name. */
    private String vertexName;
    
    /** The path. */
    private String thePath;
    
    /** The source vertex. */
    private String sourceVertex;
    
    /** The destination vertex. */
    private String destinationVertex;
    
    /** The undirected graph. */
    private boolean undirectedGraph;
    
    /** The traffic allowed. */
    private boolean trafficAllowed;

    /**
     * Gets the selected vertex.
     *
     * @return the selected vertex
     */
    public String getSelectedVertex() {
        return selectedVertex;
    }

    /**
     * Sets the selected vertex.
     *
     * @param selectedVertex the new selected vertex
     */
    public void setSelectedVertex(String selectedVertex) {
        this.selectedVertex = selectedVertex;
    }

    /**
     * Gets the vertex id.
     *
     * @return the vertex id
     */
    public String getVertexId() {
        return vertexId;
    }

    /**
     * Sets the vertex id.
     *
     * @param vertexId the new vertex id
     */
    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    /**
     * Gets the vertex name.
     *
     * @return the vertex name
     */
    public String getVertexName() {
        return vertexName;
    }

    /**
     * Sets the vertex name.
     *
     * @param vertexName the new vertex name
     */
    public void setVertexName(String vertexName) {
        this.vertexName = vertexName;
    }

    /**
     * Gets the the path.
     *
     * @return the the path
     */
    public String getThePath() {
        return thePath;
    }

    /**
     * Sets the the path.
     *
     * @param thePath the new the path
     */
    public void setThePath(String thePath) {
        this.thePath = thePath;
    }

    /**
     * Gets the selected vertex name.
     *
     * @return the selected vertex name
     */
    public String getSelectedVertexName() {
        return selectedVertexName;
    }

    /**
     * Sets the selected vertex name.
     *
     * @param selectedVertexName the new selected vertex name
     */
    public void setSelectedVertexName(String selectedVertexName) {
        this.selectedVertexName = selectedVertexName;
    }

    /**
     * Gets the source vertex.
     *
     * @return the source vertex
     */
    public String getSourceVertex() {
        return sourceVertex;
    }

    /**
     * Sets the source vertex.
     *
     * @param sourceVertex the new source vertex
     */
    public void setSourceVertex(String sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    /**
     * Gets the destination vertex.
     *
     * @return the destination vertex
     */
    public String getDestinationVertex() {
        return destinationVertex;
    }

    /**
     * Sets the destination vertex.
     *
     * @param destinationVertex the new destination vertex
     */
    public void setDestinationVertex(String destinationVertex) {
        this.destinationVertex = destinationVertex;
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
}
