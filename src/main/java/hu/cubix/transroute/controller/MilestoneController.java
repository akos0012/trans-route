package hu.cubix.transroute.controller;

import hu.cubix.transroute.dto.MilestoneDTO;
import hu.cubix.transroute.mapper.MilestoneMapper;
import hu.cubix.transroute.model.Milestone;
import hu.cubix.transroute.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MilestoneMapper milestoneMapper;

    @GetMapping
    public List<MilestoneDTO> findAll() {
        return milestoneMapper.milestonesToDtos(milestoneService.findAll());
    }

    @GetMapping("/{id}")
    public MilestoneDTO findById(@PathVariable long id) {
        Milestone milestone = milestoneService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return milestoneMapper.milestoneToDto(milestone);
    }
}
