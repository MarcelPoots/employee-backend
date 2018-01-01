package nl.rossie.employee.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/rest/users")
public class ElasticsearchController {
	
	@Autowired
    RestClient client;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchController.class);
	
    @GetMapping("/view/{id}")
    public String view(@PathVariable final String id) throws IOException, JSONException {


		final HttpEntity payload = new NStringEntity("", ContentType.APPLICATION_JSON);
		Map<String, String> params = Collections.singletonMap("pretty", "true");

		final Response response = client.performRequest(HttpGet.METHOD_NAME, "/employee/doc/"+ id, params, payload);
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(EntityUtils.toString(response.getEntity()));
			if (jsonObject.has("_source")) {
				return jsonObject.get("_source").toString();
	    	}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
    }
    
    @GetMapping("/insert/{id}")
    public String insert(@PathVariable final String id) throws IOException {
       	
    	//index a document
    	String s = "{\n" +
    	        "    \"username\" : \"user-kimchy\",\n" +
    	        "    \"firstname\" : \"kim\",\n" +
    	        "    \"lastname\" : \"Jong\",\n" +
    	        "    \"gender\" : \"M\"\n" +
    	        "}";
    	System.err.println(s);
    	HttpEntity entity = new NStringEntity(s
    	        , ContentType.APPLICATION_JSON);

    	Response response = client.performRequest(
    	        "PUT",
    	        "/employee/doc/"+ id,
    	        Collections.<String, String>emptyMap(),
    	        entity);
    	
    	return response.toString();
    }
    
}
