package com.jt.controller.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WangHai
 * @Date 2019/12/7
 * @Describle what
 */
@RestController
public class JSONPController {

    @RequestMapping("/web/testJSON")
    public JSONPObject testJSON(String callback) {

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(200L).setItemDesc("还不错呢");

        String json = ObjectMapperUtil.objectToJson(itemDesc);

        return new JSONPObject(callback, json);
    }
}
