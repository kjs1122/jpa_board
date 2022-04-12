package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.service.BoardService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;



    @Test
    void insertBoard() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }
    @Transactional
    @Test
    void testRead1() {

        Board board = boardRepository.findById(100L).get();

        System.out.println("board = " + board);
        System.out.println("board.getWriter() = " + board.getWriter());
    }

    @Test
    void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("=============================");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    void testWithReplyCount() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));


    }

    @Test
    void register() {

        BoardDTO dto = BoardDTO.builder()
                .title("한글 테스트")
                .content("한글 테스트")
                .writerEmail("user55@aaa.com")
                .build();

        Board board = boardService.dtoToEntity(dto);
        System.out.println("board = " + board);
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        System.out.println("member = " + member);

        boardRepository.save(board);
    }


    @Test
    void testSearch1() {

        boardRepository.search1();
    }
    @Test
    void testSearchPage() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").descending());

        boardRepository.searchPage("t", "1", pageable);

    }



}