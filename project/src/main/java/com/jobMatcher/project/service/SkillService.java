package com.jobMatcher.project.service;

import com.jobMatcher.project.dtos.SkillRequestDTO;
import com.jobMatcher.project.dtos.SkillResponseDTO;
import com.jobMatcher.project.exception.DuplicateResourceException;
import com.jobMatcher.project.exception.ResourceNotFoundException;
import com.jobMatcher.project.entity.Skill;
import com.jobMatcher.project.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {
    SkillRepository skillRepository;
    ModelMapper modelMapper;

    public SkillService(SkillRepository skillRepository, ModelMapper modelMapper) {
        this.skillRepository = skillRepository;
        this.modelMapper = modelMapper;
    }

    //    1. createSkill()
    public SkillResponseDTO createSkill(SkillRequestDTO skillRequestDTO){
        Skill skill=modelMapper.map(skillRequestDTO,Skill.class);
        if(skillRepository.existsByName(skill.getName())){
            throw new DuplicateResourceException("Skill Already Exists");
        }
        Skill savedSkill=skillRepository.save(skill);
        SkillResponseDTO skillResponseDTO=modelMapper.map(savedSkill,SkillResponseDTO.class);
        return skillResponseDTO;
    }
//2. getAllSkills()
    public List<SkillResponseDTO> getAllSkills(){
        List<Skill> skills=skillRepository.findAll();
        List<SkillResponseDTO> skillResponseDTOList=new ArrayList<>();
        for(Skill skill:skills){
            SkillResponseDTO skillResponseDTO=modelMapper.map(skill,SkillResponseDTO.class);
            skillResponseDTOList.add(skillResponseDTO);
        }
        return skillResponseDTOList;
    }
//3. getSkillById()
    public SkillResponseDTO getSkillById(long id){
       Skill skill= skillRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill doen't exists"));
       SkillResponseDTO skillResponseDTO=modelMapper.map(skill, SkillResponseDTO.class);

       return skillResponseDTO;
    }
//4. updateSkill()
    public SkillResponseDTO updateSkill(long id,SkillRequestDTO skillRequestDTO){
//        Optional<Skill> sk=skillRepository.findById(id);
//        if(sk.isPresent()){
//            Skill existingSkill=sk.get();
//            existingSkill.setName(skill.getName());
//            return skillRepository.save(existingSkill);
//        }
//        return null;
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill Not Found"));

        existingSkill.setName(skillRequestDTO.getName());
        Skill savedSkill=skillRepository.save(existingSkill);
        SkillResponseDTO skillResponseDTO=modelMapper.map(savedSkill, SkillResponseDTO.class);
        return skillResponseDTO;

    }
//5. deleteSkill()
    public void deleteSkill(long id){
        if(!skillRepository.existsById(id)){
            throw new ResourceNotFoundException("Skill Not Found");
        }
        skillRepository.deleteById(id);
    }
}
