package Processor;

import net.sf.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class NormalProcessor implements PageProcessor{

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(1000);

    public NormalProcessor() {}
    
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
//		 page.putField("date", new JsonPathSelector("[*]."+dateKey).selectList(page.getRawText()));
//		 page.putField("amount", new JsonPathSelector("[*]."+amountKey).selectList(page.getRawText()));
//		 page.putField("tidlist", new JsonPathSelector("[*]."+tidKey).selectList(page.getRawText()));
//		 page.putField("price", new JsonPathSelector("[*]."+priceKey).selectList(page.getRawText()));
		JSONObject o=page.getJson().toObject(JSONObject.class);
		JSONObject ticker=o.getJSONObject("ticker");
		page.putField("last",ticker.get("last").toString());
		page.putField("key", page.getUrl().get().split("=")[1]);
	
	}


}
