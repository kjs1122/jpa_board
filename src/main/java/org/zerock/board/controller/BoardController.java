package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(
            @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
            Model model) {

        log.info("list................." + pageRequestDTO);

        model.addAttribute("result", boardService.getList(pageRequestDTO));

        return "board/list";
    }

    @GetMapping("/register")
    public String register(
            @ModelAttribute("dto") BoardDTO dto) {

        log.info("register get...............");
        return "board/register";
    }

    @PostMapping("/register")
    public String registerPost(
            @Validated @ModelAttribute("dto") BoardDTO dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!dto.getWriterEmail().contains("@")) {
            bindingResult.reject("emailType");
        }
        if (bindingResult.hasErrors()) {
            return "board/register";
        }

        log.info("dto ...........{}", dto);

        Long bno = boardService.register(dto);
        log.info("bno..........{}", bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public void read(
            @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
            Long bno,
            Model model) {

        log.info("bno : {}", bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
    }
    @GetMapping("/modify")
    public String modify(
            @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
            Long bno,
            Model model) {

        log.info("bno : {}", bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info(boardDTO);

        model.addAttribute("dto", boardDTO);
        return "board/modify";
    }

    @PostMapping("/remove")
    public String remove(
            Long bno,
            RedirectAttributes redirectAttributes) {

        boardService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(
            BoardDTO dto,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes redirectAttributes) {

        boardService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addFlashAttribute("msg", dto.getBno());
        return "redirect:/board/list";
    }
}
