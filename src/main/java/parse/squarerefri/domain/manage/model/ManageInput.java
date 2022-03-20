package parse.squarerefri.domain.manage.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.ManageStatus;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@Getter
@Setter
public class ManageInput {

    private String foodName;

    private String memberId;

    private String foodType;

    private LocalDate sellbydate;

}
