package com.fortify.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectionVerification {

    private String sectionName;
    private Integer maxItemCount;

}
