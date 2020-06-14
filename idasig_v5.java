//TODO This class represents ida signature file header
//@author 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class idasig_v5  {
	
	public byte[] magic = new byte[6];  /* should be set to IDASGN */
	public byte version;   /*from 5 to 9*/
	public byte arch;
	public int file_types;
	public byte16 os_types;
	public byte16 app_types;
	public byte16 features;
	public byte16 old_n_functions;
	public byte16 crc16;
	public byte[] ctype = new byte[12];  // XXX: how to use it
	public byte library_name_len;
	public byte16 ctypes_crc16;
	public int n_functions ;
	public byte16 pattern_size ;
	public byte[] library_name ;

	
	@Override
	public String toString() {
		
	
		return String.format("ver: %d, arch: %s, file_type : %s, os_type : %s, app_types : %d, features : %s, old_n_functions : %d, crc16 : %d, library_name_len : %d, ctype : %s,", 
							  version,arch_string(arch),file_types_string(file_types),os_types_string(os_types),app_types.getIntl(),Integer.toBinaryString(features.getIntl()), old_n_functions.getIntl(),crc16.getIntl(),library_name_len,new String(ctype))
		 + String.format(" ctypes_crc16: %d, n_functions: %d, pattern_size : %d, library_name : %s",ctypes_crc16.getIntl(),n_functions,pattern_size.getIntl(),new String(library_name))	; 
		
	}
	
	
	static String file_types_string(int flg) {

		Map<Integer, String> file_types = new HashMap<>();

		file_types.put(Flags.IDASIG__FILE__DOS_EXE_OLD, "DOS_EXE_OLD");
		file_types.put(Flags.IDASIG__FILE__DOS_COM_OLD, "DOS_COM_OLD");
		file_types.put(Flags.IDASIG__FILE__BIN, "BIN");
		file_types.put(Flags.IDASIG__FILE__DOSDRV, "DOSDRV");
		file_types.put(Flags.IDASIG__FILE__NE, "NE");
		file_types.put(Flags.IDASIG__FILE__INTELHEX, "INTELHEX");
		file_types.put(Flags.IDASIG__FILE__MOSHEX, "MOSHEX");
		file_types.put(Flags.IDASIG__FILE__LX, "LX");
		file_types.put(Flags.IDASIG__FILE__LE, "LE");
		file_types.put(Flags.IDASIG__FILE__NLM, "NLM");
		file_types.put(Flags.IDASIG__FILE__COFF, "COFF");
		file_types.put(Flags.IDASIG__FILE__PE, "PE");
		file_types.put(Flags.IDASIG__FILE__OMF, "OMF");
		file_types.put(Flags.IDASIG__FILE__SREC, "SREC");
		file_types.put(Flags.IDASIG__FILE__ZIP, "ZIP");
		file_types.put(Flags.IDASIG__FILE__OMFLIB, "OMFLIB");
		file_types.put(Flags.IDASIG__FILE__AR, "AR");
		file_types.put(Flags.IDASIG__FILE__LOADER, "LOADER");
		file_types.put(Flags.IDASIG__FILE__ELF, "ELF");
		file_types.put(Flags.IDASIG__FILE__W32RUN, "W32RUN");
		file_types.put(Flags.IDASIG__FILE__AOUT, "AOUT");
		file_types.put(Flags.IDASIG__FILE__PILOT, "PILOT");
		file_types.put(Flags.IDASIG__FILE__DOS_EXE, "EXE");
		file_types.put(Flags.IDASIG__FILE__AIXAR, "AIXAR");
		
		return file_types.get(flg);
	}
	
	static String os_types_string(byte16 flg) {

		Map<Integer, String> os_types = new HashMap<>();
		
		os_types.put (Flags.IDASIG__OS__MSDOS, "MSDOS");
		os_types.put (Flags.IDASIG__OS__WIN, "WIN");
		os_types.put (Flags.IDASIG__OS__OS2, "OS2");
		os_types.put (Flags.IDASIG__OS__NETWARE, "NETWARE");
		os_types.put (Flags.IDASIG__OS__UNIX, "UNIX");
		
		return os_types.get(flg.getIntl());
	}

	static String app_types_string(byte16 flg) {

		Map<Integer, String> app_types = new HashMap<>();
		
		app_types.put (Flags.IDASIG__APP__CONSOLE, "CONSOLE");
		app_types.put (Flags.IDASIG__APP__GRAPHICS, "GRAPHICS");
		app_types.put (Flags.IDASIG__APP__EXE, "EXE");
		app_types.put (Flags.IDASIG__APP__DLL, "DLL");
		app_types.put (Flags.IDASIG__APP__DRV, "DRV");
		app_types.put (Flags.IDASIG__APP__SINGLE_THREADED, "SINGLE_THREADED");
		app_types.put (Flags.IDASIG__APP__MULTI_THREADED, "MULTI_THREADED");
		app_types.put (Flags.IDASIG__APP__16_BIT, "16_BIT");
		app_types.put (Flags.IDASIG__APP__32_BIT, "32_BIT");
		app_types.put (Flags.IDASIG__APP__64_BIT, "64_BIT");

		
		return app_types.get(flg.getIntl());
	}
	
	static String arch_string(byte flg) {

		Map<Byte, String> arch = new HashMap<>();
		
		arch.put (Flags.IDASIG__ARCH__386, "386");
		arch.put (Flags.IDASIG__ARCH__Z80, "Z80");
		arch.put (Flags.IDASIG__ARCH__I860, "I860");
		arch.put (Flags.IDASIG__ARCH__8051, "8051");
		arch.put (Flags.IDASIG__ARCH__TMS, "TMS");
		arch.put (Flags.IDASIG__ARCH__6502, "6502");
		arch.put (Flags.IDASIG__ARCH__PDP, "PDP");
		arch.put (Flags.IDASIG__ARCH__68K, "68K");
		arch.put (Flags.IDASIG__ARCH__JAVA, "JAVA");
		arch.put (Flags.IDASIG__ARCH__6800, "6800");
		arch.put (Flags.IDASIG__ARCH__ST7, "ST7");
		arch.put (Flags.IDASIG__ARCH__MC6812, "MC6812");
		arch.put (Flags.IDASIG__ARCH__MIPS, "MIPS");
		arch.put (Flags.IDASIG__ARCH__ARM, "ARM");
		arch.put (Flags.IDASIG__ARCH__TMSC6, "TMSC6");
		arch.put (Flags.IDASIG__ARCH__PPC, "PPC");
		arch.put (Flags.IDASIG__ARCH__80196, "80196");
		arch.put (Flags.IDASIG__ARCH__Z8, "Z8");
		arch.put (Flags.IDASIG__ARCH__SH, "SH");
		arch.put (Flags.IDASIG__ARCH__NET, "NET");
		arch.put (Flags.IDASIG__ARCH__AVR, "AVR");
		arch.put (Flags.IDASIG__ARCH__H8, "H8");
		arch.put (Flags.IDASIG__ARCH__PIC, "PIC");
		arch.put (Flags.IDASIG__ARCH__SPARC, "SPARC");
		arch.put (Flags.IDASIG__ARCH__ALPHA, "ALPHA");
		arch.put (Flags.IDASIG__ARCH__HPPA, "HPPA");
		arch.put (Flags.IDASIG__ARCH__H8500, "H8500");
		arch.put (Flags.IDASIG__ARCH__TRICORE, "TRICORE");
		arch.put (Flags.IDASIG__ARCH__DSP56K, "DSP56K");
		arch.put (Flags.IDASIG__ARCH__C166, "C166");
		arch.put (Flags.IDASIG__ARCH__ST20, "ST20");
		arch.put (Flags.IDASIG__ARCH__IA64, "IA64");
		arch.put (Flags.IDASIG__ARCH__I960, "I960");
		arch.put (Flags.IDASIG__ARCH__F2MC, "F2MC");
		arch.put (Flags.IDASIG__ARCH__TMS320C54, "TMS320C54");
		arch.put (Flags.IDASIG__ARCH__TMS320C55, "TMS320C55");
		arch.put (Flags.IDASIG__ARCH__TRIMEDIA, "TRIMEDIA");
		arch.put (Flags.IDASIG__ARCH__M32R, "M32R");
		arch.put (Flags.IDASIG__ARCH__NEC_78K0, "NEC_78K0");
		arch.put (Flags.IDASIG__ARCH__NEC_78K0S, "NEC_78K0S");
		arch.put (Flags.IDASIG__ARCH__M740, "M740");
		arch.put (Flags.IDASIG__ARCH__M7700, "M7700");
		arch.put (Flags.IDASIG__ARCH__ST9, "ST9");
		arch.put (Flags.IDASIG__ARCH__FR, "FR");
		arch.put (Flags.IDASIG__ARCH__MC6816, "MC6816");
		arch.put (Flags.IDASIG__ARCH__M7900, "M7900");
		arch.put (Flags.IDASIG__ARCH__TMS320C3, "TMS320C3");
		arch.put (Flags.IDASIG__ARCH__KR1878, "KR1878");
		arch.put (Flags.IDASIG__ARCH__AD218X, "AD218X");
		arch.put (Flags.IDASIG__ARCH__OAKDSP, "OAKDSP");
		arch.put (Flags.IDASIG__ARCH__TLCS900, "TLCS900");
		arch.put (Flags.IDASIG__ARCH__C39, "C39");
		arch.put (Flags.IDASIG__ARCH__CR16, "CR16");
		arch.put (Flags.IDASIG__ARCH__MN102L00, "MN102L00");
		arch.put (Flags.IDASIG__ARCH__TMS320C1X, "TMS320C1X");
		arch.put (Flags.IDASIG__ARCH__NEC_V850X, "NEC_V850X");
		arch.put (Flags.IDASIG__ARCH__SCR_ADPT, "SCR_ADPT");
		arch.put (Flags.IDASIG__ARCH__EBC, "EBC");
		arch.put (Flags.IDASIG__ARCH__MSP430, "MSP430");
		arch.put (Flags.IDASIG__ARCH__SPU, "SPU");
		arch.put (Flags.IDASIG__ARCH__DALVIK, "DALVIK");


		
		return arch.get(flg);
	}

}


