package me.tatiana.springweb.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Информация")
public class FirstController {
    @GetMapping("/start")
    @Operation(summary = "Статус работы приложения")
    public String launchApplication() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    @Operation(summary = "Информация о приложении")
    public String page() {
        return "Имя ученика: Алексеева Татьяна Название проекта: рецепты Дата создания проекта: 03.01.2023 Описание проекта: первая проба пера";
    }
}
