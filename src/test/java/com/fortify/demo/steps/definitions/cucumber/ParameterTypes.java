package com.fortify.demo.steps.definitions.cucumber;

import com.fortify.demo.domain.SectionVerification;
import io.cucumber.java.DataTableType;
import java.util.Map;

public class ParameterTypes {

    /**
     * <ul>
     *     <li>sectionName</li> a name of the section in the wisperer table
     *     <li>maxCount</li> a maximum number of displayed items under the section
     * </ul>
     *
     * @param entry input data per each row
     * @return grouped data to object
     */
    @DataTableType
    public SectionVerification sectionVerification(Map<String, String> entry) {
        return SectionVerification.builder()
            .sectionName(entry.get("sectionName"))
            .maxItemCount(Integer.parseInt(entry.get("maxCount")))
            .build();
    }

}
