/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.turnbasestack;

import java.util.Stack;
import java.util.Scanner;
import java.util.Random;

public class TurnBaseStack {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Stack<Integer> lastHP = new Stack<>();
        int gameTimer = 1;
        boolean startGame = true;
        Random random = new Random();
        
        double playerHP = 200;
        double playerDMG = 10;
        double crit = 1;
        int energy = 95;
        int botHP = 100;
        double botDMG;
        
        int randomDamage;
        int randomCrit;
        
        boolean isCrit = true;
        boolean isDone = false;
        boolean skip = false;
        
        while (startGame) {
            
            lastHP.push(botHP);
            
            System.out.println("__________________________");
            System.out.println("\nPlayer HP : " + playerHP);
            System.out.println("Energy : " + (energy + 5));
            System.out.println("Bot HP : " + botHP);
            System.out.println("__________________________");
            
            gameTimer += 1;
            if (isOddorEven(gameTimer)) {
                lastHP.push(botHP);
                int action;
                
                if (!skip) {
                    System.out.println("\nYour Turn!\n");
                    System.out.println("[1] - Tackle A simple physical attack Damage = 10. -5 Energy\n" +
                                       "[2] - Thunderbolt High-voltage attack Damage = 15. -10 Energy\n" +
                                       "[3] - Leer Reduces opponent’s defense Damage = 0. -15 Energy\n" + 
                                       "[4] - Hyper Beam Devastating power shot Damage = 40. -30 Energy\n");
                }
                
                System.out.print("What do you want to do? : ");
                action = s.nextInt();
                
                switch(action) {
                    
                    case 1:
                        botHP -= playerDMG * crit;
                        energy -= 5;
                        System.out.println("You used Tackle, Bot HP is " + botHP);
                        skip = false;
                        break;
                    case 2:
                        botHP -= (playerDMG + 5) * crit;
                        energy -= 10;
                        System.out.println("You used Thunderbolt, Bot HP is " + botHP);
                        skip = false;
                        break;
                    case 3:
                        botHP -= 0;
                        energy -= 15;
                        crit += 0.2;
                        System.out.println("You used Leer, next damage will be higher. Bot HP is " + botHP);
                        skip = false;
                        break;
                    case 4:
                        botHP -= (playerDMG + 30) * crit;
                        energy -= 40;
                        System.out.println("You used Hyper Beam, Bot HP is " + botHP);
                        skip = false;
                        break;
                    case 5:
                        System.out.println("Invalid Input only 1 - 4");
                        skip = true;
                    
                }
                
            } else if(!skip){
                System.out.println("\nEnemy's Turn!\n");
                
                if (isBotPassive() && botHP != 100) {
                    lastHP.pop();
                    botHP = lastHP.peek();
                    System.out.println("Enemy's passive is triggered, HP returns to previous state. Bot HP = " + botHP);
                }
                
                botDMG = 10;
                randomDamage = random.nextInt(5) + 1;
                randomCrit = random.nextInt(3) + 1;
                isCrit = random.nextBoolean();
                
                switch(randomDamage) {
                    
                    case 1:
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                        } else {
                            botDMG = botDMG;
                        }
                        
                        playerHP -= botDMG;
                        System.out.println("Enemy's Damage is " + botDMG);
                        break;
                    case 2:
                        botDMG += 5;
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                        } else {
                            botDMG = botDMG;
                        }
                        
                        playerHP -= botDMG;
                        System.out.println("Enemy's Damage is " + botDMG);
                        break;
                    case 3:
                        botDMG += 8;
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                        } else {
                            botDMG = botDMG;
                        }
                        
                        playerHP -= botDMG;
                        System.out.println("Enemy's Damage is " + botDMG);
                        break;
                    case 4:
                        botDMG += 10;
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                        } else {
                            botDMG = botDMG;
                        }
                        
                        playerHP -= botDMG;
                        System.out.println("Enemy's Damage is " + botDMG);
                        break;
                    case 5:
                        botDMG += 12;
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                        } else {
                            botDMG = botDMG;
                            System.out.println("Enemy's Damage is " + botDMG);
                        }
                        
                        playerHP -= botDMG;
                        break;
                }
                
            }
            
            if (botHP < 0) {
                System.out.println("VICTORY! You Defeated the enemy");
                isDone = true;
                s.nextLine();
            } else if (playerHP < 0) {
                System.out.println("Defeat! You loss");
                isDone = true;
                s.nextLine();
            }
            
            while (isDone) {
                System.out.println("Do you want to Continue? (y/n) : ");
                String option = s.nextLine();
                
                if(option.equalsIgnoreCase("y")) {
                    System.out.println("\nPlay Again!\n");
                    isDone = false;
                } else if (option.equalsIgnoreCase("n")) {
                    System.out.println("\nExiting Game...");
                    isDone = false;
                    startGame = false;
                } else {
                    System.out.println("Invalid Input only y or n");
                }
                
            }
            
        }
        
    }
    
    static boolean isOddorEven(int i) {
        boolean OddorEven;
        if(i % 2 == 0) {
            OddorEven = true;
        } else {
            OddorEven = false;
        }
        
        return OddorEven;
        
    }
    
    static boolean isBotPassive() {
        boolean triggerPassive;
        Random random = new Random();
        
        int passive = random.nextInt(4) + 1;
        if (passive == 1) {
            triggerPassive = true;
        } else {
            triggerPassive = false;
        }
        
        return triggerPassive;
    }
    
}
