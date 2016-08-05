
package com.example.andrew.testapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Category extends RealmObject {

    @PrimaryKey
    @Expose
    @JsonProperty("term_id")
    private String termId;

    @Expose
    @JsonProperty("name")
    private String name;

    @Expose
    @JsonProperty("image")
    private String image;

    @Expose
    @JsonProperty("category_link")
    private String categoryLink;

    @Expose
    @JsonProperty("parent")
    private String parent;

    @Expose
    @JsonProperty("business_number")
    private String businessNumber;

    /**
     *
     * @return
     *     The termId
     */
    @JsonProperty("term_id")
    public String getTermId() {
        return termId;
    }

    /**
     *
     * @param termId
     *     The term_id
     */
    @JsonProperty("term_id")
    public void setTermId(String termId) {
        this.termId = termId;
    }

    /**
     *
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The image
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     *     The categoryLink
     */
    @JsonProperty("category_link")
    public String getCategoryLink() {
        return categoryLink;
    }

    /**
     *
     * @param categoryLink
     *     The category_link
     */
    @JsonProperty("category_link")
    public void setCategoryLink(String categoryLink) {
        this.categoryLink = categoryLink;
    }

    /**
     *
     * @return
     *     The parent
     */
    @JsonProperty("parent")
    public String getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     *     The parent
     */
    @JsonProperty("parent")
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     *     The businessNumber
     */
    @JsonProperty("business_number")
    public String getBusinessNumber() {
        return businessNumber;
    }

    /**
     *
     * @param businessNumber
     *     The business_number
     */
    @JsonProperty("business_number")
    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

}
