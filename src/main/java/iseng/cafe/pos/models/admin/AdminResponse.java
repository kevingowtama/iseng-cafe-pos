package iseng.cafe.pos.models.admin;

import iseng.cafe.pos.models.adminType.AdminTypeResponse;

public class AdminResponse {
    private Integer id;

    private String name;

    private String email;

    private String username;

    private String password;

    private AdminTypeResponse adminType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminTypeResponse getAdminType() {
        return adminType;
    }

    public void setAdminType(AdminTypeResponse adminType) {
        this.adminType = adminType;
    }
}
