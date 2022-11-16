package com.fortify.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WispererItem {

    private String sectionName;
    private String itemName;
    private String highlightedText;

}
