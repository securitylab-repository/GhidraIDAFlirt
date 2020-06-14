//TODO write a description for this script
//@author 
//@category _BOS_SCRIPTS_
//@keybinding 
//@menupath 
//@toolbar 


public class Flags {

	public byte flags ;
	
	/*file_types Flags*/
	final static int IDASIG__FILE__DOS_EXE_OLD  =  0x00000001 ;
	final static int IDASIG__FILE__DOS_COM_OLD  =  0x00000002 ;
	final static int IDASIG__FILE__BIN          =  0x00000004 ;
	final static int IDASIG__FILE__DOSDRV       =  0x00000008 ;
	final static int IDASIG__FILE__NE           =  0x00000010 ;
	final static int IDASIG__FILE__INTELHEX     =  0x00000020 ;
	final static int IDASIG__FILE__MOSHEX       =  0x00000040 ;
	final static int IDASIG__FILE__LX           =  0x00000080 ;
	final static int IDASIG__FILE__LE           =  0x00000100 ;
	final static int IDASIG__FILE__NLM          =  0x00000200 ;
	final static int IDASIG__FILE__COFF         =  0x00000400 ;
	final static int IDASIG__FILE__PE           =  0x00000800 ;
	final static int IDASIG__FILE__OMF          =  0x00001000 ;
	final static int IDASIG__FILE__SREC         =  0x00002000 ;
	final static int IDASIG__FILE__ZIP          =  0x00004000 ;
	final static int IDASIG__FILE__OMFLIB       =  0x00008000 ;
	final static int IDASIG__FILE__AR           =  0x00010000 ;
	final static int IDASIG__FILE__LOADER       =  0x00020000 ;
	final static int IDASIG__FILE__ELF          =  0x00040000 ;
	final static int IDASIG__FILE__W32RUN       =  0x00080000 ;
	final static int IDASIG__FILE__AOUT         =  0x00100000 ;
	final static int IDASIG__FILE__PILOT        =  0x00200000 ;
	final static int IDASIG__FILE__DOS_EXE      =  0x00400000 ;
	final static int IDASIG__FILE__DOS_COM      =  0x00800000 ;
	final static int IDASIG__FILE__AIXAR        =  0x01000000 ;
	
	/*arch flags*/
	final static byte IDASIG__ARCH__386    =    0    ;   // Intel 80x86
	final static byte IDASIG__ARCH__Z80    =    1    ;   // 8085, Z80
	final static byte IDASIG__ARCH__I860   =    2    ;   // Intel 860
	final static byte IDASIG__ARCH__8051   =    3    ;   // 8051
	final static byte IDASIG__ARCH__TMS    =    4    ;   // Texas Instruments TMS320C5x
	final static byte IDASIG__ARCH__6502   =    5    ;   // 6502
	final static byte IDASIG__ARCH__PDP    =    6    ;   // PDP11
	final static byte IDASIG__ARCH__68K    =    7    ;   // Motoroal 680x0
	final static byte IDASIG__ARCH__JAVA   =    8    ;   // Java
	final static byte IDASIG__ARCH__6800   =    9    ;   // Motorola 68xx
	final static byte IDASIG__ARCH__ST7    =    10   ;   // SGS-Thomson ST7
	final static byte IDASIG__ARCH__MC6812 =    11   ;   // Motorola 68HC12
	final static byte IDASIG__ARCH__MIPS   =    12   ;   // MIPS
	final static byte IDASIG__ARCH__ARM    =    13   ;   // Advanced RISC Machines
	final static byte IDASIG__ARCH__TMSC6  =    14   ;   // Texas Instruments TMS320C6x
	final static byte IDASIG__ARCH__PPC    =    15   ;   // PowerPC
	final static byte IDASIG__ARCH__80196  =    16   ;   // Intel 80196
	final static byte IDASIG__ARCH__Z8     =    17   ;   // Z8
	final static byte IDASIG__ARCH__SH     =    18   ;   // Renesas (formerly Hitachi) SuperH
	final static byte IDASIG__ARCH__NET    =    19   ;   // Microsoft Visual Studio.Net
	final static byte IDASIG__ARCH__AVR    =    20   ;   // Atmel 8-bit RISC processor(s)
	final static byte IDASIG__ARCH__H8     =    21   ;   // Hitachi H8/300, H8/2000
	final static byte IDASIG__ARCH__PIC    =    22   ;   // Microchip's PIC
	final static byte IDASIG__ARCH__SPARC  =    23   ;   // SPARC
	final static byte IDASIG__ARCH__ALPHA  =    24   ;   // DEC Alpha
	final static byte IDASIG__ARCH__HPPA   =    25   ;   // Hewlett-Packard PA-RISC
	final static byte IDASIG__ARCH__H8500  =    26   ;   // Hitachi H8/500
	final static byte IDASIG__ARCH__TRICORE    = 27  ;    // Tasking Tricore
	final static byte IDASIG__ARCH__DSP56K     = 28  ;    // Motorola DSP5600x
	final static byte IDASIG__ARCH__C166       = 29  ;    // Siemens C166 family
	final static byte IDASIG__ARCH__ST20       = 30  ;    // SGS-Thomson ST20
	final static byte IDASIG__ARCH__IA64       = 31  ;    // Intel Itanium IA64
	final static byte IDASIG__ARCH__I960       = 32  ;    // Intel 960
	final static byte IDASIG__ARCH__F2MC       = 33  ;    // Fujistu F2MC-16
	final static byte IDASIG__ARCH__TMS320C54  = 34  ;    // Texas Instruments TMS320C54xx
	final static byte IDASIG__ARCH__TMS320C55  = 35  ;    // Texas Instruments TMS320C55xx
	final static byte IDASIG__ARCH__TRIMEDIA   = 36  ;    // Trimedia
	final static byte IDASIG__ARCH__M32R       = 37  ;    // Mitsubishi 32bit RISC
	final static byte IDASIG__ARCH__NEC_78K0   = 38  ;    // NEC 78K0
	final static byte IDASIG__ARCH__NEC_78K0S  = 39  ;    // NEC 78K0S
	final static byte IDASIG__ARCH__M740       = 40  ;    // Mitsubishi 8bit
	final static byte IDASIG__ARCH__M7700      = 41  ;    // Mitsubishi 16bit
	final static byte IDASIG__ARCH__ST9        = 42  ;    // ST9+
	final static byte IDASIG__ARCH__FR         = 43  ;    // Fujitsu FR Family
	final static byte IDASIG__ARCH__MC6816     = 44  ;    // Motorola 68HC16
	final static byte IDASIG__ARCH__M7900      = 45  ;    // Mitsubishi 7900
	final static byte IDASIG__ARCH__TMS320C3   = 46  ;    // Texas Instruments TMS320C3
	final static byte IDASIG__ARCH__KR1878     = 47  ;    // Angstrem KR1878
	final static byte IDASIG__ARCH__AD218X     = 48  ;    // Analog Devices ADSP 218X
	final static byte IDASIG__ARCH__OAKDSP     = 49  ;    // Atmel OAK DSP
	final static byte IDASIG__ARCH__TLCS900    = 50  ;    // Toshiba TLCS-900
	final static byte IDASIG__ARCH__C39        = 51  ;    // Rockwell C39
	final static byte IDASIG__ARCH__CR16       = 52  ;    // NSC CR16
	final static byte IDASIG__ARCH__MN102L00   = 53  ;    // Panasonic MN10200
	final static byte IDASIG__ARCH__TMS320C1X  = 54  ;    // Texas Instruments TMS320C1x
	final static byte IDASIG__ARCH__NEC_V850X  = 55  ;    // NEC V850 and V850ES/E1/E2
	final static byte IDASIG__ARCH__SCR_ADPT   = 56  ;    // Processor module adapter for processor modules written in scripting languages
	final static byte IDASIG__ARCH__EBC        = 57  ;    // EFI Bytecode
	final static byte IDASIG__ARCH__MSP430     = 58  ;    // Texas Instruments MSP430
	final static byte IDASIG__ARCH__SPU        = 59  ;    // Cell Broadband Engine Synergistic Processor Unit
	final static byte IDASIG__ARCH__DALVIK     = 60  ;    // Android Dalvik Virtual Machine
		
