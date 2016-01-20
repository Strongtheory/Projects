//Author: Abhishek Deo
#include "mylib.h"

u16* videoBuffer = (u16*) 0x6000000;

void fillScreen3(unsigned short color)
{
	unsigned short c = color;
	DMANow(3, &c, videoBuffer, (240*160) | DMA_SOURCE_FIXED);
}

//Tonc!!!!!!
void drawImage3(int row, int col, int width, int height, const u16* image)
{
	int i;
	for (i = 0; i < height; i++)
	{
		DMANow(3, &image[OFFSET(i, 0, width)], &videoBuffer[OFFSET(row+i, col, SCREENWIDTH)], (width));
	}
}

void setPixel(int row, int col, u16 color)
{
	videoBuffer[OFFSET(row, col, 240)] = color;
}

void drawSub(int srcRow, int srcCol, int srcWidth, int row, int col, int height, int width, const u16* image)
{
	int i = 0;
	while(i < height)
	{
		DMANow(3, &image[OFFSET(srcRow+i,srcCol, srcWidth)], 
			&videoBuffer[OFFSET(row+i, col, SCREENWIDTH)], (width));
		i++;
	}
}

//class
void DMANow(int channel, const short unsigned int* source, 
	void* destination, unsigned int control)
{
	DMA[channel].src = source;
	DMA[channel].dst = destination;
	DMA[channel].cnt = DMA_ON | control;
}

void waitForVBlank()
{
    while(SCANLINECOUNTER > 160);
    while(SCANLINECOUNTER < 160);
}