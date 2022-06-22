package com.hyh.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.hyh.ssm.model.OmOrder;
import com.hyh.ssm.service.OmOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : huang.yaohua
 * @date : 2022/6/21 17:44
 */
@Controller
@Slf4j
public class OmOrderController {

    @Autowired
    private OmOrderService omOrderService;

    @RequestMapping("/omOrder/{id}")
    @ResponseBody
    public OmOrder omOrder(@PathVariable("id") Integer id) {
        log.error("id={}", id);
        return omOrderService.getOmOrder(id);
    }


    @RequestMapping("/orders")
    @ResponseBody
    public PageInfo<OmOrder> getOrders(@RequestParam("pageSize") Integer pageSize,
                                       @RequestParam("pageIndex") Integer pageIndex) {
        return omOrderService.getOrders(pageSize, pageIndex);
    }

    @RequestMapping("/orders2")
    @ResponseBody
    public List<OmOrder> getOrders2(@RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("pageIndex") Integer pageIndex) {
        return omOrderService.getOrders2(pageSize, pageIndex);
    }

    @RequestMapping("/orders3")
    @ResponseBody
    public PageInfo<OmOrder> getOrders3(@RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("pageIndex") Integer pageIndex) {
        return omOrderService.getOrders3(pageSize, pageIndex);
    }
}
