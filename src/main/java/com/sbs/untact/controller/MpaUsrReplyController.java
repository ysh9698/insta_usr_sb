package com.sbs.untact.controller;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Reply;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.dto.Rq;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.service.ReplyService;
import com.sbs.untact.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class MpaUsrReplyController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ReplyService replyService;

    @RequestMapping("/mpaUsr/reply/doDelete")
    public String doDelete(HttpServletRequest req, int id, String redirectUri) {
        Reply reply = replyService.getReplyById(id);

        if ( reply == null ) {
            return Util.msgAndBack(req, "존재하지 않는 댓글입니다.");
        }

        Rq rq = (Rq)req.getAttribute("rq");

        if ( reply.getMemberId() != rq.getLoginedMemberId() ) {
            return Util.msgAndBack(req, "권한이 없습니다.");
        }

        ResultData deleteResultData = replyService.delete(id);

        return Util.msgAndReplace(req, deleteResultData.getMsg(), redirectUri);
    }

    @RequestMapping("/mpaUsr/reply/doWrite")
    public String doWrite(HttpServletRequest req, String relTypeCode, int relId, String body, String redirectUri) {
        switch ( relTypeCode ) {
            case "article":
                Article article = articleService.getArticleById(relId);
                if ( article == null ) {
                    return Util.msgAndBack(req, "해당 게시물이 존재하지 않습니다.");
                }
                break;
            default:
                return Util.msgAndBack(req, "올바르지 않은 relTypeCode 입니다.");
        }

        Rq rq = (Rq)req.getAttribute("rq");

        int memberId = rq.getLoginedMemberId();

        ResultData writeResultData = replyService.write(relTypeCode, relId, memberId, body);

        int newReplyId = (int)writeResultData.getBody().get("id");

        redirectUri = Util.getNewUri(redirectUri, "focusReplyId", newReplyId + "");

        return Util.msgAndReplace(req, writeResultData.getMsg(), redirectUri);
    }
}