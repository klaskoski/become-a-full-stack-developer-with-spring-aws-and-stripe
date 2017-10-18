package com.training.fullstack;

import com.training.fullstack.web.i18n.I18NService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FullstackApplicationTests {

    @Autowired
    private I18NService i18NService;

    @Test
    public void testMessageByLocaleService() {
        String expectedResult = "Bootstrap starter template";
        String messageId = "index.main.callout";
        String actual = i18NService.getMessage(messageId);
        Assert.assertEquals("String does not match", expectedResult, actual);
    }
}
