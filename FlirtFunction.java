// FlirtFunction class
//@author B. AIT SALEM
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 


public class FlirtFunction {

	byte[] name;
	int offset; // function offset from the module start , byte16 in original version
	byte negative_offset; // true if offset is negative, for referenced functions
	boolean is_local; // true if function is static
	boolean is_collision; // true if was an unresolved collision

}
