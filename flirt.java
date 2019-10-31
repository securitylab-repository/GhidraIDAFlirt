// This plugin is Ghidra version of flirt approach like IDA PRO 
//@B. AIT SALEM 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 

/*
   Flirt file format
   =================
   High level layout:
   After the v5 header, there might be two more header fields depending of the version.
   If version == 6 or version == 7, there is one more header field.
   If version == 8 or version == 9, there is two more header field.
   See idasig_v* structs for their description.
   Next there is the non null terminated library name of library_name_len length.
   Next see Parsing below.

   Endianness:
   All multi bytes values are stored in little endian form in the headers.
   For the rest of the file they are stored in big endian form.

   Parsing:
   - described headers
   - library name, not null terminated, length of library_name_len.

   parse_tree (cf. parse_tree):
   - read number of initial root nodes: 1 byte if strictly inferior to 127 otherwise 2 bytes,
   stored in big endian mode, and the most significant bit isn't used. cf. read_multiple_bytes().
   if 0, this is a leaf, goto leaf (cf. parse_leaf). else continue parsing (cf. parse_tree).

   - for number of root node do:
    - read node length, one unsigned byte (the pattern size in this node) (cf. read_node_length)
    - read node variant mask (bit array) (cf. read_node_variant_mask):
      if node length < 0x10 read up to two bytes. cf. read_max_2_bytes
      if node length < 0x20 read up to five bytes. cf. read_multiple_bytes
    - read non-variant bytes (cf. read_node_bytes)
    - goto parse_tree

   leaf (cf. parse_leaf):
   - read crc length, 1 byte
   - read crc value, 2 bytes
   module:
    - read total module length:
      if version >= 9 read up to five bytes, cf. read_multiple_bytes
      else read up to two bytes, cf. read_max_2_bytes
    - read module public functions (cf. read_module_public_functions):
    same crc:
      public function name:
        - read function offset:
          if version >= 9 read up to five bytes, cf. read_multiple_bytes
          else read up to two bytes, cf. read_max_2_bytes
        - if current byte < 0x20, read it : this is a function flag, see IDASIG_FUNCTION* defines
        - read function name until current byte < 0x20
        - read parsing flag, 1 byte
        - if flag & IDASIG__PARSE__MORE_PUBLIC_NAMES: goto public function name
        - if flag & IDASIG__PARSE__READ_TAIL_BYTES, read tail bytes, cf. read_module_tail_bytes:
          - if version >= 8: read number of tail bytes, else suppose one
          - for number of tail bytes do:
            - read tail byte offset:
              if version >= 9 read up to five bytes, cf. read_multiple_bytes
              else read up to two bytes, cf. read_max_2_bytes
            - read tail byte value, one byte

        - if flag & IDASIG__PARSE__READ_REFERENCED_FUNCTIONS, read referenced functions, cf. read_module_referenced_functions:
          - if version >= 8: read number of referenced functions, else suppose one
          - for number of referenced functions do:
            - read referenced function offset:
              if version >= 9 read up to five bytes, cf. read_multiple_bytes
              else read up to two bytes, cf. read_max_2_bytes
            - read referenced function name length, one byte:
              - if name length == 0, read length up to five bytes, cf. read_multiple_bytes
            - for name length, read name chars:
              - if name is null terminated, it means the offset is negative

        - if flag & IDASIG__PARSE__MORE_MODULES_WITH_SAME_CRC, goto same crc, read function with same crc
        - if flag & IDASIG__PARSE__MORE_MODULES, goto module, to read another module


   More Information
   -----------------
   Function flags:
   - local functions ((l) with dumpsig) which are static ones.
   - collision functions ((!) with dumpsig) are the result of an unresolved collision.

   Tail bytes:
   When two modules have the same pattern, and same crc, flirt tries to identify
   a byte which is different in all the same modules.
   Their offset is from the first byte after the crc.
   They appear as "(XXXX: XX)" in dumpsig output

   Referenced functions:
   When two modules have the same pattern, and same crc, and are identical in
   non-variant bytes, they only differ by the functions they call. These functions are
   "referenced functions". They need to be identified first before the module can be
   identified.
   The offset is from the start of the function to the referenced function name.
   They appear as "(REF XXXX: NAME)" in dumpsig output
 */


