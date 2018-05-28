package com.telstra.codechallenge.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class GitHubRepowithZeroFollowersTest {

	@Value("${GitRepo.base.url}")
	private String GitRepoBaseUrl;

	HttpHeaders headers = new HttpHeaders();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void zeroFollowersServiceCheckTest() throws Exception {
		String body = this.restTemplate.getForObject(createURL("/getRepowithzeroFollowers?limit=1"), String.class);
		assertThat(body).isNotNull();

	}
	
	@Test
	public void zeroFollowersJsondatavalidator() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/getRepowithzeroFollowers?limit=10"),
				HttpMethod.GET, entity, String.class);

		JSONArray jsonArry = new JSONArray(response.getBody());

		for (int i = 0; i < jsonArry.length(); i++) {
			JSONObject responseObject = jsonArry.getJSONObject(i);
			assertNotNull(responseObject);
			assertNotNull(responseObject.get("id"));
			assertNotNull(responseObject.get("htmlUrl"));
			assertNotNull(responseObject.get("login"));			
		}
	}
	
	@Test
	public void limitvalidator() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/getRepowithzeroFollowers?limit=AA"),
				HttpMethod.GET, entity, String.class);

		JSONArray jsonArry = new JSONArray(response.getBody());

		for (int i = 0; i < jsonArry.length(); i++) {
			JSONObject responseObject = jsonArry.getJSONObject(i);
			assertNotNull(responseObject);
			assertNotNull(responseObject.get("errorMessage"));
			assertEquals(responseObject.get("errorMessage"),"Please enter valid positive interger value for Limit parameter.");
		}
	}


	@Test
	public void zeroFollowersResponseCheckTest() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/getRepowithzeroFollowers?limit=1"), HttpMethod.GET,
				entity, String.class);

		String expected = "[{\"id\":\"44\",\"login\":\"errfree\",\"htmlUrl\":\"https://github.com/errfree\"}]";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	// return rest API URL
	private String createURL(String uri) {
		return GitRepoBaseUrl + uri;
	}

}
