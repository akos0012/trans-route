package hu.cubix.transroute.controller;

import hu.cubix.transroute.dto.DelayRequestDTO;
import hu.cubix.transroute.dto.TransportPlanDTO;
import hu.cubix.transroute.mapper.TransportPlanMapper;
import hu.cubix.transroute.model.TransportPlan;
import hu.cubix.transroute.service.TransportPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPLanController {

    @Autowired
    private TransportPlanService transportPlanService;

    @Autowired
    private TransportPlanMapper transportPlanMapper;

    @GetMapping
    public List<TransportPlanDTO> findAll() {
        return transportPlanMapper.transportPlansToDtos(transportPlanService.findAll());
    }

    @GetMapping("/{id}")
    public TransportPlanDTO findById(@PathVariable long id) {
        TransportPlan transportPlan = transportPlanService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return transportPlanMapper.transportPlanToDto(transportPlan);
    }

    @PostMapping("/{id}/delay")
    public void registerDelay(@PathVariable long id, @RequestBody @Valid DelayRequestDTO delayRequestDTO) {
        transportPlanService.registerDelay(id, delayRequestDTO);
    }

}
