package com.tlu.interviewmanagement.controllers;

import com.tlu.interviewmanagement.entities.InterviewSchedule;
import com.tlu.interviewmanagement.entities.Job;
import com.tlu.interviewmanagement.entities.ResultInterview;
import com.tlu.interviewmanagement.enums.ELabelCommon;
import com.tlu.interviewmanagement.service.*;
import com.tlu.interviewmanagement.utils.SearchUtil;
import com.tlu.interviewmanagement.web.request.InterviewRequest;
import com.tlu.interviewmanagement.web.request.ResultRequest;
import com.tlu.interviewmanagement.web.request.SearchRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/interview")
@RequiredArgsConstructor
public class InterviewController {
    private final JobService jobService;
    private final UserService userService;
    private final InterviewService interviewService;
    private final SearchUtil searchUtil;
    private final CandidateService candidateService;
    private final ResultService resultService;
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @GetMapping({"/", ""})
    public String getCandidates(@ModelAttribute SearchRequest searchRequest,
                                Model model) {
        Page<InterviewSchedule> interviewSchedules = interviewService
                .findAllInterviewSchedule(searchUtil.getSearchRequest(searchRequest));
        SearchRequest searchResponse = searchUtil
                .setPageMax(interviewSchedules.getTotalPages(), searchRequest);
        model.addAttribute("interviewSchedules", interviewSchedules);
        model.addAttribute("searchResponse", searchResponse);
        return "ui/interview/list";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute InterviewRequest interviewRequest
            , Model model) {
        List<Job> jobs = jobService.findJobByStatusOpenAndApply();
        model.addAttribute("interviewRequest", interviewRequest);
        model.addAttribute("jobs", jobs);
        model.addAttribute("users", userService.findUserByRoleInterviewAndRecruiter());
        return "ui/interview/add";
    }

    @PostMapping("/create")
    public String createInterview(
            @ModelAttribute @Validated InterviewRequest interviewRequest,
            BindingResult bindingResult,
            RedirectAttributes ra) throws MessagingException {
        if(bindingResult.hasErrors()) {
            ra.addFlashAttribute("interviewRequest", interviewRequest);
            ra.addFlashAttribute("alert", "Create false");
            ra.addFlashAttribute("jobs", jobService.findJobByStatusOpen());
            ra.addFlashAttribute("users", userService.findUserByRoleInterviewAndRecruiter());
            return "redirect:/admin/interview";
        }
        logger.info(":::::::::Start to Create Interview:::::::::");
        InterviewSchedule interviewSchedule = interviewService.saveInterviewSchedule(interviewRequest);
        if(Objects.isNull(interviewSchedule)){
            ra.addFlashAttribute("interviewRequest", interviewRequest);
            ra.addFlashAttribute(ELabelCommon.ALERT.getValue(), "Fail");
            ra.addFlashAttribute(ELabelCommon.MESSAGE.getValue(), "Interview da ton tai");
            logger.info(":::::::::Create Interview Fail:::::::::");
            return "redirect:/admin/interview";
        }
//        notificationService.NotificationAddInterviewSchedule(interviewSchedule.getId(),interviewRequest);
        ra.addFlashAttribute(ELabelCommon.ALERT.getValue(), "Success");
        logger.info(":::::::::Create Interview  Successfull:::::::::");
        logger.info(":::::::::End to Create Interview:::::::::");
        return "redirect:/admin/interview";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute InterviewRequest interviewRequest
            , Model model) {
        InterviewSchedule interviewSchedule = interviewService.findInterviewScheduleById(id);
        List<Long> ids = interviewSchedule.getInterviewer()
                .stream().map(x -> x.getInterviewer().getId()).toList();
        model.addAttribute("ids", ids);
        model.addAttribute("interviewSchedule", interviewSchedule);
        model.addAttribute("users", userService.findUserByRoleInterviewAndRecruiter());
        return "ui/interview/edit";
    }

    @PostMapping("/edit/{id}")
    public String editInterview(@PathVariable Long id,
                                @ModelAttribute InterviewRequest interviewRequest,
                                BindingResult bindingResult,
                                RedirectAttributes ra,
                                Model model) throws MessagingException {
        if(bindingResult.hasErrors()){
            return edit(id,interviewRequest,model);
        }
        InterviewSchedule interviewSchedule = interviewService.updateInterviewSchedule(interviewRequest);
        if(Objects.isNull(interviewSchedule)){
            ra.addAttribute("interviewRequest", interviewRequest);
            ra.addAttribute(ELabelCommon.ALERT.getValue(), "Fail");
            ra.addAttribute(ELabelCommon.MESSAGE.getValue(), "Update khong thanh cong");
        }
        ra.addFlashAttribute(ELabelCommon.MESSAGE.getValue(), "Success");
        return "redirect:/admin/interview/edit/" + id;
    }

    @GetMapping("/{id}/candidates")
    public String candidate(@PathVariable Long id,
                            Model model) {
        InterviewSchedule interviewSchedule = interviewService.findInterviewScheduleById(id);
        model.addAttribute("resultInterviews", interviewSchedule.getResultInterviews());
        return "ui/interview/list-candidate";
    }

    @GetMapping("/{interviewId}/result/{resultId}")
    public String editCandidate(@PathVariable Long interviewId,
                                @PathVariable Long resultId,
                                Model model) {
        InterviewSchedule interviewSchedule = interviewService.findInterviewScheduleById(interviewId);
        ResultInterview resultInterview = interviewSchedule.getResultInterviews().stream()
                .filter(x -> x.getId().equals(resultId))
                .findFirst()
                .orElseThrow();
        model.addAttribute("resultInterviews", resultInterview);
        model.addAttribute("interviewSchedule", interviewSchedule);
        return "ui/interview/result";
    }

    @PostMapping("/{interviewId}/result/{resultId}")
    public String result(@PathVariable Long interviewId,
                         @PathVariable Long resultId,
                         @ModelAttribute ResultRequest resultRequest,
                         RedirectAttributes ra) {
        resultService.updateResult(resultRequest);
        ra.addFlashAttribute("alert","Update thanh cong");
        return "redirect:/admin/interview/" + interviewId + "/candidates";
    }

    @GetMapping("/{id}")
    public String interview(@PathVariable Long id,
                            @ModelAttribute ResultRequest requestRequest,
                            Model model) {
        InterviewSchedule interviewSchedule = interviewService.findInterviewScheduleById(id);
        model.addAttribute("interviewSchedule", interviewSchedule);
        return "ui/interview/detail";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes ra) throws MessagingException {
        interviewService.deleteInterviewSchedule(id);
        ra.addFlashAttribute("alert","Success");
        return "redirect:/admin/interview/";
    }
}
