package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void register() {

        Board board = Board.builder().bno(55L).build();

        replyService.register(ReplyDTO.builder()
                .bno(board.getBno())
                .text("연습")
                .replyer("guest").build());

    }

    @Test
    void getList() {

        List<ReplyDTO> result = replyService.getList(55L);

        for (ReplyDTO replyDTO : result) {
            System.out.println("replyDTO = " + replyDTO);
        }
    }

    @Test
    void modify() {

        replyService.modify(ReplyDTO.builder()
                .rno(301L)
                .text("수정")
                .replyer("수정")
                .bno(55L)
                .build());
    }

    @Test
    void remove() {

        replyService.remove(301L);
    }
}