package ro.msg.learning.shop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.ProductRepository;


import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShopApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
@ActiveProfiles("test")
public class SingleLocationIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUpTest() throws Exception {
        mvc.perform(post("/populate")).andExpect(status().isOk());
    }

    @After
    public void tearDownTest() throws Exception {
        mvc.perform(delete("/clear")).andExpect(status().isOk());
    }

    @Test
    public void testSuccess() {
        Customer customer = customerRepository.findAll().get(0);

        Product product = productRepository.findAll().get(0);

        try {
            JSONArray array = new JSONArray();
            array.put(new JSONObject().put("productId", product.getId()).put("quantity", 1));
            String jsonString = new JSONObject()
                    .put("customerId", customer.getId())
                    .put("addressCountry", "romania")
                    .put("addressCity", "bucharest")
                    .put("addressCounty", "bucharest")
                    .put("addressStreetAddress", "bdj")
                    .put("orderDetailDtoSaveList", array)
                    .toString();
            mvc.perform(post("/orders").content(jsonString)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void testProductNotFound() {
        Customer customer = customerRepository.findAll().get(0);

        try {
            JSONArray array = new JSONArray();
            array.put(new JSONObject().put("productId", 12000).put("quantity", 1));
            String jsonString = new JSONObject()
                    .put("customerId", customer.getId())
                    .put("addressCountry", "romania")
                    .put("addressCity", "bucharest")
                    .put("addressCounty", "bucharest")
                    .put("addressStreetAddress", "bdj")
                    .put("orderDetailDtoSaveList", array)
                    .toString();
            mvc.perform(post("/orders").content(jsonString)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ProductException productException) {
            assertFalse(productException.getMessage().isEmpty());
        } catch (Exception exception) {
            fail();
        }
    }
}