import ghidra.app.script.GhidraScript;
import ghidra.program.model.util.*;
import ghidra.program.model.reloc.*;
import ghidra.program.model.data.*;
import ghidra.program.model.block.*;
import ghidra.program.model.symbol.*;
import ghidra.program.model.scalar.*;
import ghidra.program.model.mem.*;
import ghidra.program.model.listing.*;
import ghidra.program.model.lang.*;
import ghidra.program.model.pcode.*;
import ghidra.program.model.address.*;
import java.io.*;
import java.nio.*;

import java.util.zip.DataFormatException;  
import java.util.zip.Deflater;  
import java.util.zip.Inflater;  

import java.util.ArrayList ;
import java.util.Iterator;

public class flirt extends GhidraScript {	

	private int offset = 0 ;
	private idasig_v5 idasig_header = new idasig_v5();
	private byte[] file_content ;
	private long file_size = 0 ;
	private static final int R_FLIRT_NAME_MAX = 1024 ;
	public void run() throws Exception {


		//int x = 0XFFFFFFFD ; // 11111111111111111111111111111101 ;// -3 ;

		//long ux = Integer.toUnsignedLong(x) - 1 ;

		//println(""+ux) ;

		//byte[] b = new byte[4] ;
		//b[0] = (byte) 0x0d;
		//b[1] = 0x03;
		//b[2] = 0x05;
		//b[3] = (byte) 0xfe ;

		//short x = read_max_2_bytes(b);

		//println("short : "+Integer.toUnsignedLong(x));


		String file_path = null;

		try {

			file_path = askFile("Select IDA Signature FIle", "Apply signatures").toString();
		}
		catch(java.lang.IllegalArgumentException  e) {

			println(e.toString());
			return ;

		}
		catch(ghidra.util.exception.CancelledException e) {

			e.printStackTrace();
			return ;
		}

		if(file_path == null) {

			println("Could not read "+file_path+" file");
			return ;

		}

		if((file_size = file_length(file_path)) == 0) {

			println("Could not read "+file_path+" file");
			return ;

		}

		file_content = read_file(file_path);

		println("Signature file size : "+file_size); // IF 0 QUIT


		if (! parse_header()){

			println("File "+ file_path + " is not ida sig file");
			return;
		}


		if(idasig_header.features.getIntl() == Flags.IDASIG__FEATURE__COMPRESSED) {

			println("IDA SIG FILE COMPRESSED");
			file_content = this.decompress(file_content);
			offset = 0 ;
		}

		FlirtNode root_node = new FlirtNode();
		parse_tree(file_content, root_node);



	}


	/**
	 * Open file_path file, read the content of the file and return it as bytes stream 
	 * @param file_path 
	 * @return bytes stream of  file file_path
	 */
	public byte[] read_file(String inputFile){


		FileInputStream inputStream = null ;
		byte[] allBytes = {} ;
		long fileSize = 0 ;
		try {

			inputStream = new FileInputStream(inputFile);
			fileSize = new File(inputFile).length();

			allBytes = new byte[(int) fileSize];
			inputStream.read(allBytes);


		} catch (IOException e) {

			e.printStackTrace();

		}
		finally {

			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {

					println("Problem occurred while reading "+inputFile+" file");
					e.printStackTrace();
				}

		}

		return allBytes ;

	}


	public long file_length(String file_path){

		long fileSize = new File(file_path).length();
		return fileSize ;    

	}
	
	

	public void read_bytes(byte[] buffer_to_read, byte[] subbuf, int len) {

		int j = 0 ;
		for(int i = offset ; i < offset + len ; i++) {

			subbuf[j] = buffer_to_read[i] ; 
			j++;
		}

		offset+=len ;
	}

