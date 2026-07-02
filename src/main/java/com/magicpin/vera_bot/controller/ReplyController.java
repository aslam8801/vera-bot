package com.magicpin.vera_bot.controller;

import com.magicpin.vera_bot.model.ReplyRequest;
import com.magicpin.vera_bot.model.ReplyResponse;
import com.magicpin.vera_bot.service.ReplyService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply")
    public ReplyResponse reply(@RequestBody ReplyRequest request) {

        String conversationId = request.getConversationId();

        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }

        String aiReply = replyService.generateReply(request);

        return new ReplyResponse(
                conversationId,
                request.getMerchantId(),
                request.getCustomerId(),
                aiReply,
                "reply"
        );
    }
}