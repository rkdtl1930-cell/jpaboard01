package com.kbs.jpademo.service;

import com.kbs.jpademo.domain.Board;
import com.kbs.jpademo.dto.BoardDTO;
import com.kbs.jpademo.dto.PageRequestDTO;
import com.kbs.jpademo.dto.PageResponseDTO;
import com.kbs.jpademo.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Override
    public Long insertBoard(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO); // 저장된 entity
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public List<BoardDTO> findAllBoards() {
        List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "bno"));
        List<BoardDTO> dtos = new ArrayList<>();
        for (Board board : boards) {
            dtos.add(entityToDTO(board));
        }
        return dtos;
    }

    @Override
    public BoardDTO findBoardById(Long bno, int mode) {
        Board board = boardRepository.findById(bno).orElse(null);
        if(mode == 1){
            board.updateReadcount();
            boardRepository.save(board);
        }
        return entityToDTO(board);
    }

    @Override
    public void updateBoard(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBno()).orElse(null);
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoardById(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("bno");
//        Page<Board> result = boardRepository.findAll(pageable);
//        Page<Board> result = boardRepository.findkeyword(pageRequestDTO.getKeyword(), pageable);
        Page<Board> result = boardRepository.searchAll(
                pageRequestDTO.getTypes(), pageRequestDTO.getKeyword(), pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());
        int total = (int)result.getTotalElements();
        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withALl()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
        return responseDTO;
    }
}
