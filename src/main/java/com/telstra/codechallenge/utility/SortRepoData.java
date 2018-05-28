package com.telstra.codechallenge.utility;

import java.util.Comparator;

import com.telstra.codechallenge.dto.GitHubMostPouplarReposCreatedLastWeek;
/*
 * Sorting the records best on rating*/
public class SortRepoData implements Comparator<GitHubMostPouplarReposCreatedLastWeek> {
	@Override
	public int compare(GitHubMostPouplarReposCreatedLastWeek o1, GitHubMostPouplarReposCreatedLastWeek o2) {
		if (Integer.parseInt(o1.getWatchersCount()) < Integer.parseInt(o2.getWatchersCount())) {
			return 1;
		} else {
			return -1;
		}
	}

}
