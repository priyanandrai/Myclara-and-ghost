/**
 * MyClara
 * 
 * Available functions (see Assignment document for explanations on what each function does):
 * treeFront, ghostWallFront,
 * getDirection, setDirection,
 * move,
 * makeScared, isScared,
 * animate, animateDead, 
 * onLeaf, removeLeaf, 
 * onMushroom, removeMushroom,
 * allLeavesEaten, 
 * isClaraDead,
 * playClaraDieSound, isClaraDieSoundStillPlaying,
 * playLeafEatenSound,
 * playPacmanIntro, isPacmanIntroStillPlaying,
 * wrapAroundWorld,
 * getCurrentLevelNumber, advanceToLevel
 */
class MyClara extends Clara
{
    // Please leave this first level here,
    // until after you've completed \"Part 12 -
    // Making and Adding Levels\"
    // Movement constants
public final char[][] LEVEL_1 = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','$','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','$','#'},
            {'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
            {'#','.','.','.','.','#','.','.','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#',' ','#','#','|','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ',' ','.',' ',' ','#','%','?','%','#',' ',' ','.',' ',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','#','#'},
            {'#','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','.','#'},
            {'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
            {'#','$','.','#','.','.','.','.','.','@','.','.','.','.','.','.','.','$','.'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };
          public final char[][] LEVEL_2 = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','#','.','.','.','.','.','.','.','@','.','.','.','.','.','.','.','$','#'},
            {' ','.','$','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.',' '},
            {'#','.','.','.','.','#','.','.','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#',' ','#','#','|','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ',' ','.',' ',' ','#','%','?','.','#',' ',' ','.',' ',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','#','#','#','#',' ','#','.','#','#','#','#'},
            {' ','.','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','.',' '},
            {'#','.','#','#','.','#','#','.','.','#','.','.','#','#','.','#','#','.','#'},
            {'#','$','.','#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };

         public final char[][] LEVEL_3 = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','#','.','.','.','.','.','.','.','@','.','.','.','.','.','.','.','$','#'},
            {' ','.','$','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.',' '},
            {'#','.','.','.','.','#','.','.','.','.','.','.','.','#','.','.','.','.','#'},
            {'#','#','#','#','.','#',' ','#','#','|','#','#',' ','#','.','#','#','#','#'},
            {' ',' ',' ',' ','.',' ',' ','#','%','?','%','#',' ',' ','.',' ',' ',' ',' '},
            {'#','#','#','#','.','#',' ','#','%','?','%','#',' ','#','.','#','#','#','#'},
            {' ','.','.','.','.','.','.','#','#','#','#','#','.','.','.','.','.','.',' '},
            {'#','.','#','#','.','#','#','.','.','#','.','.','#','#','.','#','#','.','#'},
            {'#','$','.','#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };

    public final String UP = "up";
    public final String DOWN = "down";
    public final String LEFT = "left";
    public final String RIGHT = "right";
    public final double speed = 6;
    boolean moveFlag = false;
    public int count = 0;
    public boolean newLevel = true;
    boolean gameOver = false;
    int levelCount = 1;

    

    // Add and initialise Clara's variables here

    /**
     * Act method
     * 
     * Runs of every frame
     */
    public void act()
    {
        // System.out.println(getCurrentLevelNumber());
        //The boolean flag newLevel is made which will be true only when the game starts and clara will not move
        if (newLevel)
        {
            //Will play sound when new level
            playPacmanIntro();
            newLevel = false;
        }
        //Will true only if the game is not over aor introduction is playing      
      if (!isPacmanIntroStillPlaying() && !gameOver) {
        if (!isClaraDead()) {

            //Initial Key pressed
            if (moveFlag)
                move(speed);
            //Part 1
            //Will do the keyboard function
            CheckkeyPressed();
            wrapAroundWorld();

            //Part 2
            //Animate the clara movement
            animateClara();

            //Part 3
            //To check the collision of the clara
            ClaraCollision();

            //Part 4
            //A function to eat the leaves and check winning
            EatingLeavesandWinning();
        }
        else {
            animateDead();
            if(!gameOver)
            playClaraDieSound();
                        gameOver = true;
        }

        //Part 10
        //remove mushroom if on top
        if (onMushroom()) {
            removeMushroom();
            makeScared();
        }
    }
    }
void CheckkeyPressed() {
    if (Keyboard.isKeyDown(UP)) {
        // System.out.println(UP);
        setDirection((UP));
        moveFlag = true;
    }
    else if (Keyboard.isKeyDown(DOWN)) {
        // System.out.println(DOWN);
        setDirection((DOWN));
        moveFlag = true;
    }
    else if (Keyboard.isKeyDown(LEFT)) {
        // System.out.println(LEFT);
        setDirection((LEFT));
        moveFlag = true;
    }
    else if (Keyboard.isKeyDown(RIGHT)) {
        // System.out.println(RIGHT);
        setDirection((RIGHT));
        moveFlag = true;
    }
}

void animateClara() {
    //Only after the count is 5
    if (count % 6 == 0) {
        animate();
    }
    count++;
}

void EatingLeavesandWinning() {
    if (!allLeavesEaten())
        if (onLeaf()) {
            removeLeaf();
            playLeafEatenSound();
        }
    if (allLeavesEaten()) {
        advanceToLevel(LEVEL_2);
        newLevel = true;
    }
}

void ClaraCollision() {
    if (ghostWallFront()) {
        moveFlag = false;
    }
    treeFront();
    // moveFlag = false;
}

//Give Clara functions here
}
