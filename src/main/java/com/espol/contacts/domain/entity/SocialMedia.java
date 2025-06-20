package com.espol.contacts.domain.entity;

import com.espol.contacts.domain.entity.enums.SocialPlatform;

import java.io.Serializable;
import java.util.Objects;

public class SocialMedia implements Serializable {
    private final String username;
    private final SocialPlatform platform;

    public SocialMedia(String username, SocialPlatform platform) {
        this.username = username;
        this.platform = platform;
    }

    public String getUsername() {
        return username;
    }

    public SocialPlatform getPlatform() {
        return platform;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SocialMedia that = (SocialMedia) o;
        return Objects.equals(username, that.username) && platform == that.platform;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, platform);
    }

    private static final long serialVersionUID = 3847502938475029384L;
}
