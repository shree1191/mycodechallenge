package com.telstra.codechallenge.serviceImpl;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.telstra.codechallenge.dto.GitHubMostPouplarReposCreatedLastWeek;
import com.telstra.codechallenge.service.GitHubMostPouplarReposCreatedLastWeekService;
import com.telstra.codechallenge.utility.DateUtility;
import com.telstra.codechallenge.utility.SortRepoData;

@Service
public class GitHubMostPouplarReposCreatedLastWeekServiceImpl implements GitHubMostPouplarReposCreatedLastWeekService {

	@Value("${GitRepo.base.url}")
	private String GitRepoBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public GitHubMostPouplarReposCreatedLastWeekServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false") }, fallbackMethod = "reliable")
	public List<GitHubMostPouplarReposCreatedLastWeek> mostPopularRepository(String limit) {

		LOGGER.info("---------- In the HottestRepoCreatedLastWeekService ---------- ");

		GitHubMostPouplarReposCreatedLastWeek hottestRepoCreatedLastWeek = null;
		JSONObject jsonObject = null;
		JSONArray arrayObject = null;

		int num = 0;
		List<GitHubMostPouplarReposCreatedLastWeek> hottestRepoList = new ArrayList<GitHubMostPouplarReposCreatedLastWeek>();

		String data = restTemplate.getForObject(GitRepoBaseUrl + "repositories?q=created:",
				String.class);
		try {
			jsonObject = new JSONObject(data);
			arrayObject = jsonObject.getJSONArray("items");
		} catch (JSONException e) {
			LOGGER.error("JSON Exception : " + e.getMessage());
		}

		if (arrayObject != null) {
			for (int i = 0; i < arrayObject.length(); i++) {
				try {
					// get Data from API and assign to local variable
					String createdAt = arrayObject.getJSONObject(i).getString("updated_at");
					String watchersCount = arrayObject.getJSONObject(i).getString("watchers_count");
					String language = arrayObject.getJSONObject(i).getString("language");
					String description = arrayObject.getJSONObject(i).getString("description");
					String name = arrayObject.getJSONObject(i).getString("name");
					String htmlURL = arrayObject.getJSONObject(i).getString("html_url");

					// get Formatted date , convert string formatted date into Date Formatted
					Date repoCreatedDate = DateUtility.getFormattedDate(createdAt);
					/* get date of previous 7th day date */
					Date lastWeekRecords = DateUtility.getPreviousSevenDaysdate();

					/*
					 * get the records by compare current date to previous 7th day date
					 */
					if (repoCreatedDate.after(lastWeekRecords)) {
						// set the API date to POJO object
						hottestRepoCreatedLastWeek = new GitHubMostPouplarReposCreatedLastWeek();
						hottestRepoCreatedLastWeek.setHtmlUrl(htmlURL);
						hottestRepoCreatedLastWeek.setName(name);
						hottestRepoCreatedLastWeek.setLanguage(language);
						hottestRepoCreatedLastWeek.setDescription(description);
						hottestRepoCreatedLastWeek.setWatchersCount(watchersCount);

						// store POJO object in List
						hottestRepoList.add(hottestRepoCreatedLastWeek);

						// Apply sorting descending order
						Collections.sort(hottestRepoList, new SortRepoData());
					}
				} catch (JSONException e) {
					LOGGER.error("JSON object is empty or null :" + e.getMessage());

				}

			}
		} else {
			LOGGER.error("JSON Object Array is null");
		}
		/*
		 * 
		 * Check limit value limit should be in number format and positive integre if
		 * not then display error
		 * 
		 */
		try {
			num = Integer.parseUnsignedInt(limit);
			if (num != 0) {
				return hottestRepoList.subList(0, num);
			} else {
				return hottestRepoList;
			}

		} catch (NumberFormatException e) {
			LOGGER.error("Limit must be number value : " + e.getMessage());

			List<GitHubMostPouplarReposCreatedLastWeek> numberFormatErrorMessages = new ArrayList<GitHubMostPouplarReposCreatedLastWeek>();
			GitHubMostPouplarReposCreatedLastWeek limitValueError = new GitHubMostPouplarReposCreatedLastWeek();

			limitValueError.setErrorMessage("Please enter valid positive interger value for Limit parameter.");

			numberFormatErrorMessages.add(limitValueError);

			return numberFormatErrorMessages;

		}

	}

	/*
	 * this method for netflix fallback method
	 */

	public List<GitHubMostPouplarReposCreatedLastWeek> reliable(String limit) {
		LOGGER.info("----------- In netflix relible method  ----------------");
		List<GitHubMostPouplarReposCreatedLastWeek> fallbackObject = new ArrayList<GitHubMostPouplarReposCreatedLastWeek>();
		GitHubMostPouplarReposCreatedLastWeek fallbackMessage = new GitHubMostPouplarReposCreatedLastWeek();
		fallbackMessage.setErrorMessage("Server is down !! please try after some time");
		fallbackObject.add(fallbackMessage);
		return fallbackObject;
	}
}
