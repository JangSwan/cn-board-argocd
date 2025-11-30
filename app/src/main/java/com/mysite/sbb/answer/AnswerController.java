package com.mysite.sbb.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;

// [삭제됨] import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService;

	// [수정] 답변 등록 처리: 하드코딩된 절대 경로 사용
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult) {
		
		Question question = this.questionService.getQuestion(id);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		this.answerService.create(question, answerForm.getContent());
		
		// 최종 리다이렉트 (포트 유지)
		return "redirect:http://localhost:2000/question/detail/" + id;
	}
	
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    // [수정] 답변 수정 처리: 하드코딩된 절대 경로 사용
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
            @PathVariable("id") Integer id) {
    	
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        
        Answer answer = this.answerService.getAnswer(id);
        this.answerService.modify(answer, answerForm.getContent());
        
        // 최종 리다이렉트 (포트 유지)
        return "redirect:http://localhost:2000/question/detail/" + answer.getQuestion().getId();
    }

    // [수정] 답변 삭제 처리: 하드코딩된 절대 경로 사용
    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") Integer id) {
    	
        Answer answer = this.answerService.getAnswer(id);
        Question question = answer.getQuestion();
        this.answerService.delete(answer);
        
        // 최종 리다이렉트 (포트 유지)
        return "redirect:http://localhost:2000/question/detail/" + question.getId();
    }
}
