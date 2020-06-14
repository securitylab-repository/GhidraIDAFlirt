// FlirtModule class
//@author 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 

import java.util.ArrayList;

public class FlirtModule {


	int crc_length;
	int crc16; // crc16 of the module after the pattern bytes
	// until but not including the first variant byte
	// this is a custom crc16
	int length; // total length of the module, should < 0x8000
	ArrayList<FlirtFunction> public_functions = new ArrayList<FlirtFunction>();
	//RList *tail_bytes;
	//RList *referenced_functions;



}
