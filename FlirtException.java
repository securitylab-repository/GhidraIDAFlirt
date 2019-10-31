// Flirt Exception hiearchy
//@author B. AIT SALEM 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 

import java.lang.Exception;

public class FlirtException extends Exception {

	public FlirtException () {
		super();
	}
	public FlirtException (String errorMessage ) {
		super(errorMessage);
	}

}