package com.sbs.untact.service;

import com.sbs.untact.dao.MemberDao;
import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    public Member getMemberByLoginId(String loginId) {
        return memberDao.getMemberByLoginId(loginId);
    }

    public ResultData join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
        memberDao.join(loginId, loginPw, name, nickname, cellphoneNo, email);
        int id = memberDao.getLastInsertId();

        return new ResultData("S-1", "회원가입이 완료되었습니다.", "id", id);
    }

    public Member getMemberById(int id) {
        return memberDao.getMemberById(id);
    }

    public Member getMemberByNameAndEmail(String name, String email) {
        return memberDao.getMemberByNameAndEmail(name, email);
    }
}