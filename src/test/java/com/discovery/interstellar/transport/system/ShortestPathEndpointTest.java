package com.discovery.interstellar.transport.system;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.discovery.interstellar.transport.system.ShortestPathEndpoint;
import com.discovery.interstellar.transport.system.ShortestPathRepository;
import com.discovery.interstellar.transport.system.configuration.DatasourceBean;
import com.discovery.interstellar.transport.system.configuration.PersistenceBean;
import com.discovery.interstellar.transport.system.configuration.WebServiceBean;
import com.discovery.interstellar.transport.system.dao.EdgeDao;
import com.discovery.interstellar.transport.system.dao.TrafficDao;
import com.discovery.interstellar.transport.system.dao.VertexDao;
import com.discovery.interstellar.transport.system.schema.GetShortestPathRequest;
import com.discovery.interstellar.transport.system.schema.GetShortestPathResponse;
import com.discovery.interstellar.transport.system.service.EntityManagerService;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatasourceBean.class, PersistenceBean.class, WebServiceBean.class,
        ShortestPathEndpoint.class, ShortestPathRepository.class, EntityManagerService.class, EdgeDao.class, VertexDao.class,
        TrafficDao.class},
        loader = AnnotationConfigContextLoader.class)
@Ignore
public class ShortestPathEndpointTest {

    @Autowired
    private ShortestPathEndpoint shortestPathEndpoint;

    @Test
    public void verifyThatShortestPathSOAPEndPointIsCorrect() throws Exception {
        // Set Up Fixture
        GetShortestPathRequest shortestPathRequest = new GetShortestPathRequest();
        shortestPathRequest.setName("Moon");

        StringBuilder path = new StringBuilder();
        path.append("Earth (A)\tMoon (B)\t");

        GetShortestPathResponse expectedResponse = new GetShortestPathResponse();
        expectedResponse.setPath(path.toString());

        //Test
        GetShortestPathResponse actualResponse = shortestPathEndpoint.getShortestPath(shortestPathRequest);

        // Verify
        assertThat(actualResponse, sameBeanAs(expectedResponse));
        assertThat(actualResponse.getPath(), sameBeanAs("Earth (A)\tMoon (B)\t"));
    }

}