package com.telstra.codechallenge.service;

import java.util.List;

import com.telstra.codechallenge.dto.GitHubRepowithZeroFollowers;

public interface GitHubRepowithZeroFollowersService {

	List<GitHubRepowithZeroFollowers> zeroFollowres(String limit);

}
