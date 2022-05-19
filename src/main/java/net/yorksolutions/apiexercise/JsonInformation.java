package net.yorksolutions.apiexercise;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonInformation {
    public boolean empty;
    @JsonProperty("object_or_array")
    public String objectOrArray;
    @JsonProperty("parse_time_nanoseconds")
    public int parseTimeNanoseconds;
    public boolean validate;
    public int size;


    public JsonInformation(String json) {
        this.empty = false;
        this.objectOrArray = "object";
        this.parseTimeNanoseconds = 19608;
        this.validate = true;
        this.size = 1;
    }
}
