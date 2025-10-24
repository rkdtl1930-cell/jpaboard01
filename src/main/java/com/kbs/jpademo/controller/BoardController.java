package com.kbs.jpademo.controller;

import com.kbs.jpademo.dto.BoardDTO;
import com.kbs.jpademo.dto.PageRequestDTO;
import com.kbs.jpademo.dto.PageResponseDTO;
import com.kbs.jpademo.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @GetMapping("register")
    public void registerGet(){
        log.info("registerGet");
    }
    @PostMapping("register")
    public String registerPost(BoardDTO boardDTO){
        log.info("registerPost");
        Long bno = boardService.insertBoard(boardDTO);
        log.info("board insert success : bno="+bno);
        return "redirect:/board/list";
    }
    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<BoardDTO> responseDTO = boardService.getList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
        model.addAttribute("pageRequestDTO",pageRequestDTO);
    }
//    @GetMapping("list")
    public void list(Model model){
        log.info("list");
        model.addAttribute("boards",boardService.findAllBoards());
    }
    @GetMapping({"read","modify"})
    public void read_modifyBoard(Long bno, int mode, PageRequestDTO pageRequestDTO, Model model){
        log.info("read_modifyBoard");
         model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("board",boardService.findBoardById(bno, mode));
    }
    @PostMapping("modify")
    public String modifyPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        log.info("modifyPost"+boardDTO);
        boardService.updateBoard(boardDTO);
        redirectAttributes.addAttribute("bno",boardDTO.getBno());
        redirectAttributes.addAttribute("mode", 2);
        return "redirect:/board/read";
    }
    @GetMapping("remove")
    public String removePost(Long bno){
        log.info("removePost");
        boardService.deleteBoardById(bno);
        return "redirect:/board/list";
    }

}
