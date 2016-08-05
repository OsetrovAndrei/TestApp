
package com.example.andrew.testapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class CategoryResponse<T> {
    @JsonProperty("status")
    public String status;

    @JsonProperty("code")
    public int code;

    @JsonProperty("orderby")
    public String orderby;

    @JsonProperty("categories")
    public T results;

}

