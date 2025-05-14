package com.grepp.team08.infra.config;

import com.grepp.team08.app.controller.web.member.payload.SignupRequest;
import com.grepp.team08.app.model.member.dto.MemberDto;
import com.grepp.team08.app.model.member.entity.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPreferNestedProperties(false);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(SignupRequest.class, Member.class).addMappings(mapper -> {mapper.skip(Member::setId);});
        modelMapper.typeMap(MemberDto.class, Member.class).addMappings(mapper -> mapper.skip(Member::setId));
        return modelMapper;
    }
    
}

