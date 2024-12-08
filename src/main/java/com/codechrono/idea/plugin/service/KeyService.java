package com.codechrono.idea.plugin.service;

import com.codechrono.idea.plugin.entity.EditRecord;
import com.codechrono.idea.plugin.entity.KeyChrono;
import com.codechrono.idea.plugin.service.impl.EditRecordService;
import com.codechrono.idea.plugin.service.impl.KeyChronoService;
import com.codechrono.idea.plugin.utils.CustomDateFormat;
import com.codechrono.idea.plugin.utils.HttpClientUtil;
import com.codechrono.idea.plugin.utils.KeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class KeyService {

    private final static Logger logger = LoggerFactory.getLogger(KeyService.class);

    /**
     * 校验服务器key是否有效，若有效，则保存本地记录
     *
     * @param apiKey
     * @return
     */
    public static boolean validateKeyServer(String apiKey) {


        String responseStr  = HttpClientUtil.get("key/checkKey?input=" + apiKey);
        //String responseStr="ba1af_cf63a_de6b3_72ece_85c74|133456|Sat Sep 21 16:57:17 CST 2024|Sat Sep 21 16:57:17 CST 2024|true";
       String[] parts  = responseStr.split("\\|");

        KeyChrono keyChrono=new KeyChrono();
        String responseKey= parts[0];
        //返回结果待处理
        if (StringUtils.isNotEmpty(responseKey) && apiKey.equals(responseKey)) {
            logger.info("服务端key校验成功");


            //若服务端返回成功，则保存本地记录
            KeyChronoInterface KeyChronoInterface = KeyChronoService.getInstance();

            keyChrono.setKeyCode(apiKey);
            keyChrono.setInput(parts[1]);
            
            CustomDateFormat customDataFormat = new CustomDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            keyChrono.setCreatedAt((customDataFormat.parse28(parts[2])).getTime());
            keyChrono.setUpdatedAt((customDataFormat.parse28(parts[3])).getTime());

            KeyChronoInterface.insert(keyChrono);
            return true;
        } else {
            logger.info("服务端key校验失败");
            return false;
        }
    }

    /**
     * 判断本地key是否有效
     *
     * @return
     */
    public static boolean validateKeyLocal() {

        KeyChronoInterface keyChronoService = com.codechrono.idea.plugin.service.impl.KeyChronoService.getInstance();
        KeyChrono localKey = keyChronoService.findByKeyCode("CodeChrono");
        //1.如果本地有记录，进行合法性校验，有效期校验
        //1 因该是idea启动调用
        if (localKey != null) {//
            //调用本地KeyUtil校验合法性
            if (!KeyUtils.validateKey
                    (localKey.getInput(), localKey.getCreatedAt(), localKey.getKeyCode())
            ) {
                logger.info("key校验失败");
                return false;
            }
            //校验有效期
            if (!validateKeyDate(localKey)) {
                logger.info("key已过期");
                return false;
            }
            return true;

        } else {
            //两周试用
            EditRecordInterface editRecordService = EditRecordService.getInstance();
            EditRecord data = editRecordService.findFirst();
            if (data == null) {
                return true;
            }
            logger.debug("EditRecord首次记录" + data.getCreateTime());
            if (data != null && data.getCreateTime() != null
                    && data.getCreateTime() + 1000 * 60 * 60 * 24 * 14 > System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断keyCoed是否在有效期内
     *
     * @param keyChrono
     * @return
     */
    private static boolean validateKeyDate(KeyChrono keyChrono) {
        if (keyChrono == null || keyChrono.getCreatedAt() == null) {
            return false;
        }

        // 获取当前时间
        // 获取要key有效截至日期
        Calendar keyCreatedDate = Calendar.getInstance();
        keyCreatedDate.setTime(new Date(keyChrono.getCreatedAt()));
        keyCreatedDate.add(Calendar.MONTH, 1);

        // 获取当前时间的Calendar实例
        Calendar now = Calendar.getInstance();

        // 比较
        boolean isAfteOneMonths = keyCreatedDate.after(now);

        logger.info("key截至日期大于当前时间：" + isAfteOneMonths);
        return isAfteOneMonths;
    }


}
