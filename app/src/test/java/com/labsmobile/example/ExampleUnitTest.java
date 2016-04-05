/*
 * Copyright (c) 2016, LabsMobile. All rights reserved.
 */

package com.labsmobile.example;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    public static final String TEMPLATE = "Sample app verification code: \u0025CODE\u0025. Have a nice day!";
public static final String MESSAGE = "Sample app verification code: ASDASD. Have a nice day!";
    public static final String PATTERN = "Sample app verification code: (.*?). Have a nice day!";

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(TEMPLATE);
        System.out.println(TEMPLATE.toCharArray());


        String before = TEMPLATE.substring(0, TEMPLATE.indexOf("%CODE%"));
        String after = TEMPLATE.substring(TEMPLATE.indexOf("%CODE%") + 6);

        System.out.println(before);
        System.out.println(after);


        String code = MESSAGE.substring(before.length(), before.length()+6);
        System.out.println(code);

    }
}