package com.kbs.jpademo.service;


import com.kbs.jpademo.domain.Board;
import com.kbs.jpademo.dto.BoardDTO;
import com.kbs.jpademo.dto.PageRequestDTO;
import com.kbs.jpademo.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {
    Long insertBoard(BoardDTO boardDTO);
    List<BoardDTO> findAllBoards();
    BoardDTO findBoardById(Long bno, int mode);
    void updateBoard(BoardDTO boardDTO);
    void deleteBoardById(Long bno);
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author(boardDTO.getAuthor())
                .build();
        return board;
    }
    default BoardDTO entityToDTO(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .readcount(board.getReadcount())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .build();
        return boardDTO;
    }
}
