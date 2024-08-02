package com.ssafy.iscode.regular.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regular_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularTimeDto implements Comparable<RegularTimeDto> {
    @Id
    @Column(name = "rt_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regular_id")
    private RegularDto regular;

    @Column(name = "rt_weekday")
    private int weekday;

    @Column(name = "rt_start")
    private int start;

    @Column(name = "rt_end")
    private int end;

    private final String[] WEEKDAY = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};

    @Override
    public int compareTo(RegularTimeDto o) {
        int result = Integer.compare(this.weekday, o.weekday);

        if(result == 0) {
            result = Integer.compare(this.start, o.start);

            if(result == 0) {
                result = Integer.compare(this.end, o.end);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return WEEKDAY[weekday] + " "
                + String.format("%02d", start/100) + ":" + String.format("%02d", start%100)
                + " ~ "
                + String.format("%02d", end/100) + ":" + String.format("%02d", end%100);
    }
}
