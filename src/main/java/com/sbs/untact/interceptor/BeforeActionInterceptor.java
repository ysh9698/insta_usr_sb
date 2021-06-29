package com.sbs.untact.interceptor;

import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.Rq;
import com.sbs.untact.service.MemberService;
import com.sbs.untact.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
@Slf4j
public class BeforeActionInterceptor implements HandlerInterceptor {
    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

        Map<String, String> paramMap = Util.getParamMap(req);

        HttpSession session = req.getSession();

        Member loginedMember = null;
        int loginedMemberId = 0;

        if (session.getAttribute("loginedMemberId") != null) {
            loginedMemberId = (int) session.getAttribute("loginedMemberId");
        }

        if (loginedMemberId != 0) {
            String loginedMemberJsonStr = (String) session.getAttribute("loginedMemberJsonStr");

            loginedMember = Util.fromJsonStr(loginedMemberJsonStr, Member.class);
        }

        String currentUri = req.getRequestURI();
        String queryString = req.getQueryString();

        if (queryString != null && queryString.length() > 0) {
            currentUri += "?" + queryString;
        }

        boolean needToChangePassword = false;

        if (loginedMember != null) {
            if (session.getAttribute("needToChangePassword") == null) {
                needToChangePassword = memberService.needToChangePassword(loginedMember.getId());

                session.setAttribute("needToChangePassword", needToChangePassword);
            }

            needToChangePassword = (boolean) session.getAttribute("needToChangePassword");
        }

        req.setAttribute("rq", new Rq(loginedMember, currentUri, paramMap, needToChangePassword));

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}