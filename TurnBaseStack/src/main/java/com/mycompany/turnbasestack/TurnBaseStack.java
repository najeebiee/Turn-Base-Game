
package com.mycompany.turnbasestack;

import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;

public class TurnBaseStack {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Stack<Integer> lastHP = new Stack<>();
        Stack<String> jinguPassive = new Stack<>();
        
        double[] damages = new double[5];
        
        int gameTimer = 1;
        boolean startGame = true;
        Random random = new Random();
        
        double playerHP = 200;
        double playerDMG = 10;
        double crit = 1;
        int energy = 95;
        int botHP = 200;
        boolean bot_healed = false;
        int bot_max_hp = 200;
        double botDMG;
        
        int randomDamage;
        int randomCrit;
        int passiveCounter = 0;
        int jinguBuffLeft = 0;
        int jinguHits = 0;

        
        boolean isCrit = true;
        boolean isDone = false;
        boolean skip = false;
        boolean isPassive = false;
        boolean JinguActive = false;
        
        System.out.println("You encountered : " + randomName(random));
        
        while (startGame) {
            
            lastHP.push(botHP);
            if (!skip) {
                energy += 5;
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

                            double totalDamage = playerDMG * crit;

                            if (JinguActive && jinguBuffLeft > 0) {
                                totalDamage += 5 * crit; 
                                jinguBuffLeft--;

                                if (jinguBuffLeft == 0) {
                                    JinguActive = false;
                                    playerDMG -= 5;
                                    jinguHits = 0;
                                    System.out.println("Jingu Mastery has worn off.");
                                }
                            }

                            botHP -= totalDamage;
                            energy -= 5;
                            System.out.println("Your Damage is " + totalDamage);
                            System.out.println("You used Tackle, Bot HP is " + botHP);
                            skip = false;

                            jinguPassive.push("hit");

                            if (jinguPassive.size() > 4) {
                                jinguPassive.remove(0);
                            }

                            if (jinguPassive.size() == 4 && jinguPassive.stream().allMatch(h -> h.equals("hit"))) {
                                JinguActive = true;
                                jinguBuffLeft = 3;
                                System.out.println("Jingu Mastery activated! Bonus damage for 3 turns.");
                            }

                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;

                            jinguPassive.push("miss");

                            if (jinguPassive.size() > 4) {
                                jinguPassive.remove(0);
                            }
                        }

                        break;

                       
                    case 2:
                    if (energy >= 10) {
                        
                        double totalDamage = (playerDMG + 5) * crit;

                        if (JinguActive && jinguBuffLeft > 0) {
                            
                            totalDamage += 5 * crit;
                            jinguBuffLeft--;
                            
                            if (jinguBuffLeft == 0) {
                                JinguActive = false;
                                jinguHits = 0;
                                System.out.println("Jingu Mastery has worn off.");
                            }
                        }

                        botHP -= totalDamage;
                        energy -= 10;
                        System.out.println("Your Damage is " + totalDamage);
                        System.out.println("You used Thunderbolt, Bot HP is " + botHP);
                        skip = false;
                        
                        // ✅ Record a successful hit
                        jinguPassive.push("hit");

                        // ✅ Count only the last 4 hits
                        if (jinguPassive.size() > 4) {
                            jinguPassive.remove(0); // remove oldest entry
                        }

                           // ✅ Check if last 4 entries are all "hit"
                        if (jinguPassive.size() == 4 && jinguPassive.stream().allMatch(h -> h.equals("hit"))) {
                            JinguActive = true;
                            jinguBuffLeft = 3;
                            System.out.println("Jingu Mastery activated! Bonus damage for 3 turns.");
                        }

                        jinguHits++;
                        
                        if (jinguHits >= 4 && !JinguActive) {
                            JinguActive = true;
                            jinguBuffLeft = 3; // lasts for 3 turns
                            System.out.println("Jingu Mastery activated! Bonus damage for 3 turns.");
                        }
                        
                    } else {
                        System.out.println("Insufficient Energy");
                        skip = true;
                    }
                    break;

                        
                    case 3:
                        
                        if (energy >= 15) {
                            jinguPassive.push("hit");
                            botHP -= 0;
                            energy -= 15;
                            crit += 0.2;
                            System.out.println("Your Damage is 0");
                            System.out.println("You used Leer, next damage will be higher. Bot HP is " + botHP);
                            skip = false;
                        } else {
                            jinguPassive.push("hit");
                            System.out.println("Insufficient Energy");
                            skip = true;
                        }
                        break;
                        
                    case 4:

                        if (energy >= 30) {

                            double totalDamage = (playerDMG + 30) * crit;

                            if (JinguActive && jinguBuffLeft > 0) {
                                totalDamage += 5 * crit; 
                                jinguBuffLeft--;

                                if (jinguBuffLeft == 0) {
                                    JinguActive = false;
                                    playerDMG -= 5;
                                    jinguHits = 0;
                                    System.out.println("Jingu Mastery has worn off.");
                                }
                            }

                            botHP -= totalDamage;
                            energy -= 30;
                            System.out.println("Your Damage is " + totalDamage);
                            System.out.println("You used Hyper Beam, Bot HP is " + botHP);
                            skip = false;

                            jinguPassive.push("hit");

                            if (jinguPassive.size() > 4) {
                                jinguPassive.remove(0);
                            }

                            if (jinguPassive.size() == 4 &&
                                jinguPassive.get(0).equals("hit") &&
                                jinguPassive.get(1).equals("hit") &&
                                jinguPassive.get(2).equals("hit") &&
                                jinguPassive.get(3).equals("hit")) {
                                JinguActive = true;
                                jinguBuffLeft = 3;
                                System.out.println("Jingu Mastery activated! Bonus damage for 3 turns.");
                            }

                        } else {
                            System.out.println("Insufficient Energy");
                            skip = true;

                            jinguPassive.push("miss");

                            if (jinguPassive.size() > 4) {
                                jinguPassive.remove(0);
                            }
                        }

                        break;

                        
                    case 5:
                        System.out.println("Invalid Input only 1 - 4");
                        skip = true;
                    
                }
                
            } else if(!skip){
                System.out.println("\nEnemy's Turn!\n");
                
                if (isBotPassive() && botHP <= 100) {
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

                } else if (botHP <= 30 && !bot_healed) {

                    System.out.println("Bot uses Heal!");
                    botHP += 50;
                    if (botHP > bot_max_hp) botHP = bot_max_hp;
                    bot_healed = true;
                    System.out.println("Bot healed to " + botHP + " HP");
                    
                }
                switch(randomDamage) {
                    
                    case 1:
                        if (isCrit) {
                            if (randomCrit == 1) {botDMG *= 1.2;}
                            else if (randomCrit == 2) {botDMG *= 1.5;}
                            else if (randomCrit == 3) {botDMG *= 1.8;}
                            System.out.println("Critical Damage! Enemy's Damage is " + botDMG);
                            
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
                        
                        jinguHits++;
                        if (jinguHits >= 4) {
                            JinguActive = true;
                            jinguBuffLeft = 3; // lasts for 3 turns
                            System.out.println("Jingu Mastery activated! Bonus damage for 3 turns.");
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
    
    static String randomName(Random random) {
        
        LinkedList<String> fName = new LinkedList<>();
        LinkedList<String> lName = new LinkedList<>();
        
        String[] firstName = {"John", "Jane", "Michael", "Emily", "David", "Sarah", "Robert", "Jessica", "William", "Linda",
                              "James", "Mary", "Christopher", "Patricia", "Daniel", "Jennifer", "Matthew", "Elizabeth", "Anthony", "Barbara",
                              "Joshua", "Susan", "Andrew", "Margaret", "Joseph", "Karen", "Thomas", "Nancy", "Charles", "Lisa",
                              "Mark", "Betty", "Paul", "Dorothy", "Steven", "Sandra", "Kevin", "Ashley", "Brian", "Kimberly",
                              "George", "Donna", "Edward", "Carol", "Ronald", "Michelle", "Timothy", "Emily", "Jason", "Amanda"};
        
        String[] lastName = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
                             "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
                             "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
                             "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
                             "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"};

        for (int x = 0; x < firstName.length; x++) {
            fName.add(firstName[x]);
            lName.add(lastName[x]);
        }
        
        
        int size = fName.size() - 1;
        int randInt; 
        String first, last, randName;
        
        randInt = random.nextInt(size);
        first = fName.get(randInt);
        randInt = random.nextInt(size);
        last = lName.get(randInt);
        randName = first + " " + last;
        
        return randName;
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
