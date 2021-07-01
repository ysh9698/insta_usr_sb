package com.sbs.untact.dto;

import com.sbs.untact.util.Util;
import lombok.Getter;

import java.util.Map;

public class Rq {
    private String currentUrl;
    @Getter
    private String currentUri;
    private Member loginedMember;
    private Map<String, String> paramMap;
    @Getter
    private boolean needToChangePassword;

    public Rq(Member loginedMember, String currentUri, Map<String, String> paramMap, boolean needToChangePassword) {
        this.loginedMember = loginedMember;
        this.currentUrl = currentUri.split("\\?")[0];
        this.currentUri = currentUri;
        this.paramMap = paramMap;
        this.needToChangePassword = needToChangePassword;
    }

    public String getParamJsonStr() {
        return Util.toJsonStr(paramMap);
    }

    public boolean isLogined() {
        return loginedMember != null;
    }

    public boolean isNotLogined() {
        return isLogined() == false;
    }

    public int getLoginedMemberId() {
        if (isNotLogined()) return 0;

        return loginedMember.getId();
    }

    public Member getLoginedMember() {
        return loginedMember;
    }

    public String getEncodedCurrentUri() {
        return Util.getUriEncoded(getCurrentUri());
    }

    public String getCurrentUri() {
        return currentUri;
    }

    public String getLoginPageUri() {
        String afterLoginUri;

        if (isLoginPage()) {
            afterLoginUri = Util.getUriEncoded(paramMap.get("afterLoginUri"));
        } else {
            afterLoginUri = getEncodedCurrentUri();
        }

        return "../member/login?afterLoginUri=" + afterLoginUri;
    }

    private boolean isLoginPage() {
        return currentUrl.equals("/mpaUsr/member/login");
    }
}