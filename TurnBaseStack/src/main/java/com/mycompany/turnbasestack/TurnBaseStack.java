/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.turnbasestack;

import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class TurnBaseStack {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Stack<Integer> lastHP = new Stack<>();
        
        double[] damages = new double[5];
        
        int gameTimer = 1;
        boolean startGame = true;
        Random random = new Random();
        
        double playerHP = 200;
        double playerDMG = 10;
        double crit = 1;
        int energy = 95;
        int botHP = 200;
        double botDMG;
        
        int randomDamage;
        int randomCrit;
        int passiveCounter = 0;
        
        boolean isCrit = true;
        boolean isDone = false;
        boolean skip = false;
        boolean isPassive = false;
        
        while (startGame) {
            
            for (double damage:damages) {
                System.out.println(damage);
            }
            
            lastHP.push(botHP);
            if (!skip) {
                energy += 2;
                System.out.println("__________________________");
                System.out.println("\nPlayer HP : " + playerHP);
                System.out.println("Energy : " + (energy));
                System.out.println("Bot HP : " + botHP);
                System.out.println("__________________________");
            }
            
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
                        
                        if (energy >= 5) {
                            botHP -= playerDMG * crit;
                            energy -= 5;
                            System.out.println("Your Damage is " + (playerDMG * crit));
                            System.out.println("You used Tackle, Bot HP is " + botHP);
                            skip = false;
                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;
                        }
                        break;
                       
                    case 2:
                        
                        if (energy >= 10) {
                            botHP -= (playerDMG + 5) * crit;
                            energy -= 10;
                            System.out.println("Your Damage is " + ((playerDMG + 5)* crit));
                            System.out.println("You used Thunderbolt, Bot HP is " + botHP);
                            skip = false;
                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;
                        }
                        break;
                        
                    case 3:
                        
                        if (energy >= 15) {
                             botHP -= 0;
                            energy -= 15;
                            crit += 0.2;
                            System.out.println("Your Damage is 0");
                            System.out.println("You used Leer, next damage will be higher. Bot HP is " + botHP);
                            skip = false;
                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;
                        }
                        break;
                        
                    case 4:
                        
                        if (energy >= 30) {
                            botHP -= (playerDMG + 30) * crit;
                            energy -= 30;
                            System.out.println("Your Damage is " + ((playerDMG + 30) * crit));
                            System.out.println("You used Hyper Beam, Bot HP is " + botHP);
                            skip = false;
                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;
                        }
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
                
                isPassive = false;
                botDMG = 10;
                randomDamage = random.nextInt(5) + 1;
                randomCrit = random.nextInt(3) + 1;
                isCrit = random.nextBoolean();
                double newBotDMG = 0;
                
                if (passiveCounter == 5) { 
                    passiveCounter = 0;
                    Arrays.fill(damages, 0);
                    newBotDMG = playerPassive(damages, botDMG);
                    isPassive = true;
                }
                
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
                        
                        
                        damages[passiveCounter] = botDMG;
                        if (isPassive) {
                            botDMG = newBotDMG;
                            System.out.println("Player's passive is activated");
                            if (botDMG == 0) {
                                System.out.println("Enemy's damage is deflected");
                            } else {
                                System.out.println("Enemy's damage is reduced by 25%");
                            }
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
                        
                        damages[passiveCounter] = botDMG;
                        
                        if (isPassive) {
                            botDMG = newBotDMG;
                            System.out.println("Player's passive is activated");
                            if (botDMG == 0) {
                                System.out.println("Enemy's damage is deflected");
                            } else {
                                System.out.println("Enemy's damage is reduced by 25%");
                            }
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
                        
                        damages[passiveCounter] = botDMG;
                        
                        if (isPassive) {
                            botDMG = newBotDMG;
                            System.out.println("Player's passive is activated");
                            if (botDMG == 0) {
                                System.out.println("Enemy's damage is deflected");
                            } else {
                                System.out.println("Enemy's damage is reduced by 25%");
                            }
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
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                        } else {
                            botDMG = botDMG;
                        }
                        
                        damages[passiveCounter] = botDMG;
                        
                        if (isPassive) {
                            botDMG = newBotDMG;
                            System.out.println("Player's passive is activated");
                            if (botDMG == 0) {
                                System.out.println("Enemy's damage is deflected");
                            } else {
                                System.out.println("Enemy's damage is reduced by 25%");
                            }
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
                        }
                        damages[passiveCounter] = botDMG;
                        
                        if (isPassive) {
                            botDMG = newBotDMG;
                            System.out.println("Player's passive is activated");
                            if (botDMG == 0) {
                                System.out.println("Enemy's damage is deflected");
                            } else {
                                System.out.println("Enemy's damage is reduced by 25%");
                            }
                        }
                        
                        playerHP -= botDMG;
                        System.out.println("Enemy's Damage is " + botDMG);
                        break;
                }
                
                passiveCounter++;
                
            }
            
            
            if (botHP < 0) {
                System.out.println("\nVICTORY! You Defeated the enemy\n");
                isDone = true;
                s.nextLine();
            } else if (playerHP < 0) {
                System.out.println("\nDefeat! You loss\n");
                isDone = true;
                s.nextLine();
            }
            
            while (isDone) {
                System.out.print("Do you want to Continue? (y/n) : ");
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
    
    static double playerPassive(double[] damages, double botDMG) {
        
        double totalDamage = 0;
        boolean isFullDeflect = false;
        
        for (double damage:damages) {
            totalDamage += damage;
        }
        
        if (totalDamage >= 50) {
            botDMG = 0;
        } else {
            botDMG *= 0.75;
        }
        return botDMG;
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
