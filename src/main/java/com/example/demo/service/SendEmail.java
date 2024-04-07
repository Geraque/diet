package com.example.demo.service;

import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SendEmail {

  private final EmailService emailService;
  @Async
  public void apply(UserItem user, PlanItem plan, String ingredientOld, String ingredientNew,
      String comment) {
    System.out.println("Началась отправка письма");
    String userEmail = plan.getUser().getEmail();
    System.out.println("userEmail2 " + userEmail);

    String messageText = String.format(
        "Ваш пациент %s %s изменил ингредиент %s на %s. Комментарий: %s.",
        user.getName(), user.getLastname(),
        ingredientOld, ingredientNew, comment);

    // Отправляем письмо
    emailService.sendEmail(userEmail, "Изменение в плане питания", messageText);
    System.out.println("Закончилась отправка письма");
  }
}
