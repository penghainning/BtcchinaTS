package Pipeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import Util.sendMail;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class NormalPipeline implements Pipeline {
	@Override
	public void process(ResultItems resultItems, Task task) {
		//获取之前保存的数据
	
		String sql="SELECT * FROM `user` a,`warm_value` b where b.userid=a.id";
		List<Record> records= Db.find(sql);
		Double last=Double.valueOf(resultItems.get("last"));
		String content="";
		String valueKey=resultItems.get("key").toString().split("_")[0];
		System.out.println("正在处理..... "+valueKey+" : "+last);
		String title=valueKey+"预警提醒";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Record r:records){
			try{
				long lasttime=sdf.parse(r.getStr(valueKey+"_lasttime")).getTime();
				Double lowvalue=r.getDouble(valueKey+"_low_value");
				if(last<=lowvalue){
					content="中国比特币预警\n"
							+"当前"
							+ valueKey
							+ "的价格："
							+last
							+" 已经低于您设置的值："
							+lowvalue
							+"\n时间："
							+sdf.format(new Date());
					
				}
				
				Double highvalue=r.getDouble(valueKey+"_high_value");
				if(last>=highvalue){
					content="中国比特币预警\n"
							+"当前"
							+ valueKey
							+ "的价格："
							+last
							+" 已经高于您设置的值："
							+highvalue
							+"\n时间："
							+sdf.format(new Date());
					
				}
				
				Date nowdate=new Date();
				long now=nowdate.getTime();
				String time=sdf.format(new Date(now));
				long middle=(now-lasttime)/ (60 * 1000);
				if(middle>=5&& !"".equals(content)){
					sendMail.sendMsg(r.get("mail").toString(),title,content);
					//System.out.println(content+"#"+r.get("mail").toString());
					Db.update("Update `user` set `"+valueKey+"_lasttime` = '"+time+"' where id = '"+r.get("id")+"'");
					System.out.println("发送邮件预警成功！"+time);
				}
				content="";
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
			
	}
	
}
