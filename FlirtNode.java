//Class FlirtNode
//@author B. AIT SALEM 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 

import java.util.ArrayList;

public class FlirtNode {

	
		ArrayList<FlirtNode> child_list = new ArrayList<FlirtNode>();
		ArrayList<FlirtModule> module_list = new ArrayList<FlirtModule>();
		int length = 0; // length of the pattern
		FlirtLong variant_mask = new FlirtLong() ; // this is the mask that will define variant bytes in ut8 *pattern_bytes
		ArrayList<Byte> pattern_bytes = new ArrayList<Byte>() ; // holds the pattern bytes of the signature
		ArrayList<Boolean> variant_bool_array = new ArrayList<Boolean>() ; // bool array, if true, byte in pattern_bytes is a variant byte

}
