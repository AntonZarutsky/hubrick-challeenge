package de.hubrick.challenge.zarutsky.service;

import de.hubrick.challenge.zarutsky.dto.ReportDto;
import de.hubrick.challenge.zarutsky.utils.ProcessingException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReportWriter implements ReportWriter{


    public static final Charset ENCODING_CHARSET = Charset.forName("UTF-8");
    public static final String SEPARATOR = ",";

    @Override
    public void write(ReportDto header, List<ReportDto> data, Path filePath) {
        try {
//          Writing header
            Files.write(filePath, Collections.singletonList(toString(header)), ENCODING_CHARSET );
//          Writing report data itself
            Files.write(filePath,
                        data.stream()
                            .map(this::toString)
                            .collect(Collectors.toList()),
                        ENCODING_CHARSET,
                        StandardOpenOption.APPEND);
        }catch (IOException e) {
            throw new ProcessingException("Unable to write report to " + filePath, e);
        }

    }

//  Simplified object serializer.
    private String toString(ReportDto dto) {
        final StringBuilder sb = new StringBuilder();

        for (String token: dto.getValues()){
            sb.append(escape(token))
                    .append(SEPARATOR);
        }

//      remove last Separator character
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

//    since it's forbidden to use any 3rd party libraries, token for csv file should be escaped.
//    I would partly skip this for now, just remove ','.
    private String escape(String token) {
        return token.replace("," , "");
    }

}
