#include <stdio.h>
#include "xil_printf.h"
#include "platform.h"
#include "xparameters.h"
#include "xgpio_l.h"
#include "xtmrctr_l.h"

#include "stdbool.h"

/******************************** Data types *********************************/

// State machine data type
typedef enum {Stopped, Started, SetLSSec, SetMSSec, SetLSMin, SetMSMin} TFSMState;

// Buttons GPIO masks
//CENTER RIGHT DOWN LEFT UP
#define BUTTON_UP_MASK		0x01
#define BUTTON_DOWN_MASK	0x04
#define BUTTON_RIGHT_MASK	0x08
#define BUTTON_CENTER_MASK	0x10

// Data structure to store buttons status
typedef struct SButtonStatus
{
	bool upPressed;
	bool downPressed;
	bool setPressed;
	bool startPressed;

	bool setPrevious;
	bool startPrevious;
} TButtonStatus;

// Data structure to store countdown timer value
typedef struct STimerValue
{
	unsigned int minMSValue;
	unsigned int minLSValue;
	unsigned int secMSValue;
	unsigned int secLSValue;
} TTimerValue;

/***************************** Helper functions ******************************/

// 7 segment decoder
unsigned char Hex2Seg(unsigned int value, bool dp) //converts a 4-bit number [0..15] to 7-segments; dp is the decimal point
{
	static const char Hex2SegLUT[] = {0x40, 0x79, 0x24, 0x30, 0x19, 0x12, 0x02, 0x78,
									  0x00, 0x10, 0x08, 0x03, 0x46, 0x21, 0x06, 0x0E};
	return dp ? Hex2SegLUT[value] : (0x80 | Hex2SegLUT[value]);
}

// Rising edge detection and clear
bool DetectAndClearRisingEdge(bool* pOldValue, bool newValue)
{
	bool retValue;

	retValue = (!(*pOldValue)) && newValue; //&& - AND l�gico as we work with boolean values
	*pOldValue = newValue;
	return retValue;
}

// Modular increment
bool ModularInc(unsigned int* pValue, unsigned int modulo)
{
	if (*pValue < modulo - 1)
	{
		(*pValue)++;
		return false;
	}
	else
	{
		*pValue = 0;
		return true;
	}
}

// Modular decrement
bool ModularDec(unsigned int* pValue, unsigned int modulo)
{
	if (*pValue > 0)
	{
		(*pValue)--;
		return false;
	}
	else
	{
		*pValue = modulo - 1;
		return true;
	}
}

// Tests if all timer digits are zero
bool IsTimerValueZero(const TTimerValue* pTimerValue)
{
	return ((pTimerValue->secLSValue == 0) && (pTimerValue->secMSValue == 0) &&
			(pTimerValue->minLSValue == 0) && (pTimerValue->minMSValue == 0));
}

// Conversion of the countdown timer values stored in a structure to an array of digits
void TimerValue2DigitValues(const TTimerValue* pTimerValue, unsigned int digitValues[8])
{
	digitValues[0] = 0;		//d�gito mais � direita
	digitValues[1] = 0;
	digitValues[2] = pTimerValue->secLSValue;
	digitValues[3] = pTimerValue->secMSValue;
	digitValues[4] = pTimerValue->minLSValue;
	digitValues[5] = pTimerValue->minMSValue;
	digitValues[6] = 0;
	digitValues[7] = 0;		//d�gito mais � esquerda
}

/******************* Countdown timer operations functions ********************/
//all enables come in positive logic, this function has to be invoked at correct frequency (e.g. 800Hz)
void RefreshDisplays(unsigned char digitEnables, const unsigned int digitValues[8], unsigned char decPtEnables)
{
	static unsigned int digitRefreshIdx = 0; // static variable - is preserved across calls

	// Insert your code here...
	///*** STEP 1
    unsigned int digit = digitValues[digitRefreshIdx];
	bool dp = (decPtEnables >> digitRefreshIdx) & 0x01;
	unsigned char segCode = Hex2Seg(digit, dp);

    XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR, XGPIO_DATA_OFFSET, segCode);
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR, XGPIO_DATA2_OFFSET, (1 << digitRefreshIdx) & digitEnables);



	digitRefreshIdx++;
	digitRefreshIdx &= 0x07; // AND bitwise
}

