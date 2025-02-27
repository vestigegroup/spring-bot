package org.finos.springbot.teams.content;

import java.io.IOException;
import java.nio.charset.Charset;

import org.finos.springbot.teams.content.serialization.TeamsMarkupWriter;
import org.finos.springbot.teams.response.templating.MarkupAndEntities;
import org.finos.springbot.tests.content.AbstractContentTest;
import org.finos.springbot.workflow.content.Message;
import org.finos.springbot.workflow.content.serialization.MarkupWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { TeamsContentConfig.class, })
public class TeamsContentTest extends AbstractContentTest {

	@Autowired
	TeamsMarkupWriter mw;

	@Test
	public void testTeamsContents() throws IOException {
		// tag def
		doAssertsOnObject(new TeamsMultiwayChat("id123", "Some Chat"), new TeamsMultiwayChat("id123", "Some Chat"));
		doAssertsOnContent(new TeamsUser("id123", "Geoff Z", "aad1243"), new TeamsUser("id123", "Geoff Z","aad1243"));
		doAssertsOnContent(new TeamsChannel("id123", "Geoff Z"), new TeamsChannel("id123", "Geoff Z"));
		doAssertsOnObject(new TeamsConversation("id123;messageId=23485", "Geoff Z"), new TeamsConversation("id123;messageId=23485", "Geoff Z"));
		

		MarkupAndEntities mae = new MarkupAndEntities();
		
		Message m = Message.of(
			new TeamsChannel("aad123", "Big Convo"),
			new TeamsUser("ht123", "Geoff Z", "aad1243"));
		
		String out = mw.apply(m, mae);
		System.out.println(out);
		Assertions.assertEquals(load("testTeamsContents.html"), out);		
		Assertions.assertEquals(2,  mae.getEntities().size());
		
		String jsonOut = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(mae.getEntities());
		System.out.println(jsonOut);
		Assertions.assertEquals(load("testTeamsContents.json"), jsonOut);		
		
	}

	@Test
	public void testWriteContent() throws IOException {
		MarkupAndEntities mae = new MarkupAndEntities();
		String out = mw.apply(createMessage(), mae);
		System.out.println(out);
		Assertions.assertEquals(load("testWriteContent.html"), out);
		Assertions.assertEquals(0,  mae.getEntities().size());
	}

	private String load(String string) throws IOException {
		return StreamUtils
				.copyToString(TeamsContentTest.class.getResourceAsStream(string), Charset.forName("UTF-8"))
				.replace("\r\n", "\n");
	}
}
