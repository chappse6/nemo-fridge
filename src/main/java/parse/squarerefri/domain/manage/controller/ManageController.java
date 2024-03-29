package parse.squarerefri.domain.manage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import parse.squarerefri.domain.common.SessionConst;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.manage.service.ManageService;
import parse.squarerefri.domain.member.domain.Member;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

import static parse.squarerefri.domain.manage.model.ManageInput.LocalDateConverter;

@Controller
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;

    //리스트 출력
    @GetMapping("/{storageStatus}/list")
    public String listOutput(@PathVariable("storageStatus") StorageStatus storageStatus,
                             @RequestParam(required = false) boolean deleteMode,
                             Principal principal, Model model) {
        List<Management> managements = manageService.findAll(principal.getName(), storageStatus);
        model.addAttribute("managements", managements);
        model.addAttribute("storageStatus", storageStatus);
        model.addAttribute("deleteMode", deleteMode);
        return "manage/listfood";
    }

    @GetMapping("/manage/add")
    public String add(Model model) {
        List<String> foodList = manageService.findAllForList();
        model.addAttribute("foodList", foodList);
        return "manage/addfood";
    }

    @PostMapping("/manage/add")
    public String addSubmit(Principal principal, Model model, RedirectAttributes redirectAttributes, ManageInput parameter) {
        parameter.setMemberId(principal.getName());
        parameter.setSellbydate(LocalDateConverter(parameter.getSellbydateString()));
        String foodName = manageService.registManage(parameter);

        redirectAttributes.addAttribute("storageStatus", parameter.getStorageStatus());
        model.addAttribute("foodName", foodName);

        return "redirect:/{storageStatus}/list";
    }

    @PostMapping("{status}/list/{manageId}/cancel")
    public String cancelFood(@PathVariable("manageId") Long manageId, RedirectAttributes redirectAttributes) {
        manageService.deleteManage(manageId);

        redirectAttributes.addAttribute("deleteMode", true);
        return "redirect:/{status}/list";
    }

}
