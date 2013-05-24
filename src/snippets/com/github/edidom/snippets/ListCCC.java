/*******************************************************************************
 * Copyright 2013 Raymond Chin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.edidom.snippets;

import com.github.edidom.common.Document;
import com.github.edidom.common.Segment;
import com.github.edidom.input.EdifactBuilder;

public class ListCCC {

    public final static String IFTMBC = 
        "UNA:+.? 'UNB+UNOC:3+AAA:ZZZ+BBB:ZZZ+110419:0251+100015458'UNH+001+IFTMBC:D:99B:UN:2.0'BGM+770++6+AP'CTA+CW+:QOMARIYAH, NURUL'COM+?+6231-3295181 EX60:TE'COM+?+62 31 3292261:FX'DTM+137:201104190251:203'TSR+30'FTX+AAI+++THIS BOOKING CONFIRMATION IS SUBJECT TO SEALING OF THE CONTAINER WITH NOT BE RESPONSIBLE FOR ANY COSTS/DELAYS OCCURING DUE TO INTERVENTION'RFF+ZZZ:5502166'RFF+BN:92418824'RFF+BM:HLCUSUB110411566'RFF+FF:USISBY03130'TDT+20+127+1++HLCU:172:182+++9444962::11:SINAR SANGIR'LOC+9+IDSUB:139:6:SURABAYA'DTM+180:201104130200:203'DTM+133:201104170000:203'LOC+11+PKKHI:139:6:KARACHI'DTM+132:201105081200:203'NAD+CA+HLCU:160:86+HAPAG - LLOYD AG:ROSENSTRASSE 17+++++20095'NAD+ZZZ+CSUNIVERSALSHIPSUR:160:86+PT UNIVERSAL SHIPPING INDONESIA:PENGAMPON SQUARE C 28:JL. SEMUT BARU+++++60161'NAD+FW+CSUNIVERSALSHIPSUR:160:86+PT UNIVERSAL SHIPPING INDONESIA:PENGAMPON SQUARE C 28:JL. SEMUT BARU+++++60161'NAD+CZ++PT. SORINI TOWA BERLIAN CORPORINDO:DESA CANGKRINGMALANG,:BEJI, KAB.:EAST JAVA+++++67154'NAD+FC+CSUNIVERSALSHIPSUR:160:86+PT UNIVERSAL SHIPPING INDONESIA:PENGAMPON SQUARE C 28:JL. SEMUT BARU+++++60161'NAD+CN++MCB BANK LTD., (FORMERLY MOSLIM:COMMERCIAL BANK LTD):CIRCULAR ROAD BRANCH'GID+1+80:PK:67:6'FTX+AAA+++1X20?'FCL CONTAINER STC ?:'MEA+AAE+G+KGM:22160'EQD+CN++22G1:102:5'EQN+1:2'NAD+CK++MASAJI TATANAN CONTR:JL TANJUNG TEMBAGA NO 10:TG PERAK+++++60167'NAD+TR++PT BERLIAN JASA TERMINAL INDONESIA:JL. PERAK BARAT 379'UNT+32+001'UNZ+1+100015458'";
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        EdifactBuilder builder = new EdifactBuilder();
        Document doc = builder.buildDocument(IFTMBC);
        for (Segment seg : doc.getSegments("NAD")) {
            System.out.println(seg.getField(2).getValues()[0]);
        }
    }

}
