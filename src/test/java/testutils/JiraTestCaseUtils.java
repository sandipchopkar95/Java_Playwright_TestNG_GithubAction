package testutils;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JiraTestCaseUtils {

    public static void attachJiraTestId(Method method, ExtentTest test) {
        try {
            List<String> descriptions = new ArrayList<>();

            // Check if @Test annotation is present
            if (method.isAnnotationPresent(Test.class)) {
                Test testAnnotation = method.getAnnotation(Test.class);
                String description = testAnnotation.description();
                if (description != null && !description.trim().isEmpty()) {
                    descriptions.add(description);
                }
            }

            // Check for other custom annotations (example: @AdditionalDescription)
            if (method.isAnnotationPresent(AdditionalDescriptions.class)) {
                AdditionalDescriptions additionalDescription = method.getAnnotation(AdditionalDescriptions.class);
                String[] additionalDescriptions = additionalDescription.value();
                for (String desc : additionalDescriptions) {
                    if (desc != null && !desc.trim().isEmpty()) {
                        descriptions.add(desc);
                    }
                }
            }

            // If no descriptions are found, log a warning
            if (descriptions.isEmpty()) {
                throw new IllegalArgumentException("No description provided for the test: " + method.getName());
            }

            // Create Jira links for all descriptions
            for (String description : descriptions) {
                String getTicketPath = "https://truvideo.atlassian.net/browse/" + description;
                test.info("Test Case : <a href='" + getTicketPath + "' target='_blank'>" + description + "</a>");
                test.assignCategory(description);
            }
        } catch (Exception e) {
            // Log the exception or handle it gracefully
            test.info("Failed to attach Jira Test IDs: " + e.getMessage());
        }
    }

}
