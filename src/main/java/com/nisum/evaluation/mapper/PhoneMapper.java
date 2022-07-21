package com.nisum.evaluation.mapper;

import org.mapstruct.Mapper;

import com.nisum.evaluation.domain.Phone;
import com.nisum.evaluation.dto.PhoneDTO;

@Mapper(componentModel = "spring")
public interface PhoneMapper extends EntityMapper<PhoneDTO, Phone> {}