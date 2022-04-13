package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void insertReply() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    void 조회() {

        Optional<Reply> result = replyRepository.findById(1L);
        Reply reply = result.get();

        System.out.println("reply = " + reply);
        System.out.println("reply.getBoard() = " + reply.getBoard());
    }

    @Test
    void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(58L).build());

        replyList.forEach(reply -> System.out.println("reply = " + reply));
    }

}