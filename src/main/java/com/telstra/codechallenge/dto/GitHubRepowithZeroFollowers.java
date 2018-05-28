package com.telstra.codechallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data

public class GitHubRepowithZeroFollowers {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String login;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String htmlUrl;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ZeroFollowers [id=" + id + ", login=" + login + ", htmlUrl=" + htmlUrl + "]";
	}

}