	public byte read_byte(byte[] buffer_to_read) throws FileEOFException {

		if(offset == this.file_size)
			throw new FileEOFException();
		byte b = buffer_to_read[offset] ; 
		offset+=1;
		return b ; 
	}


	

	public short read_short(byte[] b) throws FileEOFException{


		short r = (short) ( read_byte(b)  << 8);
		r |= read_byte(b);

		return r;

	}
	
	public u16 read_u16(byte[] b) throws FileEOFException {
		
		return new u16(read_short(b));
	}

	public int read_word(byte [] b) throws FileEOFException {


		int r = (read_short (b) << 16);
		r |= read_short (b);
		return r;

	}


	public u32 read_u32(byte[] b) throws FileEOFException {
		
		return new u32(read_word(b));
	}
	

	public int read_multiple_bytes(byte[] buffer_to_read) throws FileEOFException  {

		if(offset == this.file_size)
			throw new FileEOFException();

		byte b = read_byte(buffer_to_read);

		if ((b & 0x80) != 0x80)
			return b ;
		if ((b & 0xC0) != 0xC0)
			return ((b & 0x7F) << 8) | read_byte(buffer_to_read);
		if ((b & 0xE0) != 0xE0)
			return ((b & 0x3F) << 24) |  (read_byte(buffer_to_read) << 16); 

		return read_word(buffer_to_read);
	}


	public short  read_max_2_bytes(byte[] b) throws FileEOFException {

		byte r = read_byte (b);

		return ( (r & 0x80) != 0) ? (short) ((short) ((byte) (r & 0x7f) << 8) | read_byte(b)) : (short) r ;

	}


	/*public byte16 read_max_2_bytes(byte[] buffer_to_read) throws FileEOFException  {

		if(offset == this.file_size)
			throw new FileEOFException();

		byte b = read_byte(buffer_to_read) ;
		byte16 b16 = new byte16();

		if ((b & 0x80) == 0x80) {

			b16.value(0, (byte)(b & 0x7F));  
			b16.value(1,  read_byte(buffer_to_read) );  
		}

		else {

			b16.value(0, (byte)0);  
			b16.value(1,  b ); 

		}

		return b16;
	}*/

	// test long ret parameter 
	public boolean read_node_variant_mask(byte[] buffer_to_read, int length, FlirtLong ret) throws FlirtException 
	{

		try {
			if (length < 0x10) {
				ret.value(Short.toUnsignedLong(read_max_2_bytes(buffer_to_read)));
				return true ;
			}

			if (length <= 0x20) {
				ret.value(Integer.toUnsignedLong(read_multiple_bytes(buffer_to_read))) ;
				return true ;
			}
			if (length <= 0x40) {
				ret.value(  (read_multiple_bytes(buffer_to_read) << 32) | read_multiple_bytes(buffer_to_read)) ;
				return true ;
			}
			
		}
		catch(FileEOFException ex) {

			println(ex.getMessage());
			return false ;
		}

		throw new FlirtException(String.format("Wrong node variant mask length: %d",length));

	}


	public boolean read_node_bytes(byte[] buffer_to_read, FlirtNode node) {


		long mask_bit = 1 << node.length - 1 ;

		try {
			for (int i=0 ; i < node.length ; i++) {

				boolean curr_mask_bool = ((node.variant_mask.value() & mask_bit) != 0) ? true : false ;
				if (curr_mask_bool)
					node.pattern_bytes.add((byte)0);
				else
					node.pattern_bytes.add(read_byte(buffer_to_read));

				node.variant_bool_array.add(curr_mask_bool);
				mask_bit >>= 1 ;
			}
		}
		catch(FileEOFException ex) {

			println(ex.getMessage());
			return false ;
		}

		return true ;

	}
	

	

