package com.ogoma.hr_core.boundaries.project_management.models;

public interface ClientViewModel {
    public Long getId();
    public String getCompanyName();
    //contact person
    public  String getFirstName();
    //contact person
    public String getLastName();
    public String getPhone();
    public String getEmail();
    default String getFullName() {
        String lastName = null;
        if (null != getLastName()) {
            lastName = getLastName();
        }
        if (null != getFirstName()) {
            return getFirstName().concat(" ").concat(lastName);
        }
        return "";
    }
}
