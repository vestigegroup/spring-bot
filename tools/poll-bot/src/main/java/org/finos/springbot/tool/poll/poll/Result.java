package org.finos.springbot.tool.poll.poll;

import java.util.List;

import org.finos.springbot.symphony.content.HashTag;
import org.finos.springbot.workflow.annotations.Template;
import org.finos.springbot.workflow.annotations.Work;
import org.finos.springbot.workflow.content.User;

@Work
@Template(view="result")
public class Result {

	private HashTag pollID;
	private List<Integer> counts;
	private List<String> options;
	private String question;
	private User poller;

	private Integer totalResponses = 0;
	
	public Result() {
		super();
	}

	public Result(HashTag pollID, List<Integer> counts, List<String> options, String question, User poller,
			Integer totalResponses) {
		super();
		this.pollID = pollID;
		this.counts = counts;
		this.options = options;
		this.question = question;
		this.poller = poller;
		this.totalResponses = totalResponses;
	}



	public Integer getTotalResponses() {
		return totalResponses;
	}

	public void setTotalResponses(Integer totalResponses) {
		this.totalResponses = totalResponses;
	}

	public List<Integer> getCounts() {
		return counts;
	}

	public void setCounts(List<Integer> counts) {
		this.counts = counts;
	}

	public HashTag getPollID() {
		return pollID;
	}

	public void setPollID(HashTag pollID) {
		this.pollID = pollID;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public User getPoller() {
		return poller;
	}

	public void setPoller(User poller) {
		this.poller = poller;
	}
	

}