	public boolean read_module_public_functions(FlirtModule module, byte[] b, Flags flags) {
		/* Reads and set the public functions names and offsets associated within a module */
		/*returns false on parsing error*/
		int i;
		int off = 0 ;  // byte16 in original version
		byte current_byte;
		FlirtFunction function ;

		do {

			try {

				function = new FlirtFunction() ;

				if (idasig_header.version >= 9) {   // seems like version 9 introduced some larger offsets

					off+= read_multiple_bytes (b) ; // offsets are dependent of the previous ones

				} else {

					off += Short.toUnsignedInt(read_max_2_bytes (b)); // offsets are dependent of the previous ones
				}
				function.offset = off;

				current_byte = read_byte (b);

				if (current_byte < 0x20) {

					if ((current_byte &  Flags.IDASIG__FUNCTION__LOCAL) != 0) { // static function
						function.is_local = true;
					}
					if ((current_byte & Flags.IDASIG__FUNCTION__UNRESOLVED_COLLISION) != 0) {
						// unresolved collision (happens in *.exc while creating .sig from .pat)
						function.is_collision = true;
					}
					if ((current_byte & 0x01) != 0 || (current_byte & 0x04) != 0) { // appears as 'd' or '?' in dumpsig
					}
					current_byte = read_byte (b);
				}

				for (i = 0; current_byte >= 0x20 && i < R_FLIRT_NAME_MAX; i++) {
					function.name[i] = current_byte;
					current_byte = read_byte (b);
				}

				if (i == R_FLIRT_NAME_MAX) {
					println ("Function name too long\n");
					function.name[R_FLIRT_NAME_MAX - 1] = '\0';
				} else {
					function.name[i] = '\0';
				}


				flags.flags = current_byte;
				module.public_functions.add(function);

			}
			catch(FileEOFException e) {

				println(e.getMessage());
				return false ;
			}


		} while ((flags.flags & Flags.IDASIG__PARSE__MORE_PUBLIC_NAMES) != 0);

		return true;

	}


	public boolean parse_leaf(byte[] b, FlirtNode node) {
		/*parses a signature leaf: modules with same leading pattern*/
		/*returns false on parsing error*/
		Flags flags = new Flags() ;  
		byte  crc_length;
		byte16 crc16;
		FlirtModule module = new FlirtModule() ;

		try {
			do { // loop for all modules having the same prefix

				crc_length = read_byte (b); 
				crc16 = read_16 (b);

				do { // loop for all modules having the same crc

					module.crc_length = crc_length;
					module.crc16 = crc16.getIntb();

					if (idasig_header.version >= 9) { // seems like version 9 introduced some larger length
						/*/!\ XXX don't trust ./zipsig output because it will write a version 9 header, but keep the old version offsets*/
						module.length = read_multiple_bytes (b); // should be < 0x8000
					} else {
						module.length = Short.toUnsignedInt(read_max_2_bytes (b)); // should be < 0x8000
					}

					if (! read_module_public_functions (module, b, flags)) {

						return false;

					}

					/*if (flags & IDASIG__PARSE__READ_TAIL_BYTES) { // we need to read some tail bytes because in this leaf we have functions with same crc
					if (!read_module_tail_bytes (module, b)) {
						goto err_exit;
					}
				}
				if (flags & IDASIG__PARSE__READ_REFERENCED_FUNCTIONS) { // we need to read some referenced functions
					if (!read_module_referenced_functions (module, b)) {
						goto err_exit;
					}
				}*/

					node.module_list.add(module);

				} while ((flags.flags & Flags.IDASIG__PARSE__MORE_MODULES_WITH_SAME_CRC) != 0);

			} while ((flags.flags & Flags.IDASIG__PARSE__MORE_MODULES) != 0); // same prefix but different crc
		}
		catch(FileEOFException e) {
			return false ;
		}
		return true;

	}


