package com.telstra.codechallenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GitHubMostPouplarReposCreatedLastWeekTest {

	@Value("${GitRepo.base.url}")
	private String GitRepoBaseUrl;

	HttpHeaders headers = new HttpHeaders();
    
	@Autowired
	private RestTemplate restTemplate;

//	@InjectMocks
//	HottestRepoCreatedLastWeekServiceImpl ht;

	@Test
	public void hottestCreatedRepoServiceCheckTest() throws Exception {
		String body = this.restTemplate.getForObject(createURL("/getpopularcreatedrepo?limit=1"), String.class);
		assertThat(body).isNotNull();
	}

//	@Test
//	public void hottestRepository() {
//		List<HottestRepoCreatedLastWeek> mockList = createTest();
//		List<HottestRepoCreatedLastWeek> originalList = ht.hottestRepository("10");
//		assertEquals(mockList, originalList);
//
//	}

	@Test
	public void hottestcreatedrepoJsondatavalidator() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/getpopularcreatedrepo?limit=10"),
				HttpMethod.GET, entity, String.class);

		JSONArray jsonArry = new JSONArray(response.getBody());

		for (int i = 0; i < jsonArry.length(); i++) {
			JSONObject responseObject = jsonArry.getJSONObject(i);
			assertNotNull(responseObject);
			assertNotNull(responseObject.get("watchersCount"));
			assertNotNull(responseObject.get("htmlUrl"));
			assertNotNull(responseObject.get("name"));
			assertNotNull(responseObject.get("description"));
			assertNotNull(responseObject.get("language"));
		}
	}

	@Test
	public void limitvalidator() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/getpopularcreatedrepo?limit=AA"),
				HttpMethod.GET, entity, String.class);

		JSONArray jsonArry = new JSONArray(response.getBody());

		for (int i = 0; i < jsonArry.length(); i++) {
			JSONObject responseObject = jsonArry.getJSONObject(i);
			assertNotNull(responseObject);
			assertNotNull(responseObject.get("errorMessage"));
			assertEquals(responseObject.get("errorMessage"),"Please enter valid positive interger value for Limit parameter.");
		}
	}

	// return rest API URL
	private String createURL(String uri) {
		return GitRepoBaseUrl + uri;
	}

}
