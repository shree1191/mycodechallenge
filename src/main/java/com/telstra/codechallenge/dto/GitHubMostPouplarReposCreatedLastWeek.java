package com.telstra.codechallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GitHubMostPouplarReposCreatedLastWeek {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String htmlUrl;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String watchersCount;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String language;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String description;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorMessage;

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getWatchersCount() {
		return watchersCount;
	}

	public void setWatchersCount(String watchersCount) {
		this.watchersCount = watchersCount;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
 

	@Override
	public String toString() {
		return "HottestRepoCreatedLastWeek [htmlUrl=" + htmlUrl + ", watchersCount=" + watchersCount + ", language="
				+ language + ", description=" + description + ", name=" + name + "]";
	}

}
