/**
 * TODO :
 *
 * @author : yiming
 * 2016年8月27日 下午4:08:46 / 精致科技 copyright
 */
package com.hf.springbootmvn.demo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hf.springbootmvn.demo.configuration.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import weixin.popular.support.TicketManager;

@WebListener
public class TicketManagerListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(TicketManagerListener.class);

    @Autowired
    WxConfig wxConf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("------------------TicketManagerListener----contextInitialized---------------");
        TicketManager.init(wxConf.getAppID(), 15, 60 * 119);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("------------------TicketManagerListener----contextInitialized---------------");
        TicketManager.destroyed();
    }

}
