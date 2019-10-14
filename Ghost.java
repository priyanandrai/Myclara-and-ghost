// import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
/**
 * Ghost Class
 * 
 * Available functions (see Assignment document for explanations on what each function does):
 * treeFront, treeAbove, treeBelow, treeToLeft, treeToRight,
 * getDirection, setDirection,
 * move,
 * isScared,
 * animate, animateDead, animateScared,
 * getClara, getGhostHealer,
 * isAboveMe, isBelowMe, isToMyLeft, isToMyRight,
 * makeClaraDead,
 * playGhostEatenSound,
 * isPacmanIntroStillPlaying,
 * wrapAroundWorld
 */

class Ghost extends Character
{
    //Add and initialise Ghost variables here

    /**
     * Act method, runs on every frame
     */
    //The speed of the ghost
    public final double speed = 4;
    //A count incrementing continously 
    public int count = 0;
    //String definition
    public final String UP = "up";
    public final String DOWN = "down";
    public final String LEFT = "left";
    public final String RIGHT = "right";
    //to detect intersection which mean how many chouces are available for ghost
    public int intersectionCount = 0;
    //A flag to set direction
    public boolean intersectionFlag = false;
    //A flag to make sure that the ghost is move forward from the inersection point
    public boolean intersectionFlag2 = false;
    //To move into what direction
    private String chosenDirection = "";
    public boolean initialFlag = false;

    String directions[] = {
        "up",
        "down",
        "left",
        "right"
    };


    public void act()
    { //Make the Ghost do things here

        if (!isPacmanIntroStillPlaying()) //First time it will check if the introduction playing.
            if (!isScared()) { //Checking if the clara has eaten the mushroom
                move(speed);
                wrapAroundWorld(); //A helping function to move off the other side of the world 

                //Part 6
                animateGhost(); //it will animate the ghost

                //Part 7
                if (treeFront()) { //Will check the collisions
                    // moveFlag=false;
                }

                //Part 8
                artificialIntlgncandIntrsctn(); // The random path and everything

                //Part9
                if (intersects(getClara())) { //Check if the clara is intesecting with the ghosts
                    makeClaraDead(); //a helping function
                }

                //Part11
                GhostsAndRegeneration(); ///regenerate when clara will make ghost dead after eating mushroom
            }
        else {
            animateScared(); //This means the clara has eaten the mushroom so animate scare
            if (intersects(getClara())) { //If intersect while scared it is dead
                animateDead();
                playGhostEatenSound();
            }
        }
    }

    //Part 6
    void animateGhost() {
        //Only after the count is 5(Like a delay)
        if (count % 5 == 0) {
            animate();
        }
        count++;
    }

    void artificialIntlgncandIntrsctn() {
        int intrsctnCount = interSectionCount(); //Number of the options available
        //All possibilities
        if (intrsctnCount == 1 && treeFront()) {
            // System.out.print(1);
            if (treeAbove() && treeBelow() && treeToLeft()) {
                chosenDirection = RIGHT;
            }
            if (treeAbove() && treeBelow() && treeToRight()) {
                chosenDirection = LEFT;
            }
            if (treeAbove() && treeToLeft() && treeToRight()) {
                chosenDirection = DOWN;
            }
            if (treeBelow() && treeToRight() && treeToLeft()) {
                chosenDirection = UP;
            }
            //This means that a decision is made so change the direction
            intersectionFlag = true;
        }
        else if (intrsctnCount == 2 && treeFront()) {
            // System.out.print(2);
            // if ((treeAbove() && treeBelow()) || (treeAbove() && treeToRight()) || (treeToRight() && treeBelow())) {
            //     chosenDirection = LEFT;
            // }
            // else if ((treeAbove() && treeBelow()) || (treeAbove() && treeToLeft()) || (treeBelow() && treeToLeft())) {
            //     chosenDirection = RIGHT;

            // }
            // else if ((treeToLeft() && treeToRight()) || (treeToLeft() && treeBelow()) || (treeToRight() && treeBelow())) {
            //     chosenDirection = UP;
            // }
            // else if ((treeToLeft() && treeToRight()) || (treeAbove() && treeToLeft()) || (treeAbove() && treeToRight())) {
            //     chosenDirection = DOWN;
            // }
            if ((treeAbove() && treeBelow() && isToMyLeft(getClara())) || (treeAbove() && treeToRight() && isToMyLeft(getClara()))) {
                chosenDirection = LEFT;
            }
            if ((treeAbove() && treeBelow() && isToMyRight(getClara())) || (treeAbove() && treeToLeft() && isToMyRight(getClara()))) {
                chosenDirection = RIGHT;
            }
            if ((treeToLeft() && treeToRight() && isAboveMe(getClara())) || (treeToLeft() && treeBelow() && isAboveMe(getClara()))) {
                chosenDirection = UP;
            }
            if ((treeToLeft() && treeToRight() && isBelowMe(getClara())) || (treeAbove() && treeToLeft() && isBelowMe(getClara()))) {
                chosenDirection = DOWN;
            }
            //This means that a decision is made so change the direction
            intersectionFlag = true;
        }
        else if (intrsctnCount >= 3) {
            //Select random location
            if (initialFlag) {
                chosenDirection = directions[Clara.getRandomNumber(3)];
                // initialFlag=false;
            }
            else if (isToMyLeft(getClara()) && !treeToLeft()) {
                chosenDirection = LEFT;
            }
            else if (isToMyRight(getClara()) && !treeToRight()) {
                chosenDirection = RIGHT;
            }
            else if (isAboveMe(getClara()) && !treeAbove()) {
                chosenDirection = UP;
            }
            else if (isBelowMe(getClara()) && !treeBelow()) {
                chosenDirection = DOWN;
            }
            // chosenDirection = directions[Clara.getRandomNumber(3)];
            ///To make sure that the intersection has passed from
            if (!intersectionFlag2) {
                intersectionFlag = true;
                intersectionFlag2 = true;
            }
        }

        if (intersects(getGhostHealer())) {
            setDirection(UP);
            initialFlag = true;
        }
        //A delay to check the intersection passed
        if (count % 13 == 0)
            intersectionFlag2 = false;
        if (intersectionFlag) {
            setDirection(chosenDirection);
            intersectionFlag = false;
        }
        intersectionCount = 0;
    }


