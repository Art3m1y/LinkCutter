package ru.Art3m1y.LinkCutter.dtoes;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LinkDto {
    @NotNull(message = "Значение изначальной ссылки должно быть обязательно передано")
    private String fullLink;
    @Min(value = 1, message = "Минимальное значение времени работы ссылки - 1 час")
    @Max(value = 720, message = "Максимальное значение времени работы ссылки - 720 часов")
    private int hoursInExpires;
}
