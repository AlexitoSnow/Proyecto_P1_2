package com.espol.contacts.domain.entity.enums;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome6.FontAwesomeBrands;

import static org.kordamp.ikonli.material2.Material2AL.COMMENT;

public enum SocialPlatform {
    LINKEDIN(FontAwesomeBrands.LINKEDIN),
    INSTAGRAM(FontAwesomeBrands.INSTAGRAM),
    TIKTOK(FontAwesomeBrands.TIKTOK),
    FACEBOOK(FontAwesomeBrands.FACEBOOK),
    X(FontAwesomeBrands.TWITTER),
    OTRO(COMMENT);

    private final Ikon iconCode;

    SocialPlatform(Ikon iconCode) {
        this.iconCode = iconCode;
    }

    public Ikon getIcon() {
        return iconCode;
    }
}
