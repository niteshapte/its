package com.discovery.interstellar.transport.system.helper;

import org.junit.Test;

import com.discovery.interstellar.transport.system.helper.ValidationCodes;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;

@Ignore
public class ValidationCodesTest {
    @Test
    public void verifyThatValidationCodesHandlingIsCorrect() throws Exception {
        ValidationCodes expectedCode = ValidationCodes.ROUTE_EXISTS;
        int codeId = ValidationCodes.TRAFFIC_EXISTS.getId();
        ValidationCodes actualMode = ValidationCodes.fromString("ROUTE EXISTS");
        assertThat(expectedCode, sameBeanAs(actualMode));
        assertThat(3, sameBeanAs(codeId));
    }
}