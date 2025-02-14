package hu.cubix.transroute.service;

import hu.cubix.transroute.model.Address;
import hu.cubix.transroute.model.Section;
import hu.cubix.transroute.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section save(Section section){
        return sectionRepository.save(section);
    }

    public List<Section> findAll(){
        return sectionRepository.findAll();
    }

    public Optional<Section> findById(long id){
        return sectionRepository.findById(id);
    }
}
