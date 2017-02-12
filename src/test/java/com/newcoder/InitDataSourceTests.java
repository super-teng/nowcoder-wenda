package com.newcoder;

import com.newcoder.dao.NewsDao;
import com.newcoder.dao.QuestionDAO;
import com.newcoder.dao.UserDao;
import com.newcoder.model.News;
import com.newcoder.model.Question;
import com.newcoder.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class InitDataSourceTests {

	@Autowired
	UserDao userDao;

	@Autowired
	NewsDao newsDao;

	@Autowired
	QuestionDAO questionDAO;

	@Test
	public void contextLoads() {
		Random random = new Random();
		for(int i = 0 ; i < 10 ;i++){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setName(String.format("User%d",i));
			user.setPassword("");
			user.setSalt("");
//			userDao.addUser(user);
			News news = new News();
			news.setCommentCount(i);
			Date date = new Date();
			//每个资讯间隔5个小时
			date.setTime(date.getTime() + 1000*3600*5*i);
			news.setCreateDate(date);
			news.setImage(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			news.setLikeCount(i);
			news.setUserId(i);
			news.setTitle(String.format("TITLE%d",i));
			news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
//			newsDao.addNews(news);
			Question question = new Question();
			question.setUserId(i);
			question.setTitle(String.format("TITLE%d",i));
			question.setCommentCount(new Random().nextInt(100));
			date.setTime(date.getTime() + 1000*3600*5*i);
			question.setCreatedDate(date);
			question.setContent(String.format("JAVA和PHP那个是最好的语言%d？",i));
//			questionDAO.addQuestion(question);
		}
		List<Question> list = questionDAO.selectLatestQuestions(0,0,10);
		for(Question question : list){
			System.out.println(question.toString());
		}
	}

	@Test
	public void select(){
		System.out.println(userDao.selectUserById(1).toString());
	}
	@Test
	public void update(){
		User user =  new User();
		user.setPassword("1111");
		user.setId(1);
		userDao.updatePassword(user);
	}

	@Test
	public void delete(){
		userDao.deleteById(10);
	}

}
