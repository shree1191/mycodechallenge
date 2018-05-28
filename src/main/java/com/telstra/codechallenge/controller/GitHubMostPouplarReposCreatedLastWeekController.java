package com.telstra.codechallenge.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.dto.GitHubMostPouplarReposCreatedLastWeek;
import com.telstra.codechallenge.service.GitHubMostPouplarReposCreatedLastWeekService;

@RestController
@RequestMapping("/api")
@EnableHystrix
public class GitHubMostPouplarReposCreatedLastWeekController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private GitHubMostPouplarReposCreatedLastWeekService gitHubMostPouplarReposCreatedLastWeekService;

	public GitHubMostPouplarReposCreatedLastWeekController(GitHubMostPouplarReposCreatedLastWeekService gitHubMostPouplarReposCreatedLastWeekService) {
		this.gitHubMostPouplarReposCreatedLastWeekService = gitHubMostPouplarReposCreatedLastWeekService;
	}

	@RequestMapping(path = "/getpopularcreatedrepo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity getpopularcreatedrepoinlastweek(
			@RequestParam(value = "limit", defaultValue = "0", required = false) String limit) {
		
		LOGGER.info("---------- In the hottestcreatedrepo controller ---------- ");

		List<GitHubMostPouplarReposCreatedLastWeek> gitHubMostPouplarReposCreatedLastWeek = gitHubMostPouplarReposCreatedLastWeekService
				.mostPopularRepository(limit);

		if (gitHubMostPouplarReposCreatedLastWeek.isEmpty()) {
			return new ResponseEntity<Object>("No Record found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<GitHubMostPouplarReposCreatedLastWeek>>(gitHubMostPouplarReposCreatedLastWeek, HttpStatus.OK);
	}

}
