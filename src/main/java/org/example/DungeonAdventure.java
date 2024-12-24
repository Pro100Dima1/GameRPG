package org.example;

import java.util.Random;
import java.util.Scanner;

public class DungeonAdventure {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Игровые параметры
        int playerHealth = 100;
        int monsterHealth;
        int numHealthPotions = 3;
        int healthPotionHeal = 30;
        int playerAttack = 20;
        int monsterAttack = 25;

        System.out.println("Добро пожаловать в приключение в подземелье!");
        System.out.println("Исследуйте коридоры, сражайтесь с монстрами и ищите сокровища!");

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
                        monsterHealth = random.nextInt(50) + 50;

                        while (monsterHealth > 0) {
                            System.out.println("\nВаше здоровье: " + playerHealth);
                            System.out.println("Здоровье монстра: " + monsterHealth);
                            System.out.println("1. Атаковать монстра");
                            System.out.println("2. Использовать зелье здоровья (" + numHealthPotions + " осталось)");
                            System.out.println("3. Убежать");
                            System.out.print("Ваш выбор: ");
                            String battleChoice = scanner.nextLine();

                            switch (battleChoice) {
                                case "1":
                                    int damageToMonster = random.nextInt(playerAttack) + 10;
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
                                        System.out.println("Теперь у вас " + playerHealth + " здоровья.");
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
                            if (random.nextInt(100) < 50) { // 50% шанс найти зелье
                                numHealthPotions++;
                                System.out.println("Вы нашли зелье здоровья!");
                            }
                        }

                    } else {
                        System.out.println("Коридор пуст. Вы продолжаете путь...");
                    }
                    break;

                case "2":
                    System.out.println("\nВаши параметры:");
                    System.out.println("Здоровье: " + playerHealth);
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