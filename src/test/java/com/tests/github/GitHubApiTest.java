package com.tests.github;

import com.example.github.api.*;
import com.zebrunner.carina.api.APIMethodPoller;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class GitHubApiTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @BeforeMethod
    public void setUp() {
        LOGGER.info("Before each test method");
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("After each test method");
    }

    @DataProvider(name = "usernames")
    public Object[][] usernames() {
        return new Object[][]{
                {"octocat"},
                {"mojombo"}
        };
    }

    @Test(dataProvider = "usernames")
    public void testGetUsersWithDataProvider(String username) {
        LOGGER.info("Testing user API with username: {}", username);
        GetUserByUsernameMethod api = new GetUserByUsernameMethod(username);
        api.callAPIExpectSuccess();
        //api.validateResponse();
    }

    @Test()
    public void testGetUserByUsername() {
        LOGGER.info("Testing retrieval of GitHub user by username");
        setCases("GH-101,GH-102");
        GetUserByUsernameMethod api = new GetUserByUsernameMethod("octocat");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test()
    public void testListRepositoriesForUser() {
        LOGGER.info("Testing retrieval of user repositories");
        ListUserReposMethod api = new ListUserReposMethod("octocat");
        api.callAPIExpectSuccess();
        //api.validateResponse();
    }

    @Test()
    public void testGetRepositoryDetails() {
        LOGGER.info("Testing retrieval of repository details");
        GetRepoDetailsMethod api = new GetRepoDetailsMethod("octocat", "boysenberry-repo-1");
        api.callAPIExpectSuccess();
        api.validateResponse();
    }

    @Test()
    @TestPriority(Priority.P1)
    public void testGetRepositoryCommitsWithRetry() {
        LOGGER.info("Testing retrieval of repository commits with retry");
        GetRepoCommitsMethod api = new GetRepoCommitsMethod("octocat", "boysenberry-repo-1");

        AtomicInteger counter = new AtomicInteger(0);

        api.callAPIWithRetry()
                .withLogStrategy(APIMethodPoller.LogStrategy.ALL)
                .peek(response -> counter.getAndIncrement())
                .until(response -> counter.get() == 3)
                .pollEvery(2, ChronoUnit.SECONDS)
                .stopAfter(10, ChronoUnit.SECONDS)
                .execute();

        api.validateResponse();
    }


    @Test()
    public void testSearchRepositories() {
        LOGGER.info("Testing repository search by query");
        SearchReposMethod api = new SearchReposMethod("dtrupenn/Tetris");
        api.callAPIExpectSuccess();
        api.validateResponse();

    }
    @Test
    public void testCreateUserWithFreemarker() {
        LOGGER.info("Testing user creation with Freemarker");

        PostCreateUserMethod post = new PostCreateUserMethod();
        Response response = post.callAPI();

        String jsonStr = response.prettyPrint();
        System.out.println(jsonStr);

        JsonPath json = JsonPath.from(jsonStr);
        Assert.assertEquals(json.get("name"), "John");
        Assert.assertTrue(json.get("email").toString().startsWith("john"));
        Assert.assertTrue(json.get("username").toString().startsWith("user"));
        Assert.assertNotNull(json.get("id"), "Expected 'id' to be present in response");
    }
}
