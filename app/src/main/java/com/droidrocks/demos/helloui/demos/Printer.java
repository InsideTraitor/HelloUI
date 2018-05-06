package com.droidrocks.demos.helloui.demos;

/**
 * Created by hollis on 5/6/18.
 */

public class Printer extends ReferenceDataTypes {

    String[] jobs = new String[10];

    public Printer() {

    }

    public String[] getJobs() {
        return this.jobs;
    }

    public void setJobs(String[] newJobList) {
        this.jobs = newJobList;
    }

    public void setJob(String string) {
        this.jobs[9] = string;
    }
}
