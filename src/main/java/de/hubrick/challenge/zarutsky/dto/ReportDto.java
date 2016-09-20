package de.hubrick.challenge.zarutsky.dto;

/**
 * Dto object, used to simplify report's generation.
 * usually there are dozens of libraries for nice object(s) serilization inc .csv format
 * where it's redundant to use something like this.
 */
public class ReportDto {

    private String[] values;

    private ReportDto(String[] values) {
        this.values = values;
    }

    public static ReportDto from(String... values){
        return new ReportDto(values);
    }

    public String[] getValues() {
        return values;
    }
}
