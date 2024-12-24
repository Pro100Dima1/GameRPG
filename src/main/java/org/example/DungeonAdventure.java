package org.example;

import java.util.Random;
import java.util.Scanner;

public class DungeonAdventure {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Выбор класса
        System.out.println("Выберите свой класс:");
        System.out.println("1. Воин (больше здоровья, средний урон)");
        System.out.println("2. Маг (меньше здоровья, высокий урон)");
        System.out.println("3. Лучник (баланс здоровья и урона)");
        System.out.print("Ваш выбор: ");
        int playerClass = scanner.nextInt();
        scanner.nextLine(); // очищаем ввод

        // Игровые параметры
        int playerHealth = playerClass == 1 ? 150 : (playerClass == 2 ? 80 : 100);
        int playerAttack = playerClass == 1 ? 15 : (playerClass == 2 ? 30 : 20);
        int playerLevel = 1;
        int playerExperience = 0;
        int numHealthPotions = 3;
        int healthPotionHeal = 30;

        System.out.println("\nДобро пожаловать в приключение в подземелье!");
        System.out.println("Исследуйте коридоры, сражайтесь с монстрами, ищите сокровища и развивайте своего героя!");

        gameLoop:
        while (true) {
            System.out.println("\nВы находитесь в коридоре подземелья.");
            System.out.println("1. Продолжить исследование");
            System.out.println("2. Посмотреть свои параметры");
            System.out.println("3. Выйти из подземелья");
            System.out.print("Ваш выбор: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\nВы двигаетесь вперед...");
                    if (random.nextInt(100) < 60) { // 60% шанс встретить монстра
                        System.out.println("На вас нападает монстр!");
                        int monsterHealth = random.nextInt(50) + 50;
                        int monsterAttack = random.nextInt(10) + 10;

                        while (monsterHealth > 0) {
                            System.out.println("\nВаше здоровье: " + playerHealth + " | Уровень: " + playerLevel);
                            System.out.println("Здоровье монстра: " + monsterHealth);
                            System.out.println("1. Атаковать монстра");
                            System.out.println("2. Использовать зелье здоровья (" + numHealthPotions + " осталось)");
                            System.out.println("3. Убежать");
                            System.out.print("Ваш выбор: ");
                            String battleChoice = scanner.nextLine();

                            switch (battleChoice) {
                                case "1":
                                    int damageToMonster = random.nextInt(playerAttack) + 10;
                                    if (random.nextInt(100) < 20) { // 20% шанс критического удара
                                        damageToMonster *= 2;
                                        System.out.println("\nКритический удар!");
                                    }
                                    int damageToPlayer = random.nextInt(monsterAttack) + 5;

                                    monsterHealth -= damageToMonster;
                                    playerHealth -= damageToPlayer;

                                    System.out.println("\nВы нанесли монстру " + damageToMonster + " урона.");
                                    System.out.println("Монстр атакует и наносит вам " + damageToPlayer + " урона.");

                                    if (playerHealth <= 0) {
                                        System.out.println("\nВы погибли! Игра окончена.");
                                        break gameLoop;
                                    }
                                    break;

                                case "2":
                                    if (numHealthPotions > 0) {
                                        playerHealth += healthPotionHeal;
                                        numHealthPotions--;
                                        System.out.println("\nВы выпили зелье здоровья и восстановили " + healthPotionHeal + " здоровья.");
                                    } else {
                                        System.out.println("\nУ вас больше нет зелий здоровья!");
                                    }
                                    break;

                                case "3":
                                    System.out.println("\nВы сбежали от монстра!");
                                    continue gameLoop;

                                default:
                                    System.out.println("\nНеверный выбор!");
                            }
                        }

                        if (monsterHealth <= 0) {
                            System.out.println("\nВы победили монстра!");
                            playerExperience += 20;
                            System.out.println("Вы получили 20 опыта!");

                            if (playerExperience >= playerLevel * 50) {
                                playerLevel++;
                                playerAttack += 5;
                                playerHealth += 20;
                                System.out.println("Поздравляем! Вы достигли уровня " + playerLevel + "!");
                            }

                            if (random.nextInt(100) < 50) { // 50% шанс найти зелье
                                numHealthPotions++;
                                System.out.println("Вы нашли зелье здоровья!");
                            }

                            if (random.nextInt(100) < 30) { // 30% шанс найти сокровище
                                System.out.println("Вы нашли сундук с сокровищами! Получено золото!");
                            }
                        }

                    } else {
                        System.out.println("Коридор пуст. Вы продолжаете путь...");
                    }
                    break;

                case "2":
                    System.out.println("\nВаши параметры:");
                    System.out.println("Здоровье: " + playerHealth);
                    System.out.println("Уровень: " + playerLevel);
                    System.out.println("Опыт: " + playerExperience + "/" + (playerLevel * 50));
                    System.out.println("Зелья здоровья: " + numHealthPotions);
                    break;

                case "3":
                    System.out.println("\nВы решили покинуть подземелье. До новых встреч, герой!");
                    break gameLoop;

                default:
                    System.out.println("\nНеверный выбор!");
            }
        }

        scanner.close();
    }
}