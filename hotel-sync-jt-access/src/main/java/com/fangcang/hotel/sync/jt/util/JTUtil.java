package com.fangcang.hotel.sync.jt.util;

import com.fangcang.enums.BedTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JTUtil {

    private static final Log log = LogFactory.getLog(JTUtil.class);

    public static String getClassifyBedType(String bed){
        String fcBed = null;
        if (StringUtils.isNotEmpty(bed))
        {
           if (bed.contains("大床"))
            {
                fcBed = BedTypeEnum.KING.key;
            }
            else if (bed.contains("双床"))
            {
                fcBed = BedTypeEnum.TWIN.key;
            }
        }
        return fcBed;
    }

}