	public boolean parse_tree(byte[] buffer_to_read, FlirtNode root_node ) {

		FlirtNode node = null;

		try {

			int nodes = read_multiple_bytes(buffer_to_read);

			if (nodes == 0) { // if there's no tree nodes remaining, that means we are on the leaf
				println("parse_leaf from root_node");
			}

			for (int i = 0 ; i < nodes ; i++) {

				node = new FlirtNode(); // form memory create it just before return is better ?
				node.length = read_byte(buffer_to_read);

				try {

					read_node_variant_mask(file_content,node.length,node.variant_mask ) ;
				}
				catch (FlirtException e) {

					println( e.getMessage());
					e.printStackTrace();
					return false ;
					// exit here , parsing error 
				}


				read_node_bytes(buffer_to_read, node) ;

				root_node.child_list.add(node);

				if(!parse_tree(buffer_to_read,node))
					return false ;
			}
		}
		catch(FileEOFException e) {

			println(e.getMessage());
			return false ;
		}

		return true ;

	}

	// here functions user only to parse header

	public int read_int(byte[] buffer_to_read) throws FileEOFException  {

		if(offset == this.file_size)
			throw new FileEOFException();

		byte[] buffer = new byte[4] ;
		int j = 0 ;
		for (int i = offset ; i < offset + 4 ; i++) {

			buffer[j] = buffer_to_read[i] ; 
			j++;
		}

		offset+=4;

		ByteBuffer bytebuf = ByteBuffer.wrap(buffer);
		bytebuf.order(ByteOrder.LITTLE_ENDIAN);
		return bytebuf.getInt();

	}


	public byte16 read_16(byte[] buffer_to_read)throws FileEOFException  {


		if(offset == this.file_size)
			throw new FileEOFException();


		byte16 b16 = new byte16();
		int j = 0 ;
		for (int i = offset ; i < offset + 2 ; i++) {

			b16.value(j, buffer_to_read[i] );  
			j++;
		}

		offset+=2;
		return b16;
	}	



	/**
	 * 
	 * @return false if a content is not in correct format (starting with IDASGN String), or error 
	 */
	public boolean parse_header() {

		try {
			// check if it is an ida sign file
			read_bytes(file_content, idasig_header.magic,idasig_header.magic.length);

			String magic = new String(idasig_header.magic) ;

			if(magic.compareTo("IDASGN") != 0) {

				println("This file is not ida signature file");
				return false;
			}

			idasig_header.version = read_byte(file_content);
			idasig_header.arch = read_byte(file_content);
			idasig_header.file_types = read_int(file_content);
			idasig_header.os_types =  read_16(file_content);
			idasig_header.app_types =  read_16(file_content);
			idasig_header.features = read_16(file_content);
			idasig_header.old_n_functions =  read_16(file_content);
			idasig_header.crc16 = read_16(file_content);
			read_bytes(file_content, idasig_header.ctype,idasig_header.ctype.length);
			idasig_header.library_name_len = read_byte(file_content);
			idasig_header.ctypes_crc16 = read_16(file_content);

			if (idasig_header.version >= 6) {
				idasig_header.n_functions = read_int(file_content);

				if (idasig_header.version >= 8) {

					idasig_header.pattern_size = read_16(file_content);

					if (idasig_header.version >= 9)
						read_16(file_content); //unknow
				}
			}

			idasig_header.library_name = new byte[idasig_header.library_name_len];
			read_bytes(file_content, idasig_header.library_name,idasig_header.library_name_len);
		}
		catch (FileEOFException ex) {

			println(ex.getMessage());
			return false ;
		}

		println(idasig_header.toString());

		return true ;

	}


	public byte[] decompress(byte[] data) throws IOException, DataFormatException {  

		Inflater inflater = new Inflater();   
		inflater.setInput(data,offset,data.length - offset);  
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);  
		byte[] buffer = new byte[1024];  

		while (!inflater.finished()) {  
			int count = inflater.inflate(buffer);  
			outputStream.write(buffer, 0, count);  
		}  

		outputStream.close();  
		return outputStream.toByteArray();   
	} 


}