void ReadButtons(TButtonStatus* pButtonStatus)
{
	unsigned int buttonsPattern;

	//buttonsPattern = // Insert your code here...
	///*** STEP 2

    unsigned int buttonsPattern = XGpio_ReadReg(XPAR_AXI_GPIO_BUTTONS_BASEADDR, XGPIO_DATA_OFFSET);

	pButtonStatus->upPressed    = buttonsPattern & BUTTON_UP_MASK;
	pButtonStatus->downPressed  = buttonsPattern & BUTTON_DOWN_MASK;
	pButtonStatus->setPressed   = buttonsPattern & BUTTON_RIGHT_MASK;
	pButtonStatus->startPressed = buttonsPattern & BUTTON_CENTER_MASK;
}

void UpdateStateMachine(TFSMState* pFSMState, TButtonStatus* pButtonStatus, bool zeroFlag, unsigned char* pSetFlags)
{
	// Insert your code here...
	///*** STEP 4
	//switch(*pFSMState)
	//	{
	//		case Stopped:
	//		{
	//			*pSetFlags = 0x0;
	//			if (DetectAndClearRisingEdge(&pButtonStatus->startPrevious, pButtonStatus->startPressed) && !zeroFlag)
	//				*pFSMState = Started;
	//			else if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
	//				*pFSMState = SetMSMin;
	//			break;
	//		}

    switch (*pFSMState)
	{
		case Stopped:
			*pSetFlags = 0x0;
			if (DetectAndClearRisingEdge(&pButtonStatus->startPrevious, pButtonStatus->startPressed) && !zeroFlag)
				*pFSMState = Started;
			else if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
				*pFSMState = SetMSMin;
			break;

		case Started:
			*pSetFlags = 0x0;
			if (DetectAndClearRisingEdge(&pButtonStatus->startPrevious, pButtonStatus->startPressed))
				*pFSMState = Stopped;
			break;

		case SetMSMin:
			*pSetFlags = 0x10 >> 2;
			if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
				*pFSMState = SetLSMin;
			break;

		case SetLSMin:
			*pSetFlags = 0x08 >> 2;
			if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
				*pFSMState = SetMSSec;
			break;

		case SetMSSec:
			*pSetFlags = 0x04 >> 2;
			if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
				*pFSMState = SetLSSec;
			break;

		case SetLSSec:
			*pSetFlags = 0x02 >> 2;
			if (DetectAndClearRisingEdge(&pButtonStatus->setPrevious, pButtonStatus->setPressed))
				*pFSMState = Stopped;
			break;
	}

	///***
}
void SetCountDownTimer(TFSMState fsmState, const TButtonStatus* pButtonStatus, TTimerValue* pTimerValue)
{
	// Insert your code here...
	///*** STEP 5
	//switch(fsmState)
	//{
	//	case SetMSMin:


    switch (fsmState)
	{
		case SetMSMin:
			if (pButtonStatus->upPressed)
				ModularInc(&pTimerValue->minMSValue, 6);
			else if (pButtonStatus->downPressed)
				ModularDec(&pTimerValue->minMSValue, 6);
			break;

		case SetLSMin:
			if (pButtonStatus->upPressed)
				ModularInc(&pTimerValue->minLSValue, 10);
			else if (pButtonStatus->downPressed)
				ModularDec(&pTimerValue->minLSValue, 10);
			break;

		case SetMSSec:
			if (pButtonStatus->upPressed)
				ModularInc(&pTimerValue->secMSValue, 6);
			else if (pButtonStatus->downPressed)
				ModularDec(&pTimerValue->secMSValue, 6);
			break;

		case SetLSSec:
			if (pButtonStatus->upPressed)
				ModularInc(&pTimerValue->secLSValue, 10);
			else if (pButtonStatus->downPressed)
				ModularDec(&pTimerValue->secLSValue, 10);
			break;

		default:
			break;
	}

}

void DecCountDownTimer(TFSMState fsmState, TTimerValue* pTimerValue)
{
	// Insert your code here...
	///*** STEP 3

    if (fsmState != Started)
		return;

	if (ModularDec(&pTimerValue->secLSValue, 10))
		if (ModularDec(&pTimerValue->secMSValue, 6))
			if (ModularDec(&pTimerValue->minLSValue, 10))
				ModularDec(&pTimerValue->minMSValue, 6);
}

/******************************* Main function *******************************/

