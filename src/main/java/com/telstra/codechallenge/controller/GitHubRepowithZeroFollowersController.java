package com.telstra.codechallenge.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.dto.GitHubRepowithZeroFollowers;
import com.telstra.codechallenge.service.GitHubRepowithZeroFollowersService;

@RestController
@RequestMapping("/api")
@EnableHystrix
public class GitHubRepowithZeroFollowersController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	public GitHubRepowithZeroFollowersService gitHubRepowithZeroFollowersService;

	@RequestMapping(path = "/getRepowithzeroFollowers", method = RequestMethod.GET)
	public ResponseEntity hotestRepo(
			@RequestParam(value = "limit", defaultValue = "0", required = false) String limit) {
		LOGGER.info("---------- In Zero Followeres Controller ---------- ");

		List<GitHubRepowithZeroFollowers> zeroFollowers = gitHubRepowithZeroFollowersService.zeroFollowres(limit);

		if (zeroFollowers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			LOGGER.info("---------- ZeroFollowersService request successfully completed ---------- ",
					zeroFollowers.toString());
			return new ResponseEntity<List<GitHubRepowithZeroFollowers>>(zeroFollowers, HttpStatus.OK);
		}
	}
}
