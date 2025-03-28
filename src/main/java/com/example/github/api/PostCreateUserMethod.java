package com.example.github.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "https://jsonplaceholder.typicode.com/users", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/github/_post/create_user_rs.json.ftl")
@SuccessfulHttpStatus(status = HttpResponseStatusType.CREATED_201)
public class PostCreateUserMethod extends AbstractApiMethodV2 {

    public PostCreateUserMethod() {
        super();
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));

        String timestamp = String.valueOf(System.currentTimeMillis());

        addProperty("name", "John");
        addProperty("email", "john" + timestamp + "@test.com");
        addProperty("username", "user" + timestamp);
    }
}
