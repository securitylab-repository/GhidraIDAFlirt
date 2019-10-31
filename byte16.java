//TODO This type represents 2 bytes
//@author B. AIT SALEM 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 


public class byte16 {

   private byte[] _value = new byte[2];
   
   /**
    * 
    * @return unsigned integer value of byte16 type. It consider a byte stream in little endian order 
    */
   public int getIntl() {

		return ( ( (_value[1] & 0xFF) << 8) | (_value[0] & 0xFF) );
		
	}
   
   /**
    * 
    * @return unsigned integer value of byte16 type. It consider a byte stream in big endian order
    */
   public int getIntb() {

		return ( ( (_value[0]  & 0xFF) << 8) | (_value[1]  & 0xFF) );
		
	}
   
   /**
    * 
    * @return String value of byte16 type
    */
   public String getString() {
	   
	   return ( new String(_value));
   }

/**
 * @return the _value
 */
public byte[] value() {
	return _value;
}

/**
 * @param i index of _value stream bytes attribute
 * @param value the _value to set at i index
 */
public void value(int i,byte value) {
	this._value[i] = value;
}


}
