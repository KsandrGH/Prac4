package com.example.phone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneNormalizerTest {

    @Test
    void shouldNormalizePlus7() {
        PhoneNormalizer n = new PhoneNormalizer("1");
        String out = n.normalizeInText("Звоните: +79000000000");
        Assertions.assertEquals("Звоните: +1 (900) 000-00-00", out);
    }

    @Test
    void shouldNormalizeLeading8WithSeparators() {
        PhoneNormalizer n = new PhoneNormalizer("1");
        String out = n.normalizeInText("Контакт: 8(901)111-22-33");
        Assertions.assertEquals("Контакт: +1 (901) 111-22-33", out);
    }

    @Test
    void shouldNotTouchLongId() {
        PhoneNormalizer n = new PhoneNormalizer("1");
        String out = n.normalizeInText("ID: 1234567890123");
        Assertions.assertEquals("ID: 1234567890123", out);
    }
}
