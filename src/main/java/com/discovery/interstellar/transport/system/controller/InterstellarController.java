package com.discovery.interstellar.transport.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.discovery.interstellar.transport.system.entity.Edge;
import com.discovery.interstellar.transport.system.entity.Traffic;
import com.discovery.interstellar.transport.system.entity.Vertex;
import com.discovery.interstellar.transport.system.helper.Graph;
import com.discovery.interstellar.transport.system.helper.ValidationCodes;
import com.discovery.interstellar.transport.system.model.ShortestPathModel;
import com.discovery.interstellar.transport.system.service.EntityManagerService;
import com.discovery.interstellar.transport.system.service.ShortestPathService;

import java.util.LinkedList;
import java.util.List;

/**
 * The Class InterstellarController.
 */
@Controller
public class InterstellarController {

    /** The Constant PATH_NOT_AVAILABLE. */
    private static final String PATH_NOT_AVAILABLE = "Unavailable.";
    
    /** The Constant PATH_NOT_NEEDED. */
    private static final String PATH_NOT_NEEDED = "Not needed. You are already on planet ";
    
    /** The Constant NO_PLANET_FOUND. */
    private static final String NO_PLANET_FOUND = "No planet found.";
    
    /** The Constant DUPLICATE_ROUTE. */
    private static final String DUPLICATE_ROUTE = "You cannot link a route to itself.";
    
    /** The Constant DUPLICATE_TRAFFIC. */
    private static final String DUPLICATE_TRAFFIC = "You cannot add traffic on the same route origin and destination.";
    
    /** The Constant INVALID_CODE. */
    private static final String INVALID_CODE = "Failed to find the validation code. Please start again.";
    
    /** The entity manager service. */
    private EntityManagerService entityManagerService;
    
    /** The shortest path service. */
    private ShortestPathService shortestPathService;

    /**
     * Instantiates a new interstellar controller.
     *
     * @param entityManagerService the entity manager service
     * @param shortestPathService the shortest path service
     */
    @Autowired
    public InterstellarController(EntityManagerService entityManagerService, ShortestPathService shortestPathService) {
        this.entityManagerService = entityManagerService;
        this.shortestPathService = shortestPathService;
    }

    /**
     * List vertices.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/vertices", method = RequestMethod.GET)
    public String listVertices(Model model) {
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        model.addAttribute("vertices", allVertices);
        return "vertices";
    }

    /**
     * Show vertex.
     *
     * @param vertexId the vertex id
     * @param model the model
     * @return the string
     */
    @RequestMapping("vertex/{vertexId}")
    public String showVertex(@PathVariable String vertexId, Model model) {
        model.addAttribute("vertex", entityManagerService.getVertexById(vertexId));
        return "vertexshow";
    }

    /**
     * Adds the vertex.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping("vertex/new")
    public String addVertex(Model model) {
        model.addAttribute("vertex", new Vertex());
        return "vertexadd";
    }

    /**
     * Save vertex.
     *
     * @param vertex the vertex
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "vertex", method = RequestMethod.POST)
    public String saveVertex(Vertex vertex, Model model) {
        if (entityManagerService.vertexExist(vertex.getVertexId())) {
            buildVertexValidation(vertex.getVertexId(), model);
            return "validation";
        }
        entityManagerService.saveVertex(vertex);
        return "redirect:/vertex/" + vertex.getVertexId();
    }

    /**
     * Edits the vertex.
     *
     * @param vertexId the vertex id
     * @param model the model
     * @return the string
     */
    @RequestMapping("vertex/edit/{vertexId}")
    public String editVertex(@PathVariable String vertexId, Model model) {
        model.addAttribute("vertex", entityManagerService.getVertexById(vertexId));
        return "vertexupdate";
    }

    /**
     * Update vertex.
     *
     * @param vertex the vertex
     * @return the string
     */
    @RequestMapping(value = "vertexupdate", method = RequestMethod.POST)
    public String updateVertex(Vertex vertex) {
        entityManagerService.updateVertex(vertex);
        return "redirect:/vertex/" + vertex.getVertexId();
    }

