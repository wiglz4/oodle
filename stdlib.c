#include <syscall.h>

int readint() {
	int val = 0;
	int negative = 0;
	char x[1];

	read(0, x, 1);
	if(x[0] == '-') { //see if number is negative
		negative = 1;
		read(0, x, 1);
	}

	while(x[0] != '\n') {
		if(x[0] >= '0' && x[0] <= '9') { //if not a digit ignore
			val *= 10;
			val += (x[0] - '0'); //convert char to int
		}
		read(0, x, 1);
	}

	if(negative) {
		val *= -1; //change to negative
	}

	return val; //return read in val
}

void writeint(int num) {
  char buf[20];
  char result[20] = "0\n";
  char *pos = buf;
  char *writeptr = result;
  int numWritten;
 
  // Handle negative numbers
  if (num < 0) {
    *writeptr++ = '-';
    num = -num;
  }
  
  if (num > 0) {
      
    // Build the number in reverse order
    while (num > 0) {
      *pos++ = (num % 10) + '0';
      num /= 10;
    }
    pos--;
    
    // Now we need to copy the results into the output buffer, reversed
    while (pos > buf) {
      *writeptr++ = *pos--;
    }
    *writeptr++ = *pos;
    *writeptr++ = 10;
    *writeptr++ = 0;
  } else {
    // number is 0; use default result
    writeptr = result + 3;
  }
  
  write(1, result, (writeptr - result) - 1);
  
}

int add(int a, int b) {
	return a + b;
}


int subtract(int a, int b) {
	return b - a;
}


int divide(int a, int b) {
	return b / a;
}


int multiply(int a, int b) {
	return a * b;
}


int negate(int a) {
	return -a;
}


int not(int a) {
	if(a != 0) {
		return 0;
	}
	return 1;
}


int and(int a, int b) {
	if (a != 0 && b != 0) {
		return 1;
	}
	return 0;
}


int or(int a, int b) {
	if (a != 0 || b != 0) {
		return 1;
	}
	return 0;
}
