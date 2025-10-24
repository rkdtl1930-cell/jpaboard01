package com.kbs.jpademo.repository;

import com.kbs.jpademo.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
@Log4j2
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Test
    public void insertBoard() {
        Board board = Board.builder()
                .title("title2")
                .content("content2")
                .author("작성자1")
                .build();
        boardRepository.save(board);
    }
//    @Test
//    public void findBoardLikeAll() {
//        List<Board> boards = boardRepository.findByTitleAndContent("1");
//        for(Board board : boards){
//            log.info(board.toString());
//        }
//    }
    @Test
    public void SearchBoard(){
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
        Page<Board> result = boardRepository.search1(pageable);
        List<Board> boards = result.getContent();
        for(Board board : boards){
            log.info(board.toString());
        }
        log.info(result.stream().count());
        log.info(result.getSize());
    }
}
