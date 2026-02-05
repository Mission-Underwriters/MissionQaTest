package com.api.pojo;

/**
 * @author myname
 */
public class CreateRecord {
   private String name;
   private  String job;

//Create constructor
    public CreateRecord(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
