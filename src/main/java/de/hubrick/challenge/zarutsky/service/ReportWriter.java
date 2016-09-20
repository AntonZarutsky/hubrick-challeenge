package de.hubrick.challenge.zarutsky.service;

import de.hubrick.challenge.zarutsky.dto.ReportDto;
import java.nio.file.Path;
import java.util.List;

public interface ReportWriter {

    void write(ReportDto header, List<ReportDto> data, Path filePath);

}
