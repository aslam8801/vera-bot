package com.magicpin.vera_bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MetadataResponse {

    private String team_name;

    private List<String> team_members;

    private String model;

    private String approach;

    private String contact_email;

    private String version;

    private String submitted_at;

}