package com.example.demo.service;

import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.UserItem;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
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
public class IngredientService {

  private final IngredientRepository ingredientRepository;
  private final UserRepository userRepository;


  public List<IngredientItem> getAllIngredients(Principal principal) {
    UserItem user = getUserByPrincipal(principal);
    return ingredientRepository.findAllByUserOrderByName(user);
  }

  public Optional<IngredientItem> getIngredientById(Long ingredientId) {
    return ingredientRepository.findAllByIngredientId(ingredientId);
  }

  @Transactional
  public List<IngredientItem> create(Principal principal, String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins) {
    UserItem user = getUserByPrincipal(principal);
    IngredientItem item = IngredientItem.builder()
        .carbohydrates(Double.valueOf(carbohydrates))
        .calories(Double.valueOf(calories))
        .fat(Double.valueOf(fat))
        .name(name)
        .proteins(Double.valueOf(proteins))
        .user(user)
        .build();
    ingredientRepository.save(item);
    return ingredientRepository.findAllByUserOrderByName(user);
  }

  public List<IngredientItem> change(Principal principal, String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins) {
    UserItem user = getUserByPrincipal(principal);
    IngredientItem ingredient = ingredientRepository.findByNameAndUser(name, user).get();
    ingredient.setName(name);
    ingredient.setCalories(Double.valueOf(calories));
    ingredient.setFat(Double.valueOf(fat));
    ingredient.setCarbohydrates(Double.valueOf(carbohydrates));
    ingredient.setProteins(Double.valueOf(proteins));
    ingredient.setUser(user);
    ingredientRepository.save(ingredient);
    return ingredientRepository.findAllByUserOrderByName(user);
  }

