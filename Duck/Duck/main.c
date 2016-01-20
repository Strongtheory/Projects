//Abhishek Deo

#include "mylib.h"
#include "right.h"
#include "left.h"
#include "dead.h"
#include "back.h"
#include "scope.h"
#include <debugging.h>
#include <stdlib.h>
#include <stdio.h>
//prototypes
void title();
void start();
void game();
void results();
void gameplay();
void duck();
void clear();
void drawDuck();
void drawScope();
void collision();
void check();
void info();
void reset();


    int state; //states of the game
    
    DUCK ducks[15];
    SCOPE view;
    int speed = 2;
    int dWidth = 20;
    int dHeight = 20;

    int crossR = 80;
    int crossC = 120;

    int count;
    int y;
    int x;

    int ammo;
    
    int won;
    int lost;

    int alive;

    unsigned int lastButtons, buttons;
    char buffer[4][41]; 

int main(void)
{
    REG_DISPCNT = MODE3 | BG2_ENABLE;
    buttons = BUTTONS;
    y = crossR+5;
    x = crossC+5;
    won=0;
    lost=0;

    count=15;
    
    state = TITLE;

    while(1){

        lastButtons = buttons;
        buttons = BUTTONS;
        y = crossR+3;
        x = crossC+3;

        switch(state)
        {
            case TITLE:
                title();
                break;
            case START:
                start();
                break;
            case GAME:
                game();
                break;
            case RESULTS:
                results();
                break;
        }
    }
}
void title()
{
    ammo = 30;
    won = 0;
    lost = 0;

    drawString(50, 50, "DUCK HUNT", WHITE);
    drawString(80,50,"A to shoot", WHITE);
    drawString(90,50,"Use Arrows to move", WHITE);
    drawString(70, 50, "Enter to Begin", WHITE);
    
    if(BUTTON_PRESSED(BUTTON_START))
    {
        count = 10;
        state = START;
        fillScreen3(BLACK);
    }
}

void start()
{   
    ammo = 30;
    alive = count;
    if (BUTTON_PRESSED(BUTTON_B))
    {
        reset();
    }
    if(BUTTON_PRESSED(BUTTON_START))
    {
        duck();
        state = GAME;
    }
    waitForVBlank();
    drawString(50,50,"Press Enter to start", WHITE);
    drawString(80,50,"Press B to reset", WHITE);
    sprintf(buffer[1], "Ammo: %d", ammo);
    drawString(60, 50, buffer[1], WHITE);
}

void game()
{
    gameplay();
    if(won)
    {
        won=0;
        fillScreen3(BLACK);
        state = RESULTS;
    }
    if(lost)
    {
        fillScreen3(BLACK);
        state = RESULTS;
    }
}

void gameplay()
{
    //clear();
    if(KEY_DOWN_NOW(BUTTON_UP))
    {
        crossR-=speed;
    }
    
    if(KEY_DOWN_NOW(BUTTON_DOWN))
    {
        crossR+=speed;
    }

    if(KEY_DOWN_NOW(BUTTON_LEFT))
    {
        crossC-=speed;
    }
    
    if(KEY_DOWN_NOW(BUTTON_RIGHT))
    {
        crossC+=speed;
    }
    for(int i=0; i < count; i++){
        if(ducks[i].alive)
        {
            if (ducks[i].row+dHeight >= 160)
            {
                ducks[i].rLevel = (-1*ducks[i].rLevel);
                ducks[i].row = 160 - dHeight;
            }
            if (ducks[i].row <= 0)
            {
                ducks[i].rLevel = (-1*ducks[i].rLevel);
                ducks[i].row = 1;
            }
            if (ducks[i].col+dWidth >= 240)
            {
                ducks[i].cLevel = (-1*ducks[i].cLevel);
                ducks[i].col = 239-dWidth;
            }
            if (ducks[i].col <= 0)
            {
                ducks[i].cLevel = (-1*ducks[i].cLevel);
                ducks[i].col= 1;
            }
        }
        else
        {
            if(ducks[i].cLevel != 0)
            {
                ducks[i].direction = ducks[i].cLevel;
                ducks[i].cLevel=0;
            }
            ducks[i].rLevel=1;
            if( (ducks[i].row+dHeight) == 160)
                ducks[i].rLevel=0;
        }
        ducks[i].row += ducks[i].rLevel;
        ducks[i].col += ducks[i].cLevel;
    }
    check();
    waitForVBlank();
    fillScreen3(BLACK);
    drawDuck();
    drawScope();
    if(BUTTON_PRESSED(BUTTON_A))
    {
        ammo--;
        collision();
    }
    info();
    if (BUTTON_PRESSED(BUTTON_B))
    {
        reset();
    }
}

void results()
{
    sprintf(buffer[1], "Left Alive: %d", alive);
    sprintf(buffer[2], "Ammo: %d", ammo);

    drawString(60, 50, buffer[1], WHITE);
    drawString(70, 50, buffer[2], WHITE);
    drawString(80, 50,"Press Enter for new game", WHITE);
    if(BUTTON_PRESSED(BUTTON_START))
    {
        state=TITLE;
        fillScreen3(BLACK);
    }
}

void duck()
{
    for(int i=0; i < count; i++){
        ducks[i].row=rand()%161-dHeight;
        ducks[i].col=rand()%241-dWidth;
        ducks[i].rLevel=1;
        ducks[i].cLevel=-1;
        ducks[i].width=dWidth;
        ducks[i].height=dHeight;
        ducks[i].alive=1;
    }
}

void clear()
{
    for(int i=0; i < count; i++){
        drawSub(ducks[i].row,ducks[i].col, 240, 
            ducks[i].row, ducks[i].col,
            dHeight,dWidth, index);
    }
    drawSub(crossR,crossC, 240, crossR,crossC,5,5, index);
    drawSub(140,0,240,140,0,20,240,index);
}

void drawDuck()
{
    for(int i=0; i < count; i++){
        if(ducks[i].alive)
        {
            if(ducks[i].cLevel>0)
                drawImage3(ducks[i].row,ducks[i].col,dHeight,dWidth,right);
            else
                drawImage3(ducks[i].row,ducks[i].col,dHeight,dWidth,left);
        }
        else
        {
            if(ducks[i].row+dHeight>=160)
            {
                //They keep on going down when dead.
            }
            else
            {
                if(ducks[i].direction>0)
                    drawImage3(ducks[i].row,ducks[i].col,dHeight,dWidth,dead);
                else
                    drawImage3(ducks[i].row,ducks[i].col,dHeight,dWidth,dead);
            }
        }   
    }
}

void drawScope()
{
    view.row = crossR;
    view.col = crossC;
    view.width = 10;
    view.height = 10;
    drawImage3(view.row, view.col, 
        view.height, view.width, scope);
}

void collision()
{
    y = crossR+5;
    x = crossC+5;
    for(int i=0; i < count; i++){
        if((ducks[i].row<y) && ((ducks[i].row+dHeight)>y)) {
            if( ducks[i].col<x && ((ducks[i].col+dWidth)>x))
            {
                if (ducks[i].alive)
                {
                    ducks[i].alive=0;
                    alive--;
                }
            }
        }
    }
}

void check()
{
    if(alive == 0)
        won=1;
    else if(ammo == 0)
        lost=1;
}

void info()
{
    sprintf(buffer[1], "Ammo: %d", ammo);
    sprintf(buffer[2], "Alive: %d", alive);

    drawString(140, 75, buffer[1], WHITE);
    drawString(150, 75, buffer[2], WHITE);
}

void reset()
{
    fillScreen3(BLACK);
    state = TITLE;
}
