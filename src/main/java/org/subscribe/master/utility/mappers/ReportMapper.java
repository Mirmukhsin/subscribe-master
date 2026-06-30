package org.subscribe.master.utility.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.subscribe.master.dtos.reportDTOs.Report;
import org.subscribe.master.dtos.reportDTOs.ReportDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(target = "totalExpenseInMainCurrency", ignore = true)
    Report reportDtoToReport(ReportDTO reportDTO);
}
