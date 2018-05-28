package com.telstra.codechallenge.service;

import java.util.List;

import com.telstra.codechallenge.dto.GitHubMostPouplarReposCreatedLastWeek;

public interface GitHubMostPouplarReposCreatedLastWeekService {

	List<GitHubMostPouplarReposCreatedLastWeek> mostPopularRepository(String limit);

}