    /**
     * Delete vertex.
     *
     * @param vertexId the vertex id
     * @return the string
     */
    @RequestMapping("vertex/delete/{vertexId}")
    public String deleteVertex(@PathVariable String vertexId) {
        entityManagerService.deleteVertex(vertexId);
        return "redirect:/vertices";
    }

    /**
     * Builds the vertex validation.
     *
     * @param vertexId the vertex id
     * @param model the model
     */
    public void buildVertexValidation(String vertexId, Model model) {
        String vertexName = entityManagerService.getVertexById(vertexId) == null ? "" : entityManagerService.getVertexById(vertexId).getName();
        String message = "Planet " + vertexId + " already exists as " + vertexName;
        model.addAttribute("validationMessage", message);
    }

    /**
     * List edges.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/edges", method = RequestMethod.GET)
    public String listEdges(Model model) {
        List<Edge> allEdges = entityManagerService.getAllEdges();
        model.addAttribute("edges", allEdges);
        return "edges";
    }

    /**
     * Show edge.
     *
     * @param recordId the record id
     * @param model the model
     * @return the string
     */
    @RequestMapping("edge/{recordId}")
    public String showEdge(@PathVariable long recordId, Model model) {
        model.addAttribute("edge", entityManagerService.getEdgeById(recordId));
        return "edgeshow";
    }

    /**
     * Delete edge.
     *
     * @param recordId the record id
     * @return the string
     */
    @RequestMapping("edge/delete/{recordId}")
    public String deleteEdge(@PathVariable long recordId) {
        entityManagerService.deleteEdge(recordId);
        return "redirect:/edges";
    }

    /**
     * Adds the edge.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "edge/new", method = RequestMethod.GET)
    public String addEdge(Model model) {
        ShortestPathModel sh = new ShortestPathModel();
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        model.addAttribute("edge", new Edge());
        model.addAttribute("edgeModel", sh);
        model.addAttribute("routeList", allVertices);
        return "edgeadd";
    }

    /**
     * Save edge.
     *
     * @param edge the edge
     * @param pathModel the path model
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "edge", method = RequestMethod.POST)
    public String saveEdge(Edge edge, @ModelAttribute ShortestPathModel pathModel, Model model) {
        int id = (int) entityManagerService.getEdgeMaxRecordId() + 1;
        edge.setRecordId(id);
        edge.setEdgeId(String.valueOf(id));
        edge.setSource(pathModel.getSourceVertex());
        edge.setDestination(pathModel.getDestinationVertex());
        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
            buildEdgeValidation(pathModel, model, ValidationCodes.ROUTE_TO_SELF.toString());
            return "validation";
        }
        if (entityManagerService.edgeExists(edge)) {
            buildEdgeValidation(pathModel, model, ValidationCodes.ROUTE_EXISTS.toString());
            return "validation";
        }
        entityManagerService.saveEdge(edge);
        return "redirect:/edge/" + edge.getRecordId();
    }

    /**
     * Edits the edge.
     *
     * @param recordId the record id
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "edge/edit/{recordId}", method = RequestMethod.GET)
    public String editEdge(@PathVariable long recordId, Model model) {
        ShortestPathModel pathModel = new ShortestPathModel();
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        Edge edgeToEdit = entityManagerService.getEdgeById(recordId);
        pathModel.setSourceVertex(edgeToEdit.getSource());
        pathModel.setDestinationVertex(edgeToEdit.getDestination());
        model.addAttribute("edge", edgeToEdit);
        model.addAttribute("edgeModel", pathModel);
        model.addAttribute("routeList", allVertices);
        return "edgeupdate";
    }

    /**
     * Update edge.
     *
     * @param edge the edge
     * @param pathModel the path model
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "edgeupdate", method = RequestMethod.POST)
    public String updateEdge(Edge edge, @ModelAttribute ShortestPathModel pathModel, Model model) {
        edge.setSource(pathModel.getSourceVertex());
        edge.setDestination(pathModel.getDestinationVertex());
        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
            buildEdgeValidation(pathModel, model, ValidationCodes.ROUTE_TO_SELF.toString());
            return "validation";
        }

        if (entityManagerService.edgeExists(edge)) {
            buildEdgeValidation(pathModel, model, ValidationCodes.ROUTE_EXISTS.toString());
            return "validation";
        }
        entityManagerService.updateEdge(edge);
        return "redirect:/edge/" + edge.getRecordId();
    }

    /**
     * Builds the edge validation.
     *
     * @param pathModel the path model
     * @param model the model
     * @param code the code
     */
    public void buildEdgeValidation(@ModelAttribute ShortestPathModel pathModel, Model model, String code) {
        String message = "";
        ValidationCodes mode = ValidationCodes.fromString(code);
        if (mode != null) {
            switch (mode) {
                case ROUTE_EXISTS:
                    String sourceName = entityManagerService.getVertexById(pathModel.getSourceVertex()) == null ? "" : entityManagerService.getVertexById(pathModel.getSourceVertex()).getName();
                    String sourceDestination = entityManagerService.getVertexById(pathModel.getDestinationVertex()) == null ? "" : entityManagerService.getVertexById(pathModel.getDestinationVertex()).getName();
                    message = "The route from " + sourceName + " (" + pathModel.getSourceVertex() + ") to " + sourceDestination + "(" + pathModel.getDestinationVertex() + ") exists already.";
                    break;
                case ROUTE_TO_SELF:
                    message = DUPLICATE_ROUTE;
                    break;
                default:
                    message = INVALID_CODE;
                    break;
            }
        }
        model.addAttribute("validationMessage", message);
    }

