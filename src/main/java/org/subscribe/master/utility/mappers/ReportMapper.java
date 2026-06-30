package org.subscribe.master.utility.mappers;

import org.mapstruct.Mapper;
import org.subscribe.master.dtos.reportDTOs.Report;
import org.subscribe.master.dtos.reportDTOs.ReportDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    Report reportDtoToReport(ReportDTO reportDTO);
}
