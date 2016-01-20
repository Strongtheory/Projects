//Abhishek Deo
#ifndef MYLIB_H
#define	MYLIB_H

/*pre-processor declarations*/
typedef unsigned char u8;
typedef unsigned short u16;
typedef unsigned int u32;

typedef volatile unsigned char vu8;
typedef volatile unsigned short vu16;
typedef volatile unsigned int vu32;

typedef signed char s8;
typedef signed short s16;
typedef signed int s32;

typedef volatile signed char vs8;
typedef volatile signed short vs16;
typedef volatile signed int vs32;


#define REG_DISPCNT *(u16 *)0x4000000
#define SCANLINECOUNTER (*(vu16 *)0x4000006)

#define MODE3 3
#define BG2_ENABLE (1<<10)

#define HEIGHT 160
#define WIDTH 240
#define SCREENWIDTH 240

//States
#define TITLE 1
#define START 2
#define GAME 3
#define RESULTS 4

//RGB macro
//2^5 = 32 so range is from 0 to 31
#define COLOR(r,g,b) ((r) | (g) << 5 | (b) << 10)
#define RED COLOR(31,0,0)
#define WHITE COLOR(31,31,31)
#define BLUE COLOR(0,0,31)
#define YELLOW COLOR(31, 31, 0)
#define BLACK 0

#define OFFSET(r,c, numcolumn) ((r)*(numcolumn)+(c))

//Inputs
#define BUTTON_A	(1<<0)
#define BUTTON_B	(1<<1)
#define BUTTON_SELECT	(1<<2)
#define BUTTON_START	(1<<3)
#define BUTTON_RIGHT	(1<<4)
#define BUTTON_LEFT	(1<<5)
#define BUTTON_UP	(1<<6)
#define BUTTON_DOWN	(1<<7)
#define BUTTON_R 	(1<<8)
#define BUTTON_L 	(1<<9)

#define BUTTONS (*(vu16*)0x04000130)

#define BUTTON_HELD(mask) (((mask) & buttons) == (mask))
#define BUTTON_PRESSED(mask) ((((mask) & lastButtons) != (mask)) && (BUTTON_HELD(mask)))
#define KEY_DOWN_NOW(key)  (~(BUTTONS) & key)

//DMA
/*-----------------------------------------------------------------*/
typedef struct
{
	const volatile void *src;
	volatile void *dst;
	volatile u32 cnt;
} DMAREC;

#define DMA ((volatile DMAREC *)0x040000B0)

//Channel 0
#define REG_DMA0SAD         *(vu32*)0x40000B0  /* source address*/
#define REG_DMA0DAD         *(vu32*)0x40000B4  /* destination address*/
#define REG_DMA0CNT         *(vu32*)0x40000B8  /* control register*/

//Channel 1
#define REG_DMA1SAD         *(vu32*)0x40000BC  /* source address*/
#define REG_DMA1DAD         *(vu32*)0x40000C0  /* destination address*/
#define REG_DMA1CNT         *(vu32*)0x40000C4  /* control register*/

//Channel 2
#define REG_DMA2SAD         *(vu32*)0x40000C8  /* source address*/
#define REG_DMA2DAD         *(vu32*)0x40000CC  /* destination address*/
#define REG_DMA2CNT         *(vu32*)0x40000D0  /* control register*/

//Channel 3
#define REG_DMA3SAD         *(vu32*)0x40000D4   /* source address*/
#define REG_DMA3DAD         *(vu32*)0x40000D8  /* destination address*/
#define REG_DMA3CNT         *(vu32*)0x40000DC  /* control register*/

//Define DMA
#define DMA_CHANNEL_0 0
#define DMA_CHANNEL_1 1
#define DMA_CHANNEL_2 2
#define DMA_CHANNEL_3 3

#define DMA_DESTINATION_INCREMENT (0 << 21)
#define DMA_DESTINATION_DECREMENT (1 << 21)
#define DMA_DESTINATION_FIXED (2 << 21)
#define DMA_DESTINATION_RESET (3 << 21)

#define DMA_SOURCE_INCREMENT (0 << 23)
#define DMA_SOURCE_DECREMENT (1 << 23)
#define DMA_SOURCE_FIXED (2 << 23)

#define DMA_REPEAT (1 << 25)

#define DMA_16 (0 << 26)
#define DMA_32 (1 << 26)

#define DMA_NOW (0 << 28)
#define DMA_AT_VBLANK (1 << 28)
#define DMA_AT_HBLANK (2 << 28)
#define DMA_AT_REFRESH (3 << 28)

#define DMA_IRQ (1 << 30)
#define DMA_ON (1 << 31)

#define START_ON_FIFO_EMPTY 0x30000000
/*-----------------------------------------------------------------*/

typedef struct 
{
	int row;
	int col;
	int width;
	int height;
	int rLevel;
	int cLevel;
	int direction;
	int alive;
} DUCK;

typedef struct
{
	int row;
	int col;
	int width;
	int height;
} SCOPE;

extern u16* videoBuffer;
//Function Prototypes
void fillScreen3(unsigned short color);
void drawString(int row, int col, char *str, u16 color);
void setPixel(int x, int y, u16 color);
void drawImage3(int row, int col, int height, int width, const u16* image);
void drawSub(int srcRow, int srcCol, int srcWidth, int row, int col, int height, int width, const u16* image);
void DMANow(int channel, const short unsigned int* source, void* destination, unsigned int control);
void waitForVBlank();
#endif	/* MY_LIB_H */
