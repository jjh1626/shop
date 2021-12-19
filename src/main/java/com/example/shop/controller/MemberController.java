package com.example.shop.controller;

import com.example.shop.model.Code;
import com.example.shop.model.Member;
import com.example.shop.service.CommonService;
import com.example.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CommonService commonService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        List<Code> cityCodes = commonService.findCodes("city");
        List<Code> hobbyCodes = commonService.findCodes("hobby");
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("cityCodes", cityCodes);
        model.addAttribute("hobbyCodes", hobbyCodes);
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Code> cityCodes = commonService.findCodes("city");
            List<Code> hobbyCodes = commonService.findCodes("hobby");
            model.addAttribute("cityCodes", cityCodes);
            model.addAttribute("hobbyCodes", hobbyCodes);
            return "members/createMemberForm";
        }
        Member member = Member.createMember(form.getName()
                                        , form.getCity()
                                        , form.getStreet()
                                        , form.getZipcode()
                                        , form.getGender()
                                        , form.getHobby());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
