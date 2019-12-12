package com.discovery.interstellar.transport.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discovery.interstellar.transport.system.dao.EdgeDao;
import com.discovery.interstellar.transport.system.dao.TrafficDao;
import com.discovery.interstellar.transport.system.dao.VertexDao;
import com.discovery.interstellar.transport.system.entity.Edge;
import com.discovery.interstellar.transport.system.entity.Traffic;
import com.discovery.interstellar.transport.system.entity.Vertex;
import com.discovery.interstellar.transport.system.helper.Graph;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * The Class EntityManagerService.
 */
@Service
public class EntityManagerService {
    
    /** The Constant EXCEL_FILENAME. */
    private static final String EXCEL_FILENAME = "/interstellar.xlsx";
    
    /** The vertex dao. */
    private VertexDao vertexDao;
    
    /** The edge dao. */
    private EdgeDao edgeDao;
    
    /** The traffic dao. */
    private TrafficDao trafficDao;

    /**
     * Instantiates a new entity manager service.
     *
     * @param vertexDao the vertex dao
     * @param edgeDao the edge dao
     * @param trafficDao the traffic dao
     */
    @Autowired
    public EntityManagerService(VertexDao vertexDao, EdgeDao edgeDao, TrafficDao trafficDao) {
        this.vertexDao = vertexDao;
        this.edgeDao = edgeDao;
        this.trafficDao = trafficDao;
    }

    /**
     * Persist graph.
     */
    public void persistGraph() {
        URL resource = getClass().getResource(EXCEL_FILENAME);
        File file1;
        try {
            file1 = new File(resource.toURI());
            persistGraph(file1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Persist graph.
     *
     * @param file the file
     */
    public void persistGraph(File file) {
        XLSXHandler handler = new XLSXHandler(file);

        List<Vertex> vertices = handler.readVertexes();
        if (vertices != null && !vertices.isEmpty()) {
            for (Vertex v : vertices) {
                vertexDao.save(v);
            }
        }
        List<Edge> edges = handler.readEdges();
        if (edges != null && !edges.isEmpty()) {
            for (Edge e : edges) {
                edgeDao.save(e);
            }
        }
        List<Traffic> traffic = handler.readTraffics();
        if (edges != null && !edges.isEmpty()) {
            for (Traffic t : traffic) {
                trafficDao.save(t);
            }
        }
    }

    /**
     * Select graph.
     *
     * @return the graph
     */
    public Graph selectGraph() {
        List<Vertex> vertices = vertexDao.selectAll();
        List<Edge> edges = edgeDao.selectAll();
        List<Traffic> traffics = trafficDao.selectAll();

        Graph graph = new Graph(vertices, edges, traffics);

        return graph;
    }

    /**
     * Save vertex.
     *
     * @param vertex the vertex
     * @return the vertex
     */
    public Vertex saveVertex(Vertex vertex) {
        vertexDao.save(vertex);
        return vertex;
    }

    /**
     * Update vertex.
     *
     * @param vertex the vertex
     * @return the vertex
     */
    public Vertex updateVertex(Vertex vertex) {
        vertexDao.update(vertex);
        return vertex;
    }

    /**
     * Delete vertex.
     *
     * @param vertexId the vertex id
     * @return true, if successful
     */
    public boolean deleteVertex(String vertexId) {
        vertexDao.delete(vertexId);
        return true;
    }

    /**
     * Gets the all vertices.
     *
     * @return the all vertices
     */
    public List<Vertex> getAllVertices() {
        return vertexDao.selectAll();
    }

    /**
     * Gets the vertex by name.
     *
     * @param name the name
     * @return the vertex by name
     */
    public Vertex getVertexByName(String name) {
        return vertexDao.selectUniqueByName(name);
    }

    /**
     * Gets the vertex by id.
     *
     * @param vertexId the vertex id
     * @return the vertex by id
     */
    public Vertex getVertexById(String vertexId) {
        return vertexDao.selectUnique(vertexId);
    }

    /**
     * Vertex exist.
     *
     * @param vertexId the vertex id
     * @return true, if successful
     */
    public boolean vertexExist(String vertexId) {
        Vertex vertex = vertexDao.selectUnique(vertexId);
        return vertex != null;
    }

    /**
     * Save edge.
     *
     * @param edge the edge
     * @return the edge
     */
    public Edge saveEdge(Edge edge) {
        edgeDao.save(edge);
        return edge;
    }

    /**
     * Update edge.
     *
     * @param edge the edge
     * @return the edge
     */
    public Edge updateEdge(Edge edge) {
        edgeDao.update(edge);
        return edge;
    }

    /**
     * Delete edge.
     *
     * @param recordId the record id
     * @return true, if successful
     */
    public boolean deleteEdge(long recordId) {
        edgeDao.delete(recordId);
        return true;
    }

    /**
     * Gets the all edges.
     *
     * @return the all edges
     */
    public List<Edge> getAllEdges() {
        return edgeDao.selectAll();
    }

    /**
     * Gets the edge by id.
     *
     * @param recordId the record id
     * @return the edge by id
     */
    public Edge getEdgeById(long recordId) {
        return edgeDao.selectUnique(recordId);
    }

    /**
     * Gets the edge max record id.
     *
     * @return the edge max record id
     */
    public long getEdgeMaxRecordId() {
        return edgeDao.selectMaxRecordId();
    }

    /**
     * Edge exists.
     *
     * @param edge the edge
     * @return true, if successful
     */
    public boolean edgeExists(Edge edge) {
        List<Edge> edges = edgeDao.edgeExists(edge);
        return !edges.isEmpty();
    }

    /**
     * Save traffic.
     *
     * @param traffic the traffic
     * @return the traffic
     */
    public Traffic saveTraffic(Traffic traffic) {
        trafficDao.save(traffic);
        return traffic;
    }

    /**
     * Update traffic.
     *
     * @param traffic the traffic
     * @return the traffic
     */
    public Traffic updateTraffic(Traffic traffic) {
        trafficDao.update(traffic);
        return traffic;
    }

    /**
     * Delete traffic.
     *
     * @param routeId the route id
     * @return true, if successful
     */
    public boolean deleteTraffic(String routeId) {
        trafficDao.delete(routeId);
        return true;
    }

    /**
     * Gets the all traffics.
     *
     * @return the all traffics
     */
    public List<Traffic> getAllTraffics() {
        return trafficDao.selectAll();
    }

    /**
     * Gets the traffic by id.
     *
     * @param routeId the route id
     * @return the traffic by id
     */
    public Traffic getTrafficById(String routeId) {
        return trafficDao.selectUnique(routeId);
    }

    /**
     * Gets the traffic max record id.
     *
     * @return the traffic max record id
     */
    public long getTrafficMaxRecordId() {
        return trafficDao.selectMaxRecordId();
    }

    /**
     * Traffic exists.
     *
     * @param traffic the traffic
     * @return true, if successful
     */
    public boolean trafficExists(Traffic traffic) {
        List<Traffic> traffics = trafficDao.trafficExists(traffic);
        return !traffics.isEmpty();
    }
}
