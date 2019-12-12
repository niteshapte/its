package com.discovery.interstellar.transport.system;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.discovery.interstellar.transport.system.ShortestPathRepository;
import com.discovery.interstellar.transport.system.configuration.DatasourceBean;
import com.discovery.interstellar.transport.system.configuration.PersistenceBean;
import com.discovery.interstellar.transport.system.entity.Edge;
import com.discovery.interstellar.transport.system.entity.Traffic;
import com.discovery.interstellar.transport.system.entity.Vertex;
import com.discovery.interstellar.transport.system.helper.Graph;
import com.discovery.interstellar.transport.system.service.EntityManagerService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatasourceBean.class, PersistenceBean.class},
        loader = AnnotationConfigContextLoader.class)
@Ignore
public class ShortestPathRepositoryTest {
    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager platformTransactionManager;

    @Test
    public void verifyThatDataInitializeAndGiveCorrectPath() throws Exception {

        // SetUp Fixture
        EntityManagerService entityManagerService = mock(EntityManagerService.class);

        Vertex vertex1 = new Vertex("A", "Earth");
        Vertex vertex2 = new Vertex("F", "Pluto");
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(vertex1);
        vertices.add(vertex2);
        Edge edge1 = new Edge(1, "30", "A", "F", 0.17f);
        List<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        Traffic traffic = new Traffic("1", "A", "F", 4f);
        List<Traffic> traffics = new ArrayList<>();
        traffics.add(traffic);

        StringBuilder path = new StringBuilder();
        Vertex expectedSource = vertices.get(0);
        Vertex expectedDestination = vertices.get(1);
        Graph graph = new Graph(vertices, edges, traffics);
        LinkedList<Vertex> pathList = new LinkedList<>();
        pathList.add(expectedSource);
        pathList.add(expectedDestination);
        when(entityManagerService.selectGraph()).thenReturn(graph);
        when(entityManagerService.getVertexByName(expectedDestination.getName())).thenReturn(expectedDestination);
        when(entityManagerService.getVertexById(expectedDestination.getVertexId())).thenReturn(expectedDestination);

        path.append("Earth (A)\tPluto (F)\t");
        ShortestPathRepository pathRepository = new ShortestPathRepository(platformTransactionManager, entityManagerService);

        // Test
        pathRepository.initData();
        String actualPath = pathRepository.getShortestPath("Pluto");

        //Verify
        assertThat(actualPath, sameBeanAs(path.toString()));
    }
}