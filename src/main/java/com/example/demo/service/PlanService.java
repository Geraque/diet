package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.model.Plan;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.IngredientDayRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import java.time.DayOfWeek;
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

  private final PlanRepository planRepository;
  private final UserRepository userRepository;
  private final DayRepository dayRepository;
  private final IngredientDayRepository ingredientDayRepository;

  public List<PlanItem> getPlansForUser(Long userId) {
    UserItem user = getUserForUserId(userId).get();
    return planRepository.findAllByUserOrderByName(user);
  }

  public List<PlanItem> getPlansForUser(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    return planRepository.findAllByUserOrderByName(user);
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

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));
  }

}
