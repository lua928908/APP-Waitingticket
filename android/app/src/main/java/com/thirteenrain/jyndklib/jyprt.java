package com.thirteenrain.jyndklib;

import java.io.UnsupportedEncodingException;
public class jyprt {

    public String StartOfText(){
        String Cmd="";
        Cmd+=(char)0x02;
        return Cmd;
    }
    public String ControlBS(){
        String Cmd="";
        Cmd+=(char)0x08;
        return Cmd;
    }
    public String ControlHorizonTab(){
        String Cmd="";
        Cmd+=(char)0x09;
        return Cmd;
    }
    public String Control_LineFeed(){
        String Cmd="";
        Cmd+=(char)0x0a;
        return Cmd;
    }
    public String Control_VerticalTab(){
        String Cmd="";
        Cmd+=(char)0x0b;
        return Cmd;
    }
    public String Control_FormFeed(){
        String Cmd="";
        Cmd+=(char)0x0c;

        return Cmd;
    }
    public String Control_CarriageReturn(){
        String Cmd="";
        Cmd+=(char)0x0d;
        return Cmd;
    }
    public String Control_ShiftOut(){
        String Cmd="";
        Cmd+=(char)0x0e;

        return Cmd;
    }
    public String Control_ShiftIn(){
        String Cmd="";
        Cmd+=(char)0x0f;

        return Cmd;
    }
    public String Control_DLE(){        /// dle+eqt + n ??
        String Cmd="";
        Cmd+=(char)0x10;

        return Cmd;
    }
    public String Control_Cancel(){
        String Cmd="";
        Cmd+=(char)0x18;

        return Cmd;
    }
    public String Control_Escape(){
        String Cmd="";
        Cmd+=(char)0x1b;

        return Cmd;
    }
    public String Control_FS(){
        String Cmd="";
        Cmd+=(char)0x1c;

        return Cmd;
    }
    public String Control_GS(){
        String Cmd="";
        Cmd+=(char)0x1d;

        return Cmd;
    }

    //Select cut mode and cut paper
    public String BS_SelectCutMode(char jMode1,char jMode2){
        String Cmd="";
        Cmd+=(char)0x08;
        Cmd+=(char)0x56;
        Cmd+=jMode1;
        Cmd+=jMode2;

        return Cmd;
    }


    // print data in page mode
    public String Esc_FormFeed(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x0c;

        return Cmd;
    }
    //Set right-side character spacing
    public String Esc_RightSideSpace(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x20;
        Cmd+=jMode;

        return Cmd;
    }
    //Select Print Mode
    public String Esc_SelectPrintMode(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x21;
        Cmd+=jMode;

        return Cmd;
    }
    //Set absolute print position
    public String Esc_SetAbsPosition(char jModeL,char jModeH){  /// LH ???? 어떻게 넣엉
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x24;
        Cmd+=jModeL;
        Cmd+=jModeH;

        return Cmd;
    }
    //Select/Cancel user-defined character set
    public String Esc_UserDefineCharSet(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x25;
        Cmd+=jMode;

        return Cmd;
    }
    //Define user-defined characters
    public String Esc_UserDefineChar(){ /// ?????????
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x26;
//0x1b + 0x26 + 3 + c1 + c2 -----

        return Cmd;
    }
    //Select bit image mode
    public String Esc_ImageMode(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x2A;
        Cmd+=jMode;

        return Cmd;
    }
    //Select/Cancel underline
    public String Esc_Underline(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x2D;
        Cmd+=jMode;

        return Cmd;
    }
    // Select default line spacing
    public String Esc_SelectDefaultLineSpacing(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x32;

        return Cmd;
    }
    // Set line spacing
    // n * 0.125 (2 phases) feeding
    public String Esc_SetLineSpacing(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x33;
        Cmd+=jMode;

        return Cmd;
    }
    //Select peripheral device
    public String Esc_SelectPeripherDev(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x3d;
        Cmd+=jMode;

        return Cmd;
    }
    //Cancel user-defined characters
    public String Esc_UserDefCharCancel(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x3f;
        Cmd+=jMode;

        return Cmd;
    }
    // Initialize printer
    public String Esc_Initialize(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x40;

        return Cmd;
    }
    //Set HT Tab positions
    public String Esc_SetHTPosition(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x44;

        return Cmd;
    }
    //Select/Cancel Emphasized mode
    public String Esc_Emphasize(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x45;
        Cmd+=jMode;

        return Cmd;
    }
    //Select/Cancel Double Strike
    public String Esc_DoubleStrike(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x47;
        Cmd+=jMode;

        return Cmd;
    }
    // 0 < jMode <9
    // Print and feed paper
    public String Esc_PrintandFeed(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x4A;
        Cmd+=jMode;

        return Cmd;
    }
    // Select Page mode
    public String Esc_SelectPageMode(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x4C;

        return Cmd;
    }
    //Select Character font
    public String Esc_SelectCharFont(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x4D;
        Cmd+=jMode;

        return Cmd;
    }
    //Select an international character set
    public String Esc_SelectIntCharSet(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x52;
        Cmd+=jMode;

        return Cmd;
    }
    //Select Standard mode
    public String Esc_SelectStandardMode(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x53;

        return Cmd;
    }
    //Select print direction in page mode
    public String Esc_DirectionMode(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x54;

        return Cmd;
    }
    //Select 90 degrees rotation mode
    public String Esc_RotateClockwise(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x56;
        Cmd+=jMode;

        return Cmd;
    }
    //Select relative print positions
    public String Esc_SetRelativePrintPosition(char xL,char xH,char yL,char yH,char dxL,char dxH,char dyL,char dyH){   ///???
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x57;
        Cmd+=(char)xL;
        Cmd+=(char)xH;
        Cmd+=(char)yL;
        Cmd+=(char)yH;
        Cmd+=(char)dxL;
        Cmd+=(char)dxH;
        Cmd+=(char)dyL;
        Cmd+=(char)dyH;

        return Cmd;
    }
    //Set relative print position
    public String Esc_SetRelativePrtPosition(char jModeL,char jModeH){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x5c;
        Cmd+=jModeH;
        Cmd+=jModeL;

        return Cmd;
    }
    //Select justification
    public String Esc_SelectJust(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x61;
        Cmd+=jMode;

        return Cmd;
    }
    //0x1b + 0x63 + 0x33 + n	; select paper sensor
    //0x1b + 0x63 + 0x34 + n    ; select paper sensor
    //0x1b + 0x63 + 0x35 + n    ;panel button on/off
    public String Esc_PaperSensorPanel(char Select ,char jMode){ ///???
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x63;
        Cmd+=(char)(0x33+Select);
        Cmd+=jMode;

        return Cmd;
    }
    public String Esc_PrintnlineFeed(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x64;
        Cmd+=jMode;

        return Cmd;
    }
    //print and feed n liles
    public String Esc_SetPrintFeedDirection(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x65;
        Cmd+=jMode;

        return Cmd;
    }
    public String Esc_PartialCut(){ ///???
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x69;

        return Cmd;
    }/*
    public String Esc_PartialCut(){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x6d;

        return Cmd;
    }*/
    //Generate pulse
    public String Esc_GeneratePulse(char jMode,char t1,char t2){  //??? /// 금전함
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x70;
        Cmd+=(char)jMode;
        Cmd+=(char)t1;
        Cmd+=(char)t2;

        return Cmd;
    }
    //Select character code table
    public String Esc_SelectCodeTable(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x74;
        Cmd+=jMode;

        return Cmd;
    }
    //upside-down printing mode on/off
    public String Esc_UpsideDownMode(char jMode){
        String Cmd="";
        Cmd+=(char)0x1b;
        Cmd+=(char)0x7b;
        Cmd+=jMode;

        return Cmd;
    }


    //Select character size
    public String GS_SelectCharaterSize(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x21;
        Cmd += jMode;


        return Cmd;
    }
    //Set absolute vertical print position in page mode
    public String GS_SetPrintPosition(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x24;

        return Cmd;
    }
    //Execute test print
    public String GS_ExecuteTestPrint(char pL,char pH,char jMode1,char jMode2) {   ///???
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x27;
        Cmd += (char) 0x41;
        Cmd += (char) pL;
        Cmd += (char) pH;
        Cmd += (char) jMode1;
        Cmd += (char) jMode2;

        return Cmd;
    }
    //Start/end macro defination
    public String GS_MacroDefination() {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x3a;

        return Cmd;
    }
    //Turns white/black reverse printing mode on/off
    public String GS_TurnsWhiteBlack(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x42;
        Cmd += (char) jMode;

        return Cmd;
    }
    //Selects the printing position of HRI characters
    public String GS_Select_HRI_position(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x48;
        Cmd += jMode;

        return Cmd;
    }
    //Transmits printer ID
    public String GS_TransmitsPrinterID(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x49;
        Cmd += jMode;

        return Cmd;
    }
    //Set left margin
    public String GS_Set_left_margin(char jModeL,char jModeH) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x4C;
        Cmd += jModeL;
        Cmd += jModeH;

