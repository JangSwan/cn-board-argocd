package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.answer.AnswerForm;

// [삭제됨] import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail (Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	// [수정] 질문 등록 처리: 하드코딩된 절대 경로 사용
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) { 
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		// 최종 리다이렉트 (포트 유지)
		return "redirect:http://localhost:2000/question/list"; 
	}
	
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    // [수정] 질문 수정 처리: 하드코딩된 절대 경로 사용
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, 
            @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        
        // 최종 리다이렉트 (포트 유지)
        return "redirect:http://localhost:2000/question/detail/" + id;
    }

    // [수정] 질문 삭제 처리: 하드코딩된 절대 경로 사용 및 /sbb로 이동
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        this.questionService.delete(question);
        
        // 최종 리다이렉트 (포트 유지)
        return "redirect:http://localhost:2000/sbb";
    }
    
    // [삭제됨] getRedirectUrl 헬퍼 메서드 제거
}
