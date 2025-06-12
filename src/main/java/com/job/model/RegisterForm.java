
package com.job.model;

import com.job.enums.CommonEnums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterForm {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên phải dưới 100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    @Size(max = 100, message = "Email phải dưới 100 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 đến 255 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-]+$", message = "Password must not contain accented characters or special symbols.")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    @Size(min = 6, max = 255, message = "Xác nhận mật khẩu phải từ 6 đến 255 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=-]+$", message = "Password must not contain accented characters or special symbols.")
    private String passwordConfirm;

    @Size(max = 20, message = "Số điện thoại phải dưới 20 ký tự")
    private String phone;

    public RegisterForm() {}

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
