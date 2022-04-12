package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.BoardRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com")
                .build();

        boardService.register(dto);
    }

    @Test
    void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println("boardDTO = " + boardDTO);
        }
    }

    @Test
    void testGet() {

        BoardDTO boardDTO = boardService.get(100L);
        System.out.println("boardDTO = " + boardDTO);
    }
    @Test
    void testDelete() {

       boardService.removeWithReplies(100L);
    }

    @Test
    void testModify() {

        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        boardService.modify(dto);
    }
}