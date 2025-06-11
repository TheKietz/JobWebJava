/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.job.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author 11090
 */
public class SystemSetting {
    private Integer id;

    @NotBlank(message = "Setting key cannot be blank")
    @Size(max = 100, message = "Setting key must be less than or equal to 100 characters")
    private String settingKey;

    @NotBlank(message = "Setting value cannot be blank")
    private String settingValue;

    // Constructors (optional)
    public SystemSetting() {}

    public SystemSetting(Integer id, String settingKey, String settingValue) {
        this.id = id;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSettingKey() { return settingKey; }
    public void setSettingKey(String settingKey) { this.settingKey = settingKey; }
    public String getSettingValue() { return settingValue; }
    public void setSettingValue(String settingValue) { this.settingValue = settingValue; }
}
