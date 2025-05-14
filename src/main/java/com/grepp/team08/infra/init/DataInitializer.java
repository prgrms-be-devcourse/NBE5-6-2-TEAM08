package com.grepp.team08.infra.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final DataInitializeService dataInitializeService;

    @EventListener(ApplicationReadyEvent.class)
    public void initHotPlaceVector(){
        dataInitializeService.initializeHotPlaceVector();
        System.out.println("HotPlace 벡터 초기화 트리거됨 (ApplicationReadyEvent).");
    }
}