    /**
     * List traffics.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/traffics", method = RequestMethod.GET)
    public String listTraffics(Model model) {
        List<Traffic> allTraffics = entityManagerService.getAllTraffics();
        model.addAttribute("traffics", allTraffics);
        return "traffics";
    }

    /**
     * Show traffic.
     *
     * @param routeId the route id
     * @param model the model
     * @return the string
     */
    @RequestMapping("traffic/{routeId}")
    public String showTraffic(@PathVariable String routeId, Model model) {
        model.addAttribute("traffic", entityManagerService.getTrafficById(routeId));
        return "trafficshow";
    }

    /**
     * Delete traffic.
     *
     * @param routeId the route id
     * @return the string
     */
    @RequestMapping("traffic/delete/{routeId}")
    public String deleteTraffic(@PathVariable String routeId) {
        entityManagerService.deleteTraffic(routeId);
        return "redirect:/traffics";
    }

    /**
     * Adds the traffic.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "traffic/new", method = RequestMethod.GET)
    public String addTraffic(Model model) {
        ShortestPathModel sh = new ShortestPathModel();
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        model.addAttribute("traffic", new Traffic());
        model.addAttribute("trafficModel", sh);
        model.addAttribute("trafficList", allVertices);
        return "trafficadd";
    }

    /**
     * Save traffic.
     *
     * @param traffic the traffic
     * @param pathModel the path model
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "traffic", method = RequestMethod.POST)
    public String saveTraffic(Traffic traffic, @ModelAttribute ShortestPathModel pathModel, Model model) {
        int id = (int) entityManagerService.getTrafficMaxRecordId() + 1;
        traffic.setRouteId(String.valueOf(id));
        traffic.setSource(pathModel.getSourceVertex());
        traffic.setDestination(pathModel.getDestinationVertex());
        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
            buildTrafficValidation(pathModel, model, ValidationCodes.TRAFFIC_TO_SELF.toString());
            return "validation";
        }
        if (entityManagerService.trafficExists(traffic)) {
            buildTrafficValidation(pathModel, model, ValidationCodes.TRAFFIC_EXISTS.toString());
            return "validation";
        }
        entityManagerService.saveTraffic(traffic);
        return "redirect:/traffic/" + traffic.getRouteId();
    }

    /**
     * Edits the traffic.
     *
     * @param routeId the route id
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "traffic/edit/{routeId}", method = RequestMethod.GET)
    public String editTraffic(@PathVariable String routeId, Model model) {
        ShortestPathModel pathModel = new ShortestPathModel();
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        Traffic trafficToEdit = entityManagerService.getTrafficById(routeId);
        pathModel.setSourceVertex(trafficToEdit.getSource());
        pathModel.setDestinationVertex(trafficToEdit.getDestination());
        model.addAttribute("traffic", trafficToEdit);
        model.addAttribute("trafficModel", pathModel);
        model.addAttribute("trafficList", allVertices);
        return "trafficupdate";
    }

    /**
     * Update traffic.
     *
     * @param traffic the traffic
     * @param pathModel the path model
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "trafficupdate", method = RequestMethod.POST)
    public String updateTraffic(Traffic traffic, @ModelAttribute ShortestPathModel pathModel, Model model) {
        traffic.setSource(pathModel.getSourceVertex());
        traffic.setDestination(pathModel.getDestinationVertex());
        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
            buildTrafficValidation(pathModel, model, ValidationCodes.TRAFFIC_TO_SELF.toString());
            return "validation";
        }
        if (entityManagerService.trafficExists(traffic)) {
            buildTrafficValidation(pathModel, model, ValidationCodes.TRAFFIC_EXISTS.toString());
            return "validation";
        }
        entityManagerService.updateTraffic(traffic);
        return "redirect:/traffic/" + traffic.getRouteId();
    }

    /**
     * Builds the traffic validation.
     *
     * @param pathModel the path model
     * @param model the model
     * @param code the code
     */
    public void buildTrafficValidation(@ModelAttribute ShortestPathModel pathModel, Model model, String code) {
        String message = "";
        ValidationCodes mode = ValidationCodes.fromString(code);
        if (mode != null) {
            switch (mode) {
                case TRAFFIC_EXISTS:
                    String sourceName = entityManagerService.getVertexById(pathModel.getSourceVertex()) == null ? "" : entityManagerService.getVertexById(pathModel.getSourceVertex()).getName();
                    String sourceDestination = entityManagerService.getVertexById(pathModel.getDestinationVertex()) == null ? "" : entityManagerService.getVertexById(pathModel.getDestinationVertex()).getName();
                    message = "The traffic from " + sourceName + " (" + pathModel.getSourceVertex() + ") to " + sourceDestination + " (" + pathModel.getDestinationVertex() + ") exists already.";
                    break;
                case TRAFFIC_TO_SELF:
                    message = DUPLICATE_TRAFFIC;
                    break;
                default:
                    message = INVALID_CODE;
                    break;
            }
        }
        //
        model.addAttribute("validationMessage", message);
    }

