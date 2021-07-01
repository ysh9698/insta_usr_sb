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

    public Reply getReplyById(int id) {
        return replyDao.getReplyById(id);
    }

    public ResultData delete(int id) {
        replyDao.delete(id);

        return new ResultData("S-1", id + "번 댓글이 삭제되었습니다.", "id", id);
    }

    public ResultData modify(int id, String body) {
        replyDao.modify(id, body);

        return new ResultData("S-1", id + "번 댓글이 수정되었습니다.", "id", id);
    }
}