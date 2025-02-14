package hu.cubix.transroute.controller;

import hu.cubix.transroute.dto.SectionDTO;
import hu.cubix.transroute.mapper.SectionMapper;
import hu.cubix.transroute.model.Section;
import hu.cubix.transroute.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SectionMapper sectionMapper;

    @GetMapping
    public List<SectionDTO> findAll() {
        return sectionMapper.sectionsToDtos(sectionService.findAll());
    }

    @GetMapping("/{id}")
    public SectionDTO findById(@PathVariable long id) {
        Section section = sectionService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return sectionMapper.sectionToDto(section);
    }
}