    /**
     * Shortest form.
     *
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/shortest", method = RequestMethod.GET)
    public String shortestForm(Model model) {
        ShortestPathModel pathModel = new ShortestPathModel();
        List<Vertex> allVertices = entityManagerService.getAllVertices();
        if (allVertices == null || allVertices.isEmpty()) {
            model.addAttribute("validationMessage", NO_PLANET_FOUND);
            return "validation";
        }
        Vertex origin = allVertices.get(0);
        pathModel.setVertexName(origin.getName());
        model.addAttribute("shortest", pathModel);
        model.addAttribute("pathList", allVertices);
        return "shortest";
    }

    /**
     * Shortest submit.
     *
     * @param pathModel the path model
     * @param model the model
     * @return the string
     */
    @RequestMapping(value = "/shortest", method = RequestMethod.POST)
    public String shortestSubmit(@ModelAttribute ShortestPathModel pathModel, Model model) {

        StringBuilder path = new StringBuilder();
        Graph graph = entityManagerService.selectGraph();
        if (pathModel.isTrafficAllowed()) {
            graph.setTrafficAllowed(true);
        }
        if (pathModel.isUndirectedGraph()) {
            graph.setUndirectedGraph(true);
        }
        shortestPathService.initializePlanets(graph);
        Vertex source = entityManagerService.getVertexByName(pathModel.getVertexName());
        Vertex destination = entityManagerService.getVertexById(pathModel.getSelectedVertex());
        //
        shortestPathService.run(source);
        LinkedList<Vertex> paths = shortestPathService.getPath(destination);
        if (paths != null) {
            for (Vertex v : paths) {
                path.append(v.getName() + " (" + v.getVertexId() + ")");
                path.append("\t");
            }
        } else if (source != null && destination != null && source.getVertexId().equals(destination.getVertexId())) {
            path.append(PATH_NOT_NEEDED + source.getName());
        } else {
            path.append(PATH_NOT_AVAILABLE);
        }
        pathModel.setThePath(path.toString());
        pathModel.setSelectedVertexName(destination.getName());
        model.addAttribute("shortest", pathModel);
        return "result";
    }
}