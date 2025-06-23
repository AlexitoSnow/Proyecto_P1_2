package com.espol.contacts.domain.entity.enums;

import com.espol.contacts.config.constants.Icons;

public enum SocialPlatform {
    LinkedIn(Icons.LINKEDIN),
    Instagram(Icons.INSTAGRAM),
    Tiktok(Icons.TIKTOK),
    Facebook(Icons.FACEBOOK),
    X(Icons.X),
    Otro(Icons.COMMENT);

    private final String iconCode;

    SocialPlatform(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getIcon() {
        return iconCode;
    }
}
