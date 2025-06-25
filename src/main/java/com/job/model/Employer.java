
package com.job.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employers")
public class Employer {

    private Integer id;
    private Integer userId; 

    @NotBlank(message = "Company name cannot be blank")
    @Size(max = 255, message = "Company name must be less than or equal to 255 characters")
    private String companyName;

    @Size(min = 1, message = "Company size must be more than or equal to 1 characters")
    private String companySize;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid website URL format")
    @Size(max = 255, message = "Website URL must be less than or equal to 255 characters")
    private String website;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    private String description;

    @Size(max = 255, message = "Logo URL must be less than or equal to 255 characters")
    private String logoUrl;

    // Constructors
    public Employer() {}

    public Employer(Integer id, Integer userId, String companyName, String companySize, String address, String website, String description, String logoUrl) {
        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.companySize = companySize;
        this.address = address;
        this.website = website;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}