package Main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import Common.PropertiesConfig;
import Common.PropertiesService;
import Pipeline.NormalPipeline;
import Processor.NormalProcessor;

import us.codecraft.webmagic.Spider;

public class Main {
	
public static class SpiderTask implements Runnable{
		
		String url1,url2,url3;
		public SpiderTask(String url1,String url2,String url3) {
			// TODO Auto-generated constructor stub
			
			this.url1=url1;
			this.url2=url2;
			this.url3=url3;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Spider.create(new NormalProcessor()).addUrl(url1,url2,url3).addPipeline(new NormalPipeline()).run();
		}
		
	}
	public static void main(String[] args) {
		    initWsDb();
			String etc_url="http://api.chbtc.com/data/v1/ticker?currency=etc_cny";
			String eth_url="http://api.chbtc.com/data/v1/ticker?currency=eth_cny";
			String ltc_url="http://api.chbtc.com/data/v1/ticker?currency=ltc_cny";
			ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1); 
			Runnable task=new SpiderTask(etc_url,eth_url,ltc_url);
			scheduledThreadPool.scheduleAtFixedRate(task,1,3, TimeUnit.SECONDS);

	}
	
	 //初始化本地数据库

    public static void initWsDb(){
    	PropertiesConfig config = PropertiesService.getApplicationConfig();	 
		C3p0Plugin c3p0Plugin = new C3p0Plugin(config.getProperty("jdbc.url"),
				config.getProperty("jdbc.username"),config.getProperty("jdbc.password"));
		c3p0Plugin.setDriverClass(config.getProperty("jdbc.driver"));
		c3p0Plugin.setMaxPoolSize(1000);
		c3p0Plugin.setMaxIdleTime(Integer.parseInt(config.getProperty("dbcp.maxIdle")));
		c3p0Plugin.start();
		ActiveRecordPlugin arp = new ActiveRecordPlugin("databases",c3p0Plugin);
		
	   
	
		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arp.start();
		System.out.println("本地数据库初始化成功.............................");

    }
}
