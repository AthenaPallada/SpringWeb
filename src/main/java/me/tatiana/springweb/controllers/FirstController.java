package me.tatiana.springweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FirstController {
    @GetMapping("/start")
    public String launchApplication() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String page() {
        return "Имя ученика: Алексеева Татьяна Название проекта: рецепты Дата создания проекта: 03.01.2023 Описание проекта: первая проба пера";
    }
}