    //A function to check the surrounding when the ghost is at specific position
    int interSectionCount() {
        if (!treeAbove())
            intersectionCount++;
        if (!treeBelow())
            intersectionCount++;
        if (!treeToLeft())
            intersectionCount++;
        if (!treeToRight())
            intersectionCount++;
        if (count >= 3) {
            intersectionFlag = false;
        }
        return intersectionCount;
    }


    void GhostsAndRegeneration() {
        // int intCount = interSectionCount();
        // if (intCount == 1) {
        //     if (treeAbove() && treeBelow() && treeLeft()) {
        //         chosenDirection = RIGHT;
        //     }
        //     else if (treeAbove() && treeBelow() && treeToRight()) {
        //         chosenDirection = LEFT;
        //     }
        //     else if (treeAbove() && treeLeft() && treeToRight()) {
        //         chosenDirection = DOWN;
        //     }
        //     else if (treeBelow() && treeToRight() && treeLeft()) {
        //         chosenDirection = UP;
        //     }
        //     intersectionFlag = true;
        // }
        // if (intCount == 2) {
        //     if ((treeAbove() && treeBelow() && isToMyLeft(getClara())) || (treeAbove() && treeToRight() && isToMyLeft(getClara())) || (treeToRight() && treeBelow() && isToMyLeft(getClara()))) {
        //         chosenDirection = LEFT;
        //     }
        //     if ((treeAbove() && treeBelow() && isToMyRight(getClara())) || (treeAbove() && treeToLeft() && isToMyRight(getClara())) || (treeBelow() && treeToLeft() && isToMyRight(getClara()))) {
        //         chosenDirection = RIGHT;
        //     }
        //     if ((treeToLeft() && treeRight() && isAboveMe(getClara())) || (treeToLeft() && treeBelow() && isAboveMe(getClara())) || (treeToRight() && treeBelow() && isAboveMe(getClara()))) {
        //         chosenDirection = UP;
        //     }
        //     if ((treeToLeft() && treeRight() && isBelowMe(getClara())) || (treeAbove() && treeToLeft() && isBelowMe(getClara())) || (treeAbove() && treeToRight() && isBelowMe(getClara()))) {
        //         chosenDirection = DOWN;
        //     }
        // }
        // else if (intCount == 3) {
        //     if (isToMyLeft(getClara()) && !treeToLeft()) {
        //         chosenDirection = LEFT;
        //     }
        //     else {
        //         chosenDirection = directions[count % 4];
        //     }
        //     if (isToMyRight(getClara()) && !treeToRight()) {
        //         chosenDirection = RIGHT;
        //     }
        //     else {
        //         chosenDirection = directions[count % 4];
        //     }
        //     if (isAboveMe(getClara()) && !treeAbove()) {
        //         chosenDirection = UP;
        //     }
        //     else {
        //         chosenDirection = directions[count % 4];
        //     }
        //     if (isBelowMe(getClara()) && !treeBelow()) {
        //         chosenDirection = DOWN;
        //     }
        //     else {
        //         chosenDirection = directions[count % 4];
        //     }
        // }
    }
    //Give the Ghost functions here
}
