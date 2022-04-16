package parse.squarerefri.domain.manage.model;

import groovy.transform.ToString;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.ManageStatus;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.member.domain.Member;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@Builder
public class ManageInput {

    private StorageStatus storageStatus;

    private String foodName;

    private String memberId;

    private String foodType;

    private String sellbydateString;

    private LocalDate sellbydate;

    //== 비지니스 로직==/

    // String을  LocalDate로 변경
     public static LocalDate LocalDateConverter(String sellbydateString) {
         return LocalDate.parse(sellbydateString, DateTimeFormatter.ISO_DATE);
     }

}
