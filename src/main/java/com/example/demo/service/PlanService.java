package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.FollowerItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientDayItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.IngredientRealDayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.RealHistoryItem;
import com.example.demo.entity.UserItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.FollowerRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IngredientDayRepository;
import com.example.demo.repository.IngredientRealDayRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RealDayRepository;
import com.example.demo.repository.RealHistoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.utils.SendEmail;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlanService {

  private final UserService userService;
  private final SendEmail sendEmail;

  private final PlanRepository planRepository;
  private final UserRepository userRepository;
  private final DayRepository dayRepository;
  private final RealDayRepository realDayRepository;
  private final IngredientDayRepository ingredientDayRepository;
  private final IngredientRealDayRepository ingredientRealDayRepository;
  private final IngredientRepository ingredientRepository;
  private final FollowerRepository followerRepository;
  private final HistoryRepository historyRepository;
  private final RealHistoryRepository realHistoryRepository;

  @Transactional
  public List<PlanItem> getPlansForUser(Long userId) {
    UserItem user = getUserForUserId(userId).get();
    return planRepository.findAllByUserOrderByName(user);
  }

  public PlanItem getPlanById(Long planId){
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
  public List<PlanItem> getPlansForUser(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    if (userService.isDiet(principal) || userService.isAdmin(user.getUserId())) {
      return planRepository.findAllByUserOrderByName(user);
    } else {
      List<PlanItem> list = new ArrayList<>();
      List<FollowerItem> followers = followerRepository.findByUserId(user.getUserId());
      if (followers == null) {
        return List.of();
      }
      for (FollowerItem follower : followers) {
        PlanItem plan = follower.getPlan();
        list.add(plan);
      }
      return list;
    }
  }

  public Optional<UserItem> getUserForUserId(Long userId) {
    return userRepository.findUserItemByUserId(userId);
  }

  @Transactional
  public PlanItem createPlan(String name, Principal principal) {
    UserItem user = getUserByPrincipal(principal);

    PlanItem planItem = PlanItem.builder()
        .name(name)
        .user(user)
        .ready(false)
        .build();

    planRepository.save(planItem);

    for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
      for (EatingTime eatingTime : EatingTime.values()) {
        DayItem dayItem = new DayItem();
        dayItem.setPlan(planItem);
        dayItem.setDay(dayOfWeek);
        dayItem.setEatingTime(eatingTime);
        dayRepository.save(dayItem);
      }
    }

//    for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
//      for (EatingTime eatingTime : EatingTime.values()) {
//        RealDayItem dayItem = RealDayItem.builder()
//            .plan(planItem)
//            .day(dayOfWeek)
//            .eatingTime(eatingTime)
//            .build();
//        dayItem.setPlan(planItem);
//        dayItem.setDay(dayOfWeek);
//        dayItem.setEatingTime(eatingTime);
//        dayRepository.save(dayItem);
//      }
//    }

    log.info("Saving Recipe for User: {}", user.getEmail());
    return planRepository.findByName(name).get();
  }

  @Transactional
  public PlanItem copy(Long planId, Long copyPlanId, Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    PlanItem plan = planRepository.findByPlanId(planId).get();
    PlanItem planCopy = planRepository.findByPlanId(copyPlanId).get();

    for (DayItem day : planCopy.getDays()) {
      for (IngredientDayItem ingredientDay : day.getIngredients()) {
        IngredientDayItem ingredientDayItem = IngredientDayItem.builder()
            .ingredient(ingredientDay.getIngredient())
            .count(ingredientDay.getCount())
            .checkIngredient(null) //Чтобы не перенимать отмену и тд
            .day(plan.getDays().stream().filter(item -> item.getDay().equals(day.getDay()) &&
                item.getEatingTime().equals(day.getEatingTime())).findFirst().get())
            .build();
        ingredientDayRepository.save(ingredientDayItem);
      }
    }

    log.info("Copy plan from {} to {} for User: {}", planId, copyPlanId, user.getEmail());
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
  public PlanItem publish(Long planId, String username, Integer week, LocalDate date,
      Principal principal) {
    UserItem user = userService.getUserByUsername(username);
    PlanItem plan = planRepository.findByPlanId(planId).get();

    plan.setReady(true);
    planRepository.save(plan);

    int count = 0;
    for (int i = 0; i < week; i++) {
      for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
        for (EatingTime eatingTime : EatingTime.values()) {
          RealDayItem dayItem = RealDayItem.builder()
              .plan(plan)
              .day(dayOfWeek)
              .eatingTime(eatingTime)
              .date(date.plusDays(count))
              .build();
          realDayRepository.save(dayItem);
        }
        count++;
      }
    }

    int countDay = 0;
    for (int i = 0; i < week; i++) {
      for (DayItem day : plan.getDays()) {
        List<IngredientDayItem> allByDayOrderByDay = ingredientDayRepository.findAllByDayOrderByDay(
            day);
        for (IngredientDayItem ingredientDayItem : allByDayOrderByDay) {
          IngredientRealDayItem item = IngredientRealDayItem.builder()
              .checkIngredient(null)
              .count(ingredientDayItem.getCount())
              .ingredient(ingredientDayItem.getIngredient())
              .day(realDayRepository.findByPlanAndDayAndEatingTimeAndDate(ingredientDayItem.getDay()
                      .getPlan(), ingredientDayItem.getDay().getDay(),
                  ingredientDayItem.getDay().getEatingTime(),
                  date.plusDays(countDay / 3).plusWeeks(i)).get())
              .build();
          ingredientRealDayRepository.save(item);
        }
        countDay++;
      }
      countDay = 0;
    }
    FollowerItem follower = FollowerItem.builder()
        .plan(plan)
        .userId(user.getUserId())
        .build();
    followerRepository.save(follower);

    sendEmail.apply(user, plan);


    log.info("Saving ready for User: {}", user.getEmail());
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
  public PlanItem addIngredient(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count) {
    PlanItem plan = planRepository.findByPlanId(planId).get();

    IngredientItem ingredientItem = ingredientRepository.findByName(ingredient).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    Optional<IngredientDayItem> ingredientDay = ingredientDayRepository.findByDayAndIngredient(
        day, ingredientItem);

    if (ingredientDay.isPresent()) {
      ingredientDay.get().setCount(ingredientDay.get().getCount() + count);
      ingredientDayRepository.save(ingredientDay.get());
    } else {
      IngredientDayItem ingredientDayItem = IngredientDayItem.builder()
          .day(dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime)
              .get())
          .ingredient(ingredientRepository.findByName(ingredient).get())
          .count(count)
          .build();
      ingredientDayRepository.save(ingredientDayItem);
    }
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  @Transactional
  public PlanItem deleteIngredient(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient) {
    PlanItem plan = planRepository.findByPlanId(planId).get();

    IngredientItem ingredientItem = ingredientRepository.findByName(ingredient).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    IngredientDayItem ingredientDay = ingredientDayRepository.findByDayAndIngredient(
        day, ingredientItem).get();
    System.out.println("zzzzzz: " + ingredientDay.getId());
    System.out.println("xxxxxxxxxxxxxx: " + day.getDayId());
    ingredientDayRepository.delete(ingredientDay);

    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
  public PlanItem addIngredientReal(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count, LocalDate date) {
    PlanItem plan = planRepository.findByPlanId(planId).get();

    IngredientRealDayItem ingredientDayItem = IngredientRealDayItem.builder()
        .day(realDayRepository.findByPlanAndDayAndEatingTimeAndDate(plan, dayOfWeek, eatingTime,
                date)
            .get())
        .ingredient(ingredientRepository.findByName(ingredient).get())
        .count(count)
        .build();
    ingredientRealDayRepository.save(ingredientDayItem);
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
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
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
  public PlanItem checkReal(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, Integer count, LocalDate date) {
    PlanItem plan = planRepository.findByPlanId(planId).get();
    IngredientItem ingredientItem = ingredientRepository.findByName(ingredient).get();
    RealDayItem day = realDayRepository.findByPlanAndDayAndEatingTimeAndDate(plan, dayOfWeek,
        eatingTime, date).get();
    IngredientRealDayItem ingredientDayItem = ingredientRealDayRepository.findByDayAndIngredient(
        day,
        ingredientItem).get();
    ingredientDayItem.setCheckIngredient(true);
    ingredientRealDayRepository.save(ingredientDayItem);
    return planRepository.findByPlanId(planId).get();
  }

  @Transactional
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

    UserItem user = userService.getUserByUsername(principal.getName());
    sendEmail.apply(user, plan, ingredientOld, ingredientNew, comment);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  @Transactional
  public PlanItem updateReal(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredientOld, String ingredientNew, Integer count, String comment, LocalDate date) {
    PlanItem plan = planRepository.findByPlanId(planId).get();
    IngredientItem ingredientItem = ingredientRepository.findByName(ingredientOld).get();
    RealDayItem day = realDayRepository.findByPlanAndDayAndEatingTimeAndDate(plan, dayOfWeek,
        eatingTime, date).get();
    IngredientItem ingredientItemNew = ingredientRepository.findByName(ingredientNew).get();

    IngredientRealDayItem ingredientDayItem = ingredientRealDayRepository.findByDayAndIngredient(
        day,
        ingredientItem).get();

    Integer oldCount = ingredientDayItem.getCount();
    IngredientItem ingredientOldItem = ingredientDayItem.getIngredient();

    ingredientDayItem.setIngredient(ingredientItemNew);
    ingredientDayItem.setCount(count);
    ingredientDayItem.setCheckIngredient(false);
    ingredientRealDayRepository.save(ingredientDayItem);

    RealHistoryItem historyItem = RealHistoryItem.builder()
        .day(realDayRepository.findByPlanAndDayAndEatingTimeAndDate(plan, dayOfWeek, eatingTime,
                date)
            .get())
        .ingredientNew(ingredientItemNew)
        .countNew(count)
        .ingredientOld(ingredientOldItem)
        .countOld(oldCount)
        .comment(comment)
        .build();
    realHistoryRepository.save(historyItem);

    UserItem user = userService.getUserByUsername(principal.getName());
    sendEmail.apply(user, plan, ingredientOld, ingredientNew, comment);
    return planRepository.findByPlanId(Long.valueOf(planId)).get();
  }

  @Transactional
  public List<DayItem> getToday(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    PlanItem plan = followerRepository.findByUserId(user.getUserId()).get(0).getPlan();

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

  @Transactional
  public void delete(String planId) {
    PlanItem plan = planRepository.findByPlanId(Long.valueOf(planId)).get();
    planRepository.delete(plan);
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
