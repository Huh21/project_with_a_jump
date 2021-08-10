package org.ict.project_with_a_jump;

import java.io.Serializable;

// 사업자 정보 등록 클래스
public class Facility implements Serializable {
    private String facilityName; // 시설명
    private String facilityAddr; // 시설주소 (도로명주소 기준)

    public Facility(String name, String addr) { // 생성자
        name = facilityName;
        addr = facilityAddr;
    }

    // main 액티비티에서 이 생성자를 등록하면 db에 데이터가 저장되게 해야 함
}