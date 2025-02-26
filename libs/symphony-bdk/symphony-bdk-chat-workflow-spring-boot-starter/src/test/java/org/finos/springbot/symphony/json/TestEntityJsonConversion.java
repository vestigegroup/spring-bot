package org.finos.springbot.symphony.json;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.finos.springbot.entityjson.EntityJson;
import org.finos.springbot.symphony.content.HashTag;
import org.finos.springbot.symphony.content.SymphonyRoom;
import org.finos.springbot.symphony.content.SymphonyUser;
import org.finos.springbot.symphony.data.SymphonyDataHandlerCofig;
import org.finos.springbot.workflow.data.EntityJsonConverter;
import org.finos.springbot.workflow.tags.HeaderDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { 
		SymphonyDataHandlerCofig.class, 
})
public class TestEntityJsonConversion {
	
	public static final String WORKFLOW_001 = "workflow_001";

	@Autowired
	EntityJsonConverter converter;
	
	ObjectMapper om = new ObjectMapper();

	public Object readWorkflowValue(String json) {
		try {
			if (json == null) {
				return null;
			}

			return converter.readValue(json).get("workflow_001");
		} catch (Exception e) {
			System.out.println(json);
			throw new UnsupportedOperationException("Map Fail", e);
		}
	}

	/** 
	 * Used in tests 
	 */
	public String toWorkflowJson(Object o) {
		try {
			if (o == null) {
				return null;
			}
			EntityJson out = new EntityJson();
			out.put(WORKFLOW_001, o);
			return converter.writeValue(out);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Map Fail", e);
		}
	}
	
	@Test
	public void testBean() throws Exception {

		TestBean a = new TestBean("83274239874", true, true, "rob@example.com", 234786, 2138);

		// can we convert to messageML? (something populated)
		String out = toWorkflowJson(a);
	
		compare(out, "{\"workflow_001\":{\"type\":\"org.finos.springbot.symphony.json.testBean\",\"version\":\"1.0\",\"isin\":\"83274239874\",\"bidAxed\":true,\"askAxed\":true,\"creator\":\"rob@example.com\",\"bidQty\":234786,\"askQty\":2138}}");
		
		TestBean b = (TestBean) readWorkflowValue(out);
		Assertions.assertEquals(a, b);
	}

	private void compare(String out, String expected) throws JsonProcessingException, JsonMappingException {
		JsonNode joOut = om.readTree(out);
		JsonNode joExpected = om.readTree(expected);

		System.out.println("expected: "+new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(joExpected));
		System.out.println("actual  : "+new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(joOut));
		
		Assertions.assertEquals(joOut, joExpected);
	}

	@Test
	public void testBeans() throws Exception {

		TestBean a1 = new TestBean("83274239874", true, true, "rob@example.com", 234786, 2138);
		TestBean a2 = new TestBean("AUD274239874", true, false, "gregb@example.com", 2386, new BigDecimal("234823498.573"));

		TestBeans a = new TestBeans(Arrays.asList(a1, a2));

		String out = toWorkflowJson(a);
		compare("{\"workflow_001\":{\"type\":\"org.finos.springbot.symphony.json.testBeans\",\"version\":\"1.0\",\"items\":[{\"type\":\"org.finos.springbot.symphony.json.testBean\",\"version\":\"1.0\",\"isin\":\"83274239874\",\"bidAxed\":true,\"askAxed\":true,\"creator\":\"rob@example.com\",\"bidQty\":234786,\"askQty\":2138},{\"type\":\"org.finos.springbot.symphony.json.testBean\",\"version\":\"1.0\",\"isin\":\"AUD274239874\",\"bidAxed\":true,\"askAxed\":false,\"creator\":\"gregb@example.com\",\"bidQty\":2386,\"askQty\":234823498.573}]}}",
				out);

		TestBeans b = (TestBeans) readWorkflowValue(out);
		Assertions.assertEquals(a, b);
	}
	
	@Test
	public void testLegacyLoad() throws Exception {
		String toLoad = StreamUtils.copyToString(TestEntityJsonConversion.class.getResourceAsStream("legacyJsonFormat.json"), StandardCharsets.UTF_8);
		EntityJson ej = converter.readValue(toLoad);
		
		HeaderDetails hd = (HeaderDetails) ej.get(HeaderDetails.KEY);
		Assertions.assertEquals(3, hd.getTags().size());
		Assertions.assertEquals("symphony-workflow", hd.getTags().get(0));
	}

	@Test
	public void testOb3() throws Exception {

		EJTestObject a1 = new EJTestObject(new SymphonyRoom("abc", "123"), new SymphonyUser("Robert Moffat", "rbo@kjite9.com"), "SOme message");
		String out =  toWorkflowJson(a1);

		compare(out, "{\n"
				+ "  \"workflow_001\" : {\n"
				+ "    \"type\" : \"org.finos.symphony.toolkit.workflow.fixture.eJTestObject\",\n"
				+ "    \"version\" : \"1.0\",\n"
				+ "    \"r\" : {\n"
				+ "      \"type\" : \"org.finos.symphony.toolkit.workflow.content.chat\",\n"
				+ "      \"version\" : \"1.0\",\n"
				+ "      \"id\" : [ {\n"
				+ "        \"type\" : \"com.symphony.user.streamID\",\n"
				+ "        \"version\" : \"1.0\",\n"
				+ "        \"value\" : \"123\"\n"
				+ "      }, {\n"
				+ "        \"type\" : \"org.finos.symphony.toolkit.workflow.sources.symphony.content.roomName\",\n"
				+ "        \"version\" : \"1.0\",\n"
				+ "        \"value\" : \"abc\"\n"
				+ "      } ]\n"
				+ "    },\n"
				+ "    \"u\" : {\n"
				+ "      \"type\" : \"com.symphony.user.mention\",\n"
				+ "      \"version\" : \"1.0\",\n"
				+ "      \"id\" : [ null, {\n"
				+ "        \"type\" : \"com.symphony.user.displayName\",\n"
				+ "        \"version\" : \"1.0\",\n"
				+ "        \"value\" : \"Robert Moffat\"\n"
				+ "      }, {\n"
				+ "        \"type\" : \"com.symphony.user.emailAddress\",\n"
				+ "        \"version\" : \"1.0\",\n"
				+ "        \"value\" : \"rbo@kjite9.com\"\n"
				+ "      } ]\n"
				+ "    },\n"
				+ "    \"someText\" : \"SOme message\"\n"
				+ "  }\n"
				+ "}");
		EJTestObject b = (EJTestObject) readWorkflowValue(out);
		Assertions.assertEquals(a1, b);
	}

}
