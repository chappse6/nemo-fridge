package parse.squarerefri.domain.manage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import parse.squarerefri.domain.common.SessionConst;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.manage.service.ManageService;
import parse.squarerefri.domain.member.domain.Member;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;

    @GetMapping("/{storageStatus}/list")
    public String listOutput(@PathVariable("storageStatus") StorageStatus storageStatus,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model) {
        List<Management> managements = manageService.findAll(loginMember.getId(), storageStatus);
        model.addAttribute("managements",managements);
        return "manage/listfood";
    }

    @GetMapping("/{storageStatus}/add")
    public String add() {
        return "{storageStatus}/add";
    }

    @PostMapping("/{storageStatus}/add")
    public String addSubmit(@PathVariable("storageStatus") StorageStatus storageStatus,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                             Model model, HttpServletRequest request, ManageInput parameter) {
        parameter.setMemberId(loginMember.getId());
        String foodName = manageService.registManage(parameter, storageStatus);
        model.addAttribute("foodName",foodName);
        return "{storageStatus}/list";
    }

}
