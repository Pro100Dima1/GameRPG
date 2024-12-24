package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class DungeonAdventureGUI extends Application {
    private int playerHealth = 100;
    private int monsterHealth;
    private int numHealthPotions = 3;
    private int healthPotionHeal = 30;
    private int playerAttack = 20;
    private int monsterAttack = 25;

    private Random random = new Random();
    private Label statusLabel = new Label("Добро пожаловать в приключение в подземелье!");
    private Label playerStatsLabel = new Label("Здоровье: 100 | Зелья здоровья: 3");
    private Button exploreButton = new Button("Продолжить исследование");
    private Button viewStatsButton = new Button("Посмотреть свои параметры");
    private Button exitButton = new Button("Выйти из подземелья");

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-padding: 15; -fx-alignment: center; -fx-spacing: 10;");

        mainLayout.getChildren().addAll(statusLabel, playerStatsLabel, exploreButton, viewStatsButton, exitButton);

        // Настраиваем действия кнопок
        exploreButton.setOnAction(event -> explore());
        viewStatsButton.setOnAction(event -> showStats());
        exitButton.setOnAction(event -> exitGame(primaryStage));

        // Создаем и отображаем сцену
        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Adventure");
        primaryStage.show();
    }

    private void explore() {
        if (random.nextInt(100) < 60) { // 60% шанс встретить монстра
            monsterHealth = random.nextInt(50) + 50;
            statusLabel.setText("На вас нападает монстр! Его здоровье: " + monsterHealth);
            fightMonster();
        } else {
            statusLabel.setText("Коридор пуст. Вы продолжаете путь...");
        }
    }

    private void fightMonster() {
        Alert fightAlert = new Alert(Alert.AlertType.INFORMATION);
        fightAlert.setTitle("Битва с монстром");
        fightAlert.setHeaderText(null);

        while (monsterHealth > 0) {
            ButtonType attack = new ButtonType("Атаковать");
            ButtonType heal = new ButtonType("Использовать зелье здоровья");
            ButtonType flee = new ButtonType("Сбежать");
            fightAlert.getButtonTypes().setAll(attack, heal, flee);

            fightAlert.setContentText("Ваше здоровье: " + playerHealth + "\nЗдоровье монстра: " + monsterHealth);
            ButtonType choice = fightAlert.showAndWait().orElse(flee);

            if (choice == attack) {
                int damageToMonster = random.nextInt(playerAttack) + 10;
                int damageToPlayer = random.nextInt(monsterAttack) + 5;

                monsterHealth -= damageToMonster;
                playerHealth -= damageToPlayer;

                if (playerHealth <= 0) {
                    gameOver();
                    return;
                }
            } else if (choice == heal) {
                if (numHealthPotions > 0) {
                    playerHealth += healthPotionHeal;
                    numHealthPotions--;
                } else {
                    statusLabel.setText("У вас больше нет зелий здоровья!");
                }
            } else if (choice == flee) {
                statusLabel.setText("Вы сбежали от монстра!");
                return;
            }
        }

        if (monsterHealth <= 0) {
            statusLabel.setText("Вы победили монстра!");
            if (random.nextInt(100) < 50) {
                numHealthPotions++;
                statusLabel.setText(statusLabel.getText() + " Вы нашли зелье здоровья!");
            }
        }
    }

    private void showStats() {
        playerStatsLabel.setText("Здоровье: " + playerHealth + " | Зелья здоровья: " + numHealthPotions);
    }

    private void gameOver() {
        statusLabel.setText("Вы погибли! Игра окончена.");
        exploreButton.setDisable(true);
    }

    private void exitGame(Stage stage) {
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}