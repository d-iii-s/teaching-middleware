/* Unusual internal identifiers used so that
   they can be easily recognized in raw dump. */

program EXAMPLE {
  version VERSION {
    void PRINT_STRING (string STR) = 0xACE;    /* function number 0xACE     */
  } = 0xBAD;                                   /* version number 0xBAD      */
} = 0xCAFEFEED;                                /* service number 0xCAFEFEED */
