package com.millenium.falcon;

import com.millenium.falcon.controller.ShootController;
import com.millenium.falcon.model.Target;
import com.millenium.falcon.repository.TargetRepository;
import com.millenium.falcon.service.GunService;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//Could simplify some actions over cleanups
@DirtiesContext
public class ShootInSpaceTest extends AbstractIntegrationTest {
    @Autowired
    private GunService gunService;

    @Autowired
    private TargetRepository repository;

    @Test
    public void serviceTest() {
        String targetName = "Death Star";
        int cordX = 5;
        int cordY = 7;
        assertFalse(repository.existsById(targetName));

        Target target = new Target();
        target.setName(targetName);
        target.setCordX(cordX);
        target.setCordY(cordY);
        gunService.shoot(target);
        proxy.setConnectionCut(true);
        assertTrue(repository.existsById(targetName));
    }

    @Test
    public void httpLayerTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ShootController(gunService)).build();
        String targetName = "Death Star1";
        int cordX = 5;
        int cordY = 8;
        assertFalse(repository.existsById(targetName));

        Target target = new Target();
        target.setName(targetName);
        target.setCordX(cordX);
        target.setCordY(cordY);
        proxy.toxics().latency("shootLatency", ToxicDirection.UPSTREAM, 5000L);
        String requestBody = String.format("{\n" +
                "    \"name\":\"%s\",\n" +
                "    \"cordX\":%s,\n" +
                "    \"cordY\":%s\n" +
                "}", targetName, cordX, cordY);
        mockMvc.perform(post("/shoot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());

        assertTrue(repository.existsById(targetName));
    }

}
