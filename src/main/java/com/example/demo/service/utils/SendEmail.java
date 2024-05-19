package com.example.demo.service.utils;

import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import com.example.demo.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SendEmail {

  private final EmailService emailService;
  private final SendNotification sendNotification;
  private final String HEADING = "Изменение в плане питания";
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

    sendNotification.apply(plan.getUser(), HEADING, messageText);
    emailService.sendEmail(userEmail, HEADING, messageText);
    System.out.println("Закончилась отправка письма");
  }

  @Async
  public void apply(UserItem user, PlanItem plan) {
    System.out.println("Началась отправка письма");
    String userEmail = user.getEmail();
    System.out.println("userEmail2 " + userEmail);

    String messageText = String.format(
        "Ваш диетолог %s %s опубликовал для вас план питания: %s.",
        user.getName(), user.getLastname(),
        plan.getName());

    sendNotification.apply(user, HEADING, messageText);
    emailService.sendEmail(userEmail, HEADING, messageText);
    System.out.println("Закончилась отправка письма");
  }
}