int main()
{
	init_platform();
	xil_printf("\n\nCount down timer - polling based version.\nConfiguring..."); //\r is carriage return, and \n is line feed

	//	GPIO tri-state configuration
	//	Inputs
	XGpio_WriteReg(XPAR_AXI_GPIO_SWITCHES_BASEADDR, XGPIO_TRI_OFFSET,  0xFFFFFFFF);
	XGpio_WriteReg(XPAR_AXI_GPIO_BUTTONS_BASEADDR,  XGPIO_TRI_OFFSET,  0xFFFFFFFF);

	//	Outputs
	XGpio_WriteReg(XPAR_AXI_GPIO_LEDS_BASEADDR,     XGPIO_TRI_OFFSET,  0xFFFF0000);
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR,  XGPIO_TRI_OFFSET,  0xFFFFFF00);
	XGpio_WriteReg(XPAR_AXI_GPIO_DISPLAYS_BASEADDR,  XGPIO_TRI2_OFFSET, 0xFFFFFF00);

	xil_printf("\nI/Os configured.");

 	// Disable hardware timer
 	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, 0x00000000);
	// Set hardware timer load value
	XTmrCtr_SetLoadReg(XPAR_AXI_TIMER_0_BASEADDR, 0, 125000); // Counter will wrap around every 1.25 ms
	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, XTC_CSR_LOAD_MASK);
	// Enable hardware timer, down counting with auto reload
	XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0, XTC_CSR_ENABLE_TMR_MASK  |
															  XTC_CSR_AUTO_RELOAD_MASK |
															  XTC_CSR_DOWN_COUNT_MASK);

	xil_printf("\n\rHardware timer configured.");

	xil_printf("\n\rSystem running.\n\r");

	// Timer event software counter
	unsigned hwTmrEventCount = 0;

	TFSMState     fsmState       = Stopped;
	unsigned char setFlags       = 0x0;
	TButtonStatus buttonStatus   = {false, false, false, false, false, false};
	TTimerValue   timerValue     = {5, 9, 5, 9};
	bool          zeroFlag       = false;

	unsigned char digitEnables   = 0x3C;
	unsigned int  digitValues[8] = {0, 0, 9, 5, 9, 5, 0, 0};
	unsigned char decPtEnables   = 0x00;

	bool          blink1HzStat   = false;
	bool          blink2HzStat   = false;

  	while (1)
  	{
  		unsigned int tmrCtrlStatReg = XTmrCtr_GetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0);

  		if (tmrCtrlStatReg & XTC_CSR_INT_OCCURED_MASK)
		{
  			// Clear hardware timer event (interrupt request flag)
			XTmrCtr_SetControlStatusReg(XPAR_AXI_TIMER_0_BASEADDR, 0,
										tmrCtrlStatReg | XTC_CSR_INT_OCCURED_MASK);
			hwTmrEventCount++;

			// Put here operations that must be performed at 800Hz rate
			// Refresh displays
			RefreshDisplays(digitEnables, digitValues, decPtEnables);


			if (hwTmrEventCount % 100 == 0) // 8Hz
			{
				// Put here operations that must be performed at 8Hz rate
				// Read push buttons
				ReadButtons(&buttonStatus);
				// Update state machine
				//fsmState - current FSM state
				//buttonStatus - structure holding status of four buttons
				//zeroFlag - is the current countdown timer value zero?
				//setFlags - what digit is being set?
				UpdateStateMachine(&fsmState, &buttonStatus, zeroFlag, &setFlags);
				if ((setFlags == 0x0) || (blink2HzStat))
				{
					digitEnables = 0x3C; // All digits active
				}
				else
				{
					digitEnables = (~(setFlags << 2)) & 0x3C; // Setting digit inactive
				}


				if (hwTmrEventCount % 200 == 0) // 4Hz
				{
					// Put here operations that must be performed at 4Hz rate
					// Switch digit set 2Hz blink status
					blink2HzStat = !blink2HzStat;


					if (hwTmrEventCount % 400 == 0) // 2Hz
					{
						// Put here operations that must be performed at 2Hz rate
						// Switch point 1Hz blink status
						blink1HzStat = !blink1HzStat;
						if (fsmState == Started)
							decPtEnables = (blink1HzStat ? 0x00 : 0x10);

						// Digit set increment/decrement
						//timerValue - structure holding the current countdown timer value
						SetCountDownTimer(fsmState, &buttonStatus, &timerValue);

						if (hwTmrEventCount == 800) // 1Hz
						{
							xil_printf("\r%d%d:%d%d", timerValue.minMSValue, timerValue.minLSValue, timerValue.secMSValue, timerValue.secLSValue);
							// Put here operations that must be performed at 1Hz rate
							// Count down timer normal operation
							DecCountDownTimer(fsmState, &timerValue);

							// Reset hwTmrEventCount every second
							hwTmrEventCount = 0;
						}
					}
				}
			}
		}

		zeroFlag = IsTimerValueZero(&timerValue);
		//digitValues - array holding display digits
		TimerValue2DigitValues(&timerValue, digitValues);

  		// Put here operations that are performed whenever possible
		///*** STEP 6
	}

	cleanup_platform();
	return 0;
}