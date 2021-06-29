package com.sbs.untact.service;

import com.sbs.untact.dao.ReplyDao;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyDao replyDao;

    public ResultData write(String relTypeCode, int relId, int memberId, String body) {
        replyDao.write(relTypeCode, relId, memberId, body);
        int id = replyDao.getLastInsertId();

        return new ResultData("S-1", "댓글이 작성되었습니다.", "id", id);
    }

    public List<Reply> getForPrintRepliesByRelTypeCodeAndRelId(String relTypeCode, int relId) {
        return replyDao.getForPrintRepliesByRelTypeCodeAndRelId(relTypeCode, relId);
    }
}