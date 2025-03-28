package com.example.github.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/search/repositories?q=${query}", methodType = HttpMethodType.GET)
@ResponseTemplatePath(path = "api/github/_get/search_repos_rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class SearchReposMethod extends AbstractApiMethodV2 {

    public SearchReposMethod(String query) {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        replaceUrlPlaceholder("query", query);
    }
}