  @Transactional
  public List<IngredientItem> delete(Principal principal, String name) {
    UserItem user = getUserByPrincipal(principal);
    IngredientItem ingredient = ingredientRepository.findByNameAndUser(name, user).get();
    ingredientRepository.delete(ingredient);
    return ingredientRepository.findAllByUserOrderByName(user);
  }

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));

  }

  public void presetIngredients(UserItem user) {
    ingredientRepository.save(IngredientItem.builder()
        .name("Брынза (сыр из коровьего молока)")
        .proteins(17.9)
        .fat(20.1)
        .carbohydrates(0.0)
        .calories(260.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Йогурт натуральный. 2% жир.")
        .proteins(4.3)
        .fat(2.0)
        .carbohydrates(6.2)
        .calories(60.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Кефир 3.2% жирный")
        .proteins(2.8)
        .fat(3.2)
        .carbohydrates(4.1)
        .calories(56.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Кефир 1% нежирный")
        .proteins(2.8)
        .fat(1.0)
        .carbohydrates(4.0)
        .calories(40.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Молоко 3.2%")
        .proteins(2.9)
        .fat(3.2)
        .carbohydrates(4.7)
        .calories(59.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Молоко 2.5%")
        .proteins(2.8)
        .fat(2.5)
        .carbohydrates(4.7)
        .calories(52.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Молоко сгущенное без сахара")
        .proteins(6.6)
        .fat(7.5)
        .carbohydrates(9.4)
        .calories(131.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Молоко сгущённое с сахаром")
        .proteins(7.2)
        .fat(8.5)
        .carbohydrates(56.0)
        .calories(320.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Молоко сухое цельное")
        .proteins(26.0)
        .fat(25.0)
        .carbohydrates(37.5)
        .calories(476.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Простокваша 2.5%")
        .proteins(2.9)
        .fat(2.5)
        .carbohydrates(4.1)
        .calories(53.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Ряженка 2.5%")
        .proteins(2.9)
        .fat(2.5)
        .carbohydrates(4.2)
        .calories(54.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сливки 10% (нежирные)")
        .proteins(3.0)
        .fat(10.0)
        .carbohydrates(4.0)
        .calories(118.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сливки 20% (средней жирности)")
        .proteins(2.8)
        .fat(20.0)
        .carbohydrates(3.7)
        .calories(205.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сметана 10% (нежирная)")
        .proteins(3.0)
        .fat(10.0)
        .carbohydrates(2.9)
        .calories(206.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сыр голландский")
        .proteins(26.0)
        .fat(26.8)
        .carbohydrates(0.0)
        .calories(352.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сыр плавленый")
        .proteins(16.8)
        .fat(11.2)
        .carbohydrates(23.8)
        .calories(257.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сыр пошехонский")
        .proteins(26.0)
        .fat(26.5)
        .carbohydrates(0.0)
        .calories(350.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сыр российский")
        .proteins(24.1)
        .fat(29.5)
        .carbohydrates(0.3)
        .calories(363.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сыр швейцарский")
        .proteins(24.9)
        .fat(31.8)
        .carbohydrates(0.0)
        .calories(396.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Творожная масса")
        .proteins(7.1)
        .fat(23.0)
        .carbohydrates(27.5)
        .calories(341.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Творог обезжиренный")
        .proteins(16.5)
        .fat(0.0)
        .carbohydrates(1.3)
        .calories(71.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Творог 5% нежирный")
        .proteins(17.2)
        .fat(5.0)
        .carbohydrates(1.8)
        .calories(121.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Творог 9% полужирный")
        .proteins(16.7)
        .fat(9.0)
        .carbohydrates(2.0)
        .calories(159.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Баранки")
        .proteins(16.0)
        .fat(1.0)
        .carbohydrates(70.0)
        .calories(366.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Мука пшеничная 1-го сорта")
        .proteins(10.6)
        .fat(1.3)
        .carbohydrates(67.6)
        .calories(331.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Мука пшеничная 2-го сорта")
        .proteins(11.7)
        .fat(1.8)
        .carbohydrates(63.7)
        .calories(324.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Мука пшеничная высш. сорт")
        .proteins(10.3)
        .fat(1.1)
        .carbohydrates(68.9)
        .calories(334.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Мука ржаная сеяная")
        .proteins(6.9)
        .fat(1.4)
        .carbohydrates(67.3)
        .calories(304.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сухари к чаю")
        .proteins(10.0)
        .fat(2.3)
        .carbohydrates(73.8)
        .calories(397.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сушки маковые")
        .proteins(11.3)
        .fat(4.4)
        .carbohydrates(70.5)
        .calories(372.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Хлеб пшеничный")
        .proteins(8.1)
        .fat(1.0)
        .carbohydrates(48.8)
        .calories(242.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Хлеб ржаной")
        .proteins(13.0)
        .fat(3.0)
        .carbohydrates(40.0)
        .calories(250.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Батон нарезной")
        .proteins(7.5)
        .fat(2.9)
        .carbohydrates(50.9)
        .calories(264.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Батон подмосковный")
        .proteins(7.5)
        .fat(2.6)
        .carbohydrates(50.6)
        .calories(261.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Жир кондитерский")
        .proteins(0.0)
        .fat(99.8)
        .carbohydrates(0.0)
        .calories(897.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Майонез провансаль")
        .proteins(3.1)
        .fat(67.0)
        .carbohydrates(2.6)
        .calories(624.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Маргарин столовый 40%")
        .proteins(0.0)
        .fat(40.0)
        .carbohydrates(0.0)
        .calories(360.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Маргарин молочный")
        .proteins(0.3)
        .fat(82.0)
        .carbohydrates(1.0)
        .calories(743.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Масло растительное")
        .proteins(0.0)
        .fat(99.0)
        .carbohydrates(0.0)
        .calories(899.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Масло сливочное 72.5%")
        .proteins(1.0)
        .fat(72.5)
        .carbohydrates(1.4)
        .calories(662.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Масло сливочное 82%")
        .proteins(0.7)
        .fat(82.0)
        .carbohydrates(0.7)
        .calories(740.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Масло пальмовое")
        .proteins(0.0)
        .fat(99.9)
        .carbohydrates(0.0)
        .calories(899.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Геркулес")
        .proteins(12.5)
        .fat(6.2)
        .carbohydrates(61.0)
        .calories(352.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Гречневая крупа (продел)")
        .proteins(9.5)
        .fat(2.3)
        .carbohydrates(65.9)
        .calories(306.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Гречневая крупа ядрица (гречка)")
        .proteins(12.6)
        .fat(3.3)
        .carbohydrates(62.1)
        .calories(313.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Кукурузная крупа")
        .proteins(8.3)
        .fat(1.2)
        .carbohydrates(75.0)
        .calories(337.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Манная крупа")
        .proteins(10.3)
        .fat(1.0)
        .carbohydrates(67.4)
        .calories(328.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Овсяная крупа")
        .proteins(12.3)
        .fat(6.1)
        .carbohydrates(59.5)
        .calories(342.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Перловая крупа")
        .proteins(9.3)
        .fat(1.1)
        .carbohydrates(73.7)
        .calories(320.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Пшеничная крупа")
        .proteins(11.5)
        .fat(1.3)
        .carbohydrates(62.0)
        .calories(316.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Пшенная крупа")
        .proteins(11.5)
        .fat(3.3)
        .carbohydrates(69.3)
        .calories(348.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Рис белый")
        .proteins(6.7)
        .fat(0.7)
        .carbohydrates(78.9)
        .calories(344.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Толокно")
        .proteins(12.5)
        .fat(6.0)
        .carbohydrates(64.9)
        .calories(363.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Ячневая")
        .proteins(10.4)
        .fat(1.3)
        .carbohydrates(66.3)
        .calories(324.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Баклажан")
        .proteins(1.2)
        .fat(0.1)
        .carbohydrates(4.5)
        .calories(24.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Брюква")
        .proteins(1.2)
        .fat(0.1)
        .carbohydrates(7.7)
        .calories(37.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Огурцы парниковые")
        .proteins(0.7)
        .fat(0.0)
        .carbohydrates(1.8)
        .calories(10.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Перец жёлтый сладкий")
        .proteins(1.3)
        .fat(0.0)
        .carbohydrates(5.3)
        .calories(27.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Перец зеленый сладкий")
        .proteins(1.3)
        .fat(0.0)
        .carbohydrates(6.9)
        .calories(33.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Перец красный сладкий")
        .proteins(1.3)
        .fat(0.0)
        .carbohydrates(5.3)
        .calories(27.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Петрушка (зелень)")
        .proteins(3.7)
        .fat(0.0)
        .carbohydrates(8.1)
        .calories(45.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Петрушка (корень)")
        .proteins(1.5)
        .fat(0.0)
        .carbohydrates(11.0)
        .calories(47.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Ревень (черешковый)")
        .proteins(0.7)
        .fat(0.0)
        .carbohydrates(2.9)
        .calories(16.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Редис")
        .proteins(1.2)
        .fat(0.1)
        .carbohydrates(3.4)
        .calories(19.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Редька")
        .proteins(1.9)
        .fat(0.0)
        .carbohydrates(7.0)
        .calories(34.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Салат")
        .proteins(1.5)
        .fat(0.0)
        .carbohydrates(2.2)
        .calories(14.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Свекла")
        .proteins(1.5)
        .fat(0.1)
        .carbohydrates(8.8)
        .calories(43.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Томаты (помидоры)")
        .proteins(1.1)
        .fat(0.2)
        .carbohydrates(3.7)
        .calories(20.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Черемша")
        .proteins(2.4)
        .fat(0.1)
        .carbohydrates(6.5)
        .calories(34.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Чеснок")
        .proteins(6.5)
        .fat(0.5)
        .carbohydrates(29.9)
        .calories(143.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Шпинат")
        .proteins(2.9)
        .fat(0.3)
        .carbohydrates(2.0)
        .calories(22.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Щавель")
        .proteins(1.5)
        .fat(0.0)
        .carbohydrates(2.9)
        .calories(19.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Абрикос")
        .proteins(0.9)
        .fat(0.0)
        .carbohydrates(9.0)
        .calories(44.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Айва")
        .proteins(0.6)
        .fat(0.0)
        .carbohydrates(9.8)
        .calories(40.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Смородина черная")
        .proteins(1.0)
        .fat(0.0)
        .carbohydrates(8.0)
        .calories(40.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Черника")
        .proteins(1.1)
        .fat(0.0)
        .carbohydrates(8.6)
        .calories(40.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Шиповник свежий")
        .proteins(1.6)
        .fat(0.0)
        .carbohydrates(24.0)
        .calories(101.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Урюк")
        .proteins(5.0)
        .fat(0.0)
        .carbohydrates(67.5)
        .calories(278.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Курага")
        .proteins(5.2)
        .fat(0.0)
        .carbohydrates(65.9)
        .calories(272.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Изюм с косточкой")
        .proteins(1.8)
        .fat(0.0)
        .carbohydrates(70.9)
        .calories(276.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Изюм кишмиш")
        .proteins(2.3)
        .fat(0.0)
        .carbohydrates(71.2)
        .calories(279.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вишня")
        .proteins(1.5)
        .fat(0.0)
        .carbohydrates(73.0)
        .calories(292.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Груша")
        .proteins(2.3)
        .fat(0.0)
        .carbohydrates(62.1)
        .calories(246.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Персик")
        .proteins(3.0)
        .fat(0.0)
        .carbohydrates(68.5)
        .calories(275.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Чернослив")
        .proteins(2.3)
        .fat(0.0)
        .carbohydrates(65.6)
        .calories(264.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Яблоко")
        .proteins(3.2)
        .fat(0.0)
        .carbohydrates(68.0)
        .calories(273.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Бобы")
        .proteins(6.0)
        .fat(0.1)
        .carbohydrates(8.3)
        .calories(58.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Горох лущеный")
        .proteins(23.0)
        .fat(1.6)
        .carbohydrates(57.7)
        .calories(323.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Горох цельный")
        .proteins(23.0)
        .fat(1.2)
        .carbohydrates(53.3)
        .calories(303.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Соя")
        .proteins(34.9)
        .fat(17.3)
        .carbohydrates(26.5)
        .calories(395.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Фасоль")
        .proteins(22.3)
        .fat(1.7)
        .carbohydrates(54.5)
        .calories(309.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Чечевица")
        .proteins(24.8)
        .fat(1.1)
        .carbohydrates(53.7)
        .calories(310.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Белые свежие грибы")
        .proteins(3.2)
        .fat(0.7)
        .carbohydrates(1.6)
        .calories(25.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Белые сушеные грибы")
        .proteins(27.6)
        .fat(6.8)
        .carbohydrates(10.0)
        .calories(209.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Подберезовики свежие")
        .proteins(2.3)
        .fat(0.9)
        .carbohydrates(3.7)
        .calories(31.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Подосиновики свежие")
        .proteins(3.3)
        .fat(0.5)
        .carbohydrates(3.4)
        .calories(31.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Баранина")
        .proteins(16.3)
        .fat(15.3)
        .carbohydrates(0.0)
        .calories(203.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говядина")
        .proteins(18.9)
        .fat(12.4)
        .carbohydrates(0.0)
        .calories(187.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Конина")
        .proteins(20.2)
        .fat(7.0)
        .carbohydrates(0.0)
        .calories(143.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Кролик")
        .proteins(20.7)
        .fat(12.9)
        .carbohydrates(0.0)
        .calories(199.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Свинина нежирная")
        .proteins(16.4)
        .fat(27.8)
        .carbohydrates(0.0)
        .calories(316.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Свинина жирная")
        .proteins(11.4)
        .fat(49.3)
        .carbohydrates(0.0)
        .calories(489.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Телятина")
        .proteins(19.7)
        .fat(1.2)
        .carbohydrates(0.0)
        .calories(90.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говяжья Печень")
        .proteins(17.4)
        .fat(3.1)
        .carbohydrates(0.0)
        .calories(98.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говяжьи Почки")
        .proteins(12.5)
        .fat(1.8)
        .carbohydrates(0.0)
        .calories(66.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говяжье Вымя")
        .proteins(12.3)
        .fat(13.7)
        .carbohydrates(0.0)
        .calories(173.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говяжье Сердце")
        .proteins(15.0)
        .fat(3.0)
        .carbohydrates(0.0)
        .calories(87.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Говяжий Язык")
        .proteins(13.6)
        .fat(12.1)
        .carbohydrates(0.0)
        .calories(163.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Почки свиные")
        .proteins(13.0)
        .fat(3.1)
        .carbohydrates(0.0)
        .calories(80.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Печень свиная")
        .proteins(18.8)
        .fat(3.6)
        .carbohydrates(0.0)
        .calories(108.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сердце свиное")
        .proteins(15.1)
        .fat(3.2)
        .carbohydrates(0.0)
        .calories(89.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Язык свиной")
        .proteins(14.2)
        .fat(16.8)
        .carbohydrates(0.0)
        .calories(208.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Гусь")
        .proteins(16.1)
        .fat(33.3)
        .carbohydrates(0.0)
        .calories(364.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Индейка")
        .proteins(21.6)
        .fat(12.0)
        .carbohydrates(0.8)
        .calories(197.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Кура")
        .proteins(20.8)
        .fat(8.8)
        .carbohydrates(0.6)
        .calories(165.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Цыплёнок")
        .proteins(18.7)
        .fat(7.8)
        .carbohydrates(0.4)
        .calories(156.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Утка")
        .proteins(16.5)
        .fat(61.2)
        .carbohydrates(0.0)
        .calories(346.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Диабетическая")
        .proteins(12.1)
        .fat(22.8)
        .carbohydrates(0.0)
        .calories(254.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Диетическая")
        .proteins(12.1)
        .fat(13.5)
        .carbohydrates(0.0)
        .calories(170.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Докторская")
        .proteins(13.7)
        .fat(22.8)
        .carbohydrates(0.0)
        .calories(260.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Любительская")
        .proteins(12.2)
        .fat(28.0)
        .carbohydrates(0.0)
        .calories(301.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Молочная")
        .proteins(11.7)
        .fat(22.8)
        .carbohydrates(0.0)
        .calories(252.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Вареная колбаса Телячья")
        .proteins(12.5)
        .fat(29.6)
        .carbohydrates(0.0)
        .calories(316.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Ветчина")
        .proteins(22.6)
        .fat(20.9)
        .carbohydrates(0.0)
        .calories(279.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Варено-копченая Сервелат")
        .proteins(28.2)
        .fat(27.5)
        .carbohydrates(0.0)
        .calories(360.0)
        .user(user)
        .build());

    ingredientRepository.save(IngredientItem.builder()
        .name("Сырокопченая колбаса Московская")
        .proteins(24.8)
        .fat(41.5)
        .carbohydrates(0.0)
        .calories(473.0)
        .user(user)
        .build());
  }
}
