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
import org.springframework.web.bind.annotation.PathVariable;
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
        List<Code> genderCodes = commonService.findCodes("gender");
        List<Code> hobbyCodes = commonService.findCodes("hobby");
        model.addAttribute("cityCodes", cityCodes);
        model.addAttribute("genderCodes", genderCodes);
        model.addAttribute("hobbyCodes", hobbyCodes);

        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Code> cityCodes = commonService.findCodes("city");
            List<Code> genderCodes = commonService.findCodes("gender");
            List<Code> hobbyCodes = commonService.findCodes("hobby");
            model.addAttribute("cityCodes", cityCodes);
            model.addAttribute("genderCodes", genderCodes);
            model.addAttribute("hobbyCodes", hobbyCodes);
            return "members/createMemberForm";
        }

        Member member = Member.createMember(form.getName()
                , form.getCity()
                , form.getStreet()
                , form.getZipcode()
                , form.getGender()
                , form.getHobby());

        if(form.getMemberId() == null){
            memberService.join(member);
        } else {
            member.setMemberId(form.getMemberId());
            memberService.modifyMember(member);
        }
        return "redirect:/members";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/{memberId}/edit")
    public String editForm(@PathVariable Long memberId, Model model) {
        Member member = memberService.findOne(memberId);

        MemberForm memberForm = new MemberForm();
        memberForm.setMemberId(member.getMemberId());
        memberForm.setName(member.getName());
        memberForm.setCity(member.getCity());
        memberForm.setStreet(member.getStreet());
        memberForm.setZipcode(member.getZipcode());
        memberForm.setGender(member.getGender());
        memberForm.setHobby(member.getHobby());
        model.addAttribute("memberForm", memberForm);

        List<Code> cityCodes = commonService.findCodes("city");
        List<Code> genderCodes = commonService.findCodes("gender");
        List<Code> hobbyCodes = commonService.findCodes("hobby");
        model.addAttribute("cityCodes", cityCodes);
        model.addAttribute("genderCodes", genderCodes);
        model.addAttribute("hobbyCodes", hobbyCodes);

        return "members/createMemberForm";
    }
}