        return Cmd;
    }
    //Set horizontal and vertical motion units
    public String GS_SetMotionUnits(char jModeX,char jModeY) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x50;
        Cmd += jModeX;
        Cmd += jModeY;

        return Cmd;
    }
    //Set print position to the begining of print line
    public String GS_SetPrintPositionPrintLing(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x54;
        Cmd += jMode;

        return Cmd;
    }
    //Select cut mode and cut paper
    public String GS_CutPaper(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x56;
        Cmd += jMode;

        return Cmd;
    }
    //Set printing area width
    public String GS_SetPrintingAeraWidth(char jModeL,char jModeH) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x57;
        Cmd += jModeL;
        Cmd += jModeH;

        return Cmd;
    }
    //Set relative vertical print position in page mode
    public String GS_SetRelativePrintPosition(char jModeL,char jModeH) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x5c;
        Cmd += jModeL;
        Cmd += jModeH;

        return Cmd;
    }
    //Execute macro
    public String GS_ExecuteMacro(char jModeL,char jModeH) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x5e;
        Cmd += jModeL;
        Cmd += jModeH;

        return Cmd;
    }
    //Turns smoothing mode on/off
    public String GS_TurnsSmootingMode(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x62;
        Cmd += jMode;

        return Cmd;
    }
    //Select font for HRI characters
    public String GS_SelectFontHRI(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x66;
        Cmd += jMode;

        return Cmd;
    }
    public String GS_PrintSignpadImage() {  ///????
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x67;

        return Cmd;
    }
    //Selects bar code height
    public String GS_Select_barcode_height(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x68;
        Cmd += jMode;

        return Cmd;
    }
    public String GS_Print_Barcode(char hri, char height,char jmode, String jData) {  ////????
        String Cmd = "";
        Cmd += (char) 0x1D;     //GS_Select_HRI_position
        Cmd += (char) 0x48;
        Cmd += (char) hri;

        Cmd += (char) 0x1D;     //GS_Select_barcode_height
        Cmd += (char) 0x68;
        Cmd += (char) height;

        Cmd += (char) 0x1D;     //GS_Print_Barcode
        Cmd += (char) 0x6b;
        Cmd += (char) jmode;
        char length= (char) jData.length();
        Cmd += length;
        Cmd += jData;

        return Cmd;
    }
    //Print raster bit image
    public String GS_PrintRasterBitImage(char jMode, char xL,char xH,char yL,char yH,char... image) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x76;
        Cmd += (char) 0x30;
        Cmd += jMode;
        Cmd += xL;
        Cmd += xH;
        Cmd += yL;
        Cmd += yH;
        int size = ((xL + xH * 256) * (yL + yH * 256));
        for (int i = 0; i < size; i++) {
            Cmd += (char) image[i];
        }

        return Cmd;
//GS + 'v' + '0' + m + xL + xH + yL + yH + d1...dk
//0x1d + 0x76 + 0x30 +  m + xL + xH + yL + yH + d1...dk
    }

    //Set bar code width
    public String GS_Set_barcode_width(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x77;
        Cmd += jMode;

        return Cmd;
    }


    //Selects Print Brightness
    public String FS_Print_Bright(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x42;
        Cmd += jMode;

        return Cmd;
    }
    //Turn  card reading mode on/off
    public String FS_CardReadingMode(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x4D;
        Cmd += jMode;

        return Cmd;
    }
    //Print QRcode
    public String GS_Print_Qrcode(char pixelSize, String jData) {
//0x1d + 0x28 + 0x6B + pL + pH + n + m
        String Cmd = "";
        Cmd += (char) 0x1D;
        Cmd += (char) 0x28;
        Cmd += (char) 0x6B;
        Cmd += (char) 0x03;
        Cmd += (char) 0x00;
        Cmd += (char) 0x31;
        Cmd += (char) 0x43;
        Cmd += (char) pixelSize;

        Cmd += (char) 0x1D;
        Cmd += (char) 0x28;
        Cmd += (char) 0x6B;
        try {
            Cmd += (char)((jData.getBytes("euc-kr").length + 3) % 256);
            Cmd += (char)((jData.getBytes("euc-kr").length + 3) / 256);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        Cmd += (char) 0x31;
        Cmd += (char) 0x50;
        Cmd += (char) 0x30;
        Cmd += jData;

        Cmd += (char) 0x1D;
        Cmd += (char) 0x28;
        Cmd += (char) 0x6B;
        Cmd += (char) 0x03;
        Cmd += (char) 0x00;
        Cmd += (char) 0x31;
        Cmd += (char) 0x51;
        Cmd += (char) 0x30;

        return Cmd;
    }
    //Turn bypass mode on
    public String FS_PosBypassMode(char jMode1,char jMode2,char jMode3,char jMode4,char jMode5) {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x53;
        Cmd += jMode1;
        Cmd += jMode2;
        Cmd += jMode3;
        Cmd += jMode4;
        Cmd += jMode5;

        return Cmd;
    }
    //Print NV bit image
    public String FS_Print_Logo(char jMode1,char jMode2) {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x70;
        Cmd += jMode1;
        Cmd += jMode2;

        return Cmd;
    }
    //Print Logo
    public String FS_SaveNVbitImage() {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x71;
        Cmd += (char) 0x02;
        Cmd += (char) 0x00;

        return Cmd;
    }
    //Set Print Mode With Image
    public String FS_PrintModeWithImage(char jMode) {
        String Cmd = "";
        Cmd += (char) 0x1C;
        Cmd += (char) 0x76;
        Cmd += jMode;

        return Cmd;
    }






}