	/*os_types flags*/
	final static int IDASIG__OS__MSDOS      = 0x01 ;
	final static int IDASIG__OS__WIN        = 0x02 ;
	final static int IDASIG__OS__OS2        = 0x04 ;
	final static int IDASIG__OS__NETWARE    = 0x08 ;
	final static int IDASIG__OS__UNIX       = 0x10 ;
	final static int IDASIG__OS__OTHER      = 0x20 ;

	/*app types flags*/
	final static int IDASIG__APP__CONSOLE            = 0x0001 ;
	final static int IDASIG__APP__GRAPHICS           = 0x0002 ;
	final static int IDASIG__APP__EXE                = 0x0004 ;
	final static int IDASIG__APP__DLL                = 0x0008 ;
	final static int IDASIG__APP__DRV                = 0x0010 ;
	final static int IDASIG__APP__SINGLE_THREADED    = 0x0020 ;
	final static int IDASIG__APP__MULTI_THREADED     = 0x0040 ;
	final static int IDASIG__APP__16_BIT             = 0x0080 ;
	final static int IDASIG__APP__32_BIT             = 0x0100 ;
	final static int IDASIG__APP__64_BIT             = 0x0200 ;

	/*feature flags*/
	final static int IDASIG__FEATURE__STARTUP          = 0x01 ;
	final static int IDASIG__FEATURE__CTYPE_CRC        = 0x02 ;
	final static int IDASIG__FEATURE__2BYTE_CTYPE      = 0x04 ;
	final static int IDASIG__FEATURE__ALT_CTYPE_CRC    = 0x08 ;
	final static int IDASIG__FEATURE__COMPRESSED       = 0x10 ;
	
	/*parsing flags*/
	final static byte  IDASIG__PARSE__MORE_PUBLIC_NAMES      =      0x01 ;
	final static byte  IDASIG__PARSE__READ_TAIL_BYTES         =     0x02 ;
	final static byte  IDASIG__PARSE__READ_REFERENCED_FUNCTIONS =   0x04 ;
	final static byte  IDASIG__PARSE__MORE_MODULES_WITH_SAME_CRC =  0x08 ;
	final static byte  IDASIG__PARSE__MORE_MODULES               =  0x10 ;
	
	/*functions flags*/
	final static byte IDASIG__FUNCTION__LOCAL         =            0x02  ;// describes a static function
	final static byte IDASIG__FUNCTION__UNRESOLVED_COLLISION =      0x08 ; // describes a collision that wasn't resolved


	
}
