package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientDayItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.IngredientRealDayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.model.Plan;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.FollowerRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IngredientDayRepository;
import com.example.demo.repository.IngredientRealDayRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RealDayRepository;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlanService {

  private final UserService userService;
  private final PlanRepository planRepository;
  private final UserRepository userRepository;
  private final DayRepository dayRepository;
  private final RealDayRepository realDayRepository;
  private final IngredientDayRepository ingredientDayRepository;
  private final IngredientRealDayRepository ingredientRealDayRepository;
  private final IngredientRepository ingredientRepository;
  private final FollowerRepository followerRepository;
  private final HistoryRepository historyRepository;
  private final SendEmail sendEmail;

  public List<PlanItem> getPlansForUser(Long userId) {
    UserItem user = getUserForUserId(userId).get();
    return planRepository.findAllByUserOrderByName(user);
  }

  public List<PlanItem> getPlansForUser(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    if (userService.isDiet(principal) || userService.isAdmin(user.getUserId())) {
      return planRepository.findAllByUserOrderByName(user);
    } else {
      List<PlanItem> list = new ArrayList<>();
      PlanItem plan = followerRepository.findByUserId(user.getUserId()).getPlan();
      list.add(plan);
      return list;
    }
  }

  public Optional<UserItem> getUserForUserId(Long userId) {
    return userRepository.findUserItemByUserId(userId);
  }

  public PlanItem createPlan(Plan plan, Principal principal) {
    // Получение пользователя
    UserItem user = getUserByPrincipal(principal);

    // Создание PlanItem
    PlanItem planItem = PlanItem.builder()
        .name(plan.getName())
        .user(user)
        .build();

    // Сохранение PlanItem в репозиторий
    planRepository.save(planItem);

    // Создание DayItems и привязка к PlanItem
    for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
      for (EatingTime eatingTime : EatingTime.values()) {
        DayItem dayItem = new DayItem();
        dayItem.setPlan(planItem); // Привязываем к только что созданному planItem
        dayItem.setDay(dayOfWeek);
        dayItem.setEatingTime(eatingTime);
        // Сохрани dayItem используя соответствующий репозиторий
        // Не забудь проверить/создать соответствующий метод в репозитории
        dayRepository.save(dayItem);
      }
    }

    // Логгирование создания плана
    log.info("Saving Recipe for User: {}", user.getEmail());

    // Возврат созданного плана
    return planItem;
  }

  public PlanItem addIngredient(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count) {
    PlanItem plan = planRepository.findByPlanId(planId).get();

    IngredientDayItem ingredientDayItem = IngredientDayItem.builder()
        .day(dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime)
            .get())
        .ingredient(ingredientRepository.findByName(ingredient).get())
        .count(count)
        .build();
    ingredientDayRepository.save(ingredientDayItem);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  public PlanItem addIngredientReal(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count) {
    PlanItem plan = planRepository.findByPlanId(planId).get();

    IngredientRealDayItem ingredientDayItem = IngredientRealDayItem.builder()
        .day(realDayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime)
            .get())
        .ingredient(ingredientRepository.findByName(ingredient).get())
        .count(count)
        .build();
    ingredientRealDayRepository.save(ingredientDayItem);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  public PlanItem check(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count) {
    PlanItem plan = planRepository.findByPlanId(planId).get();
    IngredientItem ingredientItem = ingredientRepository.findByName(ingredient).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    IngredientDayItem ingredientDayItem = ingredientDayRepository.findByDayAndIngredient(day,
        ingredientItem).get();
    ingredientDayItem.setCheckIngredient(true);
    ingredientDayRepository.save(ingredientDayItem);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  public PlanItem update(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredientOld, String ingredientNew, Integer count, String comment) {
    PlanItem plan = planRepository.findByPlanId(planId).get();
    IngredientItem ingredientItem = ingredientRepository.findByName(ingredientOld).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    IngredientItem ingredientItemNew = ingredientRepository.findByName(ingredientNew).get();

    IngredientDayItem ingredientDayItem = ingredientDayRepository.findByDayAndIngredient(day,
        ingredientItem).get();

    Integer oldCount = ingredientDayItem.getCount();
    IngredientItem ingredientOldItem = ingredientDayItem.getIngredient();

    ingredientDayItem.setIngredient(ingredientItemNew);
    ingredientDayItem.setCount(count);
    ingredientDayItem.setCheckIngredient(false);
    ingredientDayRepository.save(ingredientDayItem);

    HistoryItem historyItem = HistoryItem.builder()
        .day(dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime)
            .get())
        .ingredientNew(ingredientItemNew)
        .countNew(count)
        .ingredientOld(ingredientOldItem)
        .countOld(oldCount)
        .comment(comment)
        .build();
    historyRepository.save(historyItem);

    // Создаём экземпляр EmailService
//    String host = "smtp.mail.ru"; // SMTP сервер вашего почтового провайдера
//    String port = "465"; // Порт SMTP сервера
//    String username = "alekspavlov04@mail.ru"; // Ваш email
//    String password = "Gfhjkmvjq0"; // Ваш пароль
//    EmailService emailService = new EmailService(host, port, username, password);

    UserItem user = userService.getUserByUsername(principal.getName());
    sendEmail.apply(user, plan, ingredientOld, ingredientNew, comment);
    System.out.println("Прошло");
//    String userEmail = plan.getUser().getEmail();
//    System.out.println("userEmail2 " + userEmail);
//
//    String messageText = String.format(
//        "Ваш пациент %s %s изменил ингредиент %s на %s. Комментарий: %s.",
//        user.getName(), user.getLastname(),
//        ingredientOld, ingredientNew, comment);
//
//    // Отправляем письмо
//    emailService.sendEmail(userEmail, "Изменение в плане питания", messageText);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  public List<DayItem> getToday(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    PlanItem plan = followerRepository.findByUserId(user.getUserId()).getPlan();

    Calendar calendar = Calendar.getInstance();
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    List<DayItem> list = new ArrayList<>();
    for (DayItem dayItem : plan.getDays()) {
      if (getDayOfWeek(dayOfWeek).equals(dayItem.getDay())) {
        list.add(dayItem);
      }
    }
    return list;
  }

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));
  }

  private DayOfWeek getDayOfWeek(int day) {
    DayOfWeek dayOfWeekText = DayOfWeek.SUNDAY;
    switch (day) {
      case Calendar.SUNDAY:
        dayOfWeekText = DayOfWeek.SUNDAY;
        break;
      case Calendar.MONDAY:
        dayOfWeekText = DayOfWeek.MONDAY;
        break;
      case Calendar.TUESDAY:
        dayOfWeekText = DayOfWeek.TUESDAY;
        break;
      case Calendar.WEDNESDAY:
        dayOfWeekText = DayOfWeek.WEDNESDAY;
        break;
      case Calendar.THURSDAY:
        dayOfWeekText = DayOfWeek.THURSDAY;
        break;
      case Calendar.FRIDAY:
        dayOfWeekText = DayOfWeek.FRIDAY;
        break;
      case Calendar.SATURDAY:
        dayOfWeekText = DayOfWeek.SATURDAY;
        break;
    }
    return dayOfWeekText;
  }

}
