package com.grepp.team08.app.model.place.repository;

import com.grepp.team08.app.model.place.entity.KakaoMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoMapRepository extends JpaRepository<KakaoMap, Long> {

    List<KakaoMap> getPlaceInfoByKeyword(String keyword);

}
