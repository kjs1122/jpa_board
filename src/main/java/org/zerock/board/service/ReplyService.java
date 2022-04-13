package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    // 댓글 등록
    Long register(ReplyDTO replyDTO);
    // 특정 게시물의 댓글 목록
    List<ReplyDTO> getList(Long bno);
    // 댓글 수정
    void modify(ReplyDTO replyDTO);
    // 댓글 삭제
    void remove(Long rno);

    /**
     * ReplyDTO -> Reply
     */
    default Reply dtoToEntity(ReplyDTO replyDTO) {

        Board board = Board.builder().bno(replyDTO.getBno()).build();

        return Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
    }

    /**
     * Reply -> ReplyDTO
     */
    default ReplyDTO entityToDTO(Reply reply) {

       return ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }
}